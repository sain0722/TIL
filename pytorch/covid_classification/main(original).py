import os
import math
import datetime
import numpy as np
import time
import torch
import torch.nn as nn
from torch.optim import Adam
from torch.optim.lr_scheduler import StepLR
import argparse
from dataloader import data_loader
from evaluation import evaluation_metrics
from model import Vgg19
from tensorboardX import SummaryWriter #Modify

DATASET_PATH = os.path.join('./data')

def _infer(model, cuda, data_loader):
    res_fc = None
    res_id = None
    for index, (image_name, image, _) in enumerate(data_loader):
        if cuda :
            image = image.cuda()
        fc = model(image)
        fc = fc.detach().cpu().numpy()

        if index == 0:
            res_fc = fc
            res_id = image_name
        else:
            res_fc = np.concatenate((res_fc, fc), axis=0)
            res_id = res_id + image_name

    res_cls = np.argmax(res_fc, axis=1)
    #print('res_cls{}\n{}'.format(res_cls.shape, res_cls))

    return [res_id, res_cls]


def feed_infer(output_file, infer_func):
    prediction_name, prediction_class = infer_func()

    print('write output')
    predictions_str = []
    for index, name in enumerate(prediction_name):
        test_str = name + ' ' + str(prediction_class[index])
        predictions_str.append(test_str) 
    with open(output_file, 'w') as file_writer:
        file_writer.write("\n".join(predictions_str))

    if os.stat(output_file).st_size == 0:
        raise AssertionError('output result of inference is nothing')


def validate(prediction_file, model, validate_dataloader, validate_label_file, cuda,sentence):
    feed_infer(prediction_file, lambda : _infer(model, cuda, data_loader=validate_dataloader))

    metric_result = evaluation_metrics(prediction_file, validate_label_file)
    print("-------------------------------------------------")
    print(sentence + ' Eval result: {:.4f}'.format(metric_result))
    print("-------------------------------------------------")
    return metric_result


def test(prediction_file, model, test_dataloader, cuda):
    feed_infer(prediction_file, lambda : _infer(model, cuda, data_loader=test_dataloader))


def save_model(model_name, model, optimizer, scheduler):
    state = {
        'model': model.state_dict(),
        'optimizer': optimizer.state_dict(),
        'scheduler': scheduler.state_dict()
    }
    torch.save(state, os.path.join(model_name + '.pth'))
    print('model saved')


def load_model(model_name, model, optimizer=None, scheduler=None):
    state = torch.load(os.path.join(model_name))
    model.load_state_dict(state['model'])
    if optimizer is not None:
        optimizer.load_state_dict(state['optimizer'])
    if scheduler is not None:
        scheduler.load_state_dict(state['scheduler'])
    print('model loaded')


if __name__ == '__main__':
    # mode argument
    args = argparse.ArgumentParser()
    args.add_argument("--num_classes", type=int, default=2)
    args.add_argument("--lr", type=float, default=0.00001)
    args.add_argument("--cuda", type=bool, default=True)
    args.add_argument("--num_epochs", type=int, default=4500)
    args.add_argument("--print_iter", type=int, default=10)
    args.add_argument("--model_name", type=str, default="model.pth") 
    args.add_argument("--prediction_file", type=str, default="prediction.txt")
    args.add_argument("--batch", type=int, default=8)
    args.add_argument("--mode", type=str, default="train")

    init = 0


    config = args.parse_args()

    num_classes = config.num_classes
    base_lr = config.lr
    cuda = config.cuda
    num_epochs = config.num_epochs
    print_iter = config.print_iter
    model_name = config.model_name
    prediction_file = config.prediction_file
    training_prediction= 'prediction2.txt'

    batch = config.batch
    mode = config.mode

    # create model
    model = Vgg19(num_classes=num_classes)

    summary = SummaryWriter()


    if mode == 'test':
        load_model(model_name, model)
    if init != 0 :
        modelName = '{}.pth'.format(init)
        print(modelName)
        load_model(modelName, model)

    if cuda:
        model = model.cuda()

    if mode == 'train':
        # define loss function
        loss_fn = nn.CrossEntropyLoss()
        if cuda:
            loss_fn = loss_fn.cuda()

        # set optimizer
        optimizer = Adam([param for param in model.parameters() if param.requires_grad],lr=base_lr, weight_decay=1e-4)
        # learning decay
        scheduler = StepLR(optimizer, step_size=40, gamma=0.8)

        # get data loader
        train_dataloader, train_label_file = data_loader(root=DATASET_PATH, phase='train', batch_size=batch) # Modifiy
        validate_dataloader, validate_label_file = data_loader(root=DATASET_PATH, phase='validate', batch_size=batch)
        test_dataloader, test_label_file = data_loader(root=DATASET_PATH, phase='test', batch_size=batch)# Modifiy

        time_ = datetime.datetime.now()
        num_batches = len(train_dataloader)
        #print("num batches : ", num_batches)

        print("------------------------------------------------------------")
        total_params = sum(p.numel() for p in model.parameters())
        print("num of parameter : ",total_params)
        trainable_params = sum(p.numel() for p in model.parameters() if p.requires_grad)
        print("num of trainable_ parameter :",trainable_params)
        print("------------------------------------------------------------")


        summary.add_scalar('ACC/train', 0, 0)  # Modify
        summary.add_scalar('ACC/validate', 0, 0)  # Modify
        summary.add_scalar('ACC/test', 0, 0)  # Modify

        for epoch in range(num_epochs):
            model.train()
            name = init + epoch
            for iter_, data in enumerate(train_dataloader):
                # fetch train data
                _, image, is_label = data 
                if cuda:
                    image = image.cuda()
                    is_label = is_label.cuda() 

                # update weight
                pred = model(image)
                loss = loss_fn(pred, is_label)
                optimizer.zero_grad()
                loss.backward()
                optimizer.step()

                if (iter_ + 1) % print_iter == 0:
                    elapsed = datetime.datetime.now() - time_
                    expected = elapsed * (num_batches / print_iter)
                    _epoch = name + ((iter_ + 1) / num_batches)
                    print('[{:.3f}/{:d}] loss({}) ''elapsed {} expected per epoch {}'.format(_epoch, num_epochs, loss.item(), elapsed, expected))

                    time_ = datetime.datetime.now()

            # scheduler update
            scheduler.step()

            # save model 
            # if epoch % 500 == 0 :
            save_model(str(name + 1), model, optimizer, scheduler)

            # train
            trainingAcc = validate(training_prediction, model, train_dataloader, train_label_file, cuda,'Train')  # Modify
            # validate
            valAcc = validate(prediction_file, model, validate_dataloader, validate_label_file, cuda,'Val') #Modify
            # Test
            testAcc = validate(prediction_file, model, test_dataloader, test_label_file, cuda, 'Test')  # Modify

            summary.add_scalar('ACC/validate', valAcc, name) #Modify

            summary.add_scalar('ACC/train', trainingAcc, name)  # Modify

            summary.add_scalar('ACC/test', testAcc, name) #Modify


            time_ = datetime.datetime.now()
            elapsed = datetime.datetime.now() - time_
            print('[epoch {}] elapsed: {}'.format(name + 1, elapsed)) #Modify

    elif mode == 'test':
        model.eval()
        # get data loader
        test_dataloader, _ = data_loader(root=DATASET_PATH, phase='test', batch_size=batch)
        test(prediction_file, model, test_dataloader, cuda)
        # submit test result