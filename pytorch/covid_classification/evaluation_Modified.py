import argparse
import numpy as np
from openpyxl import load_workbook

#Metric
def evaluate(prediction_labels, gt_labels):
    count = 0.0
    # print(len(gt_labels))

    for index, query in enumerate(prediction_labels):
        gt_label = int(gt_labels[index])
        pred_label = int(prediction_labels[query])

        if gt_label == pred_label:
            count += 1.0

    acc = count / float(len(gt_labels))
    return acc


def read_prediction_pt(file_name):
    with open(file_name) as f:
        lines = f.readlines()
    dictionary = dict([l.replace('\n', '').split(' ') for l in lines])
    return dictionary


def read_prediction_gt(file_name):

    with open(file_name) as f:
        lines = f.readlines()
    dictionary = []
    for l in lines:
        dictionary.append(l.replace("\n", "").split(' ')[1])
    # dictionary = dict([l.replace('\n', '').split(' ') for l in lines])
    return dictionary


def evaluation_metrics(prediction_file, testset_path):
    prediction_labels = read_prediction_pt(prediction_file)
    gt_labels = read_prediction_gt(testset_path)

    return evaluate(prediction_labels, gt_labels)


if __name__ == '__main__':
    args = argparse.ArgumentParser()
    args.add_argument('--prediction', type=str, default='prediction.txt')
    config = args.parse_args()
    testset_path = './data/test/test_label_COVID.txt'

    # print(evaluation_metrics(config.prediction, testset_path))
    metric_result = evaluation_metrics(config.prediction, testset_path)

    model_name = config.prediction.split("_")[1:]

    print("-------------------------------------------------\n")
    for name in model_name:
        print(name, end=" ")

    # print(" Parameters")
    # print(" Learning Rate: 0.00003")
    # print(" gamma: 0.99")
    # print(" batch: 8")
    # print(" weight_decay: 1e-4")
    # print(" Epochs: 166/200\n")
    print('\n\nTest Eval result: {:.4f}'.format(metric_result))
    print("\n-------------------------------------------------")