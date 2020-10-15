"""

    데이터는?
    : 일반적으로 이미지나 텍스트, 오디오나 비디오 데이터를 다룰 때는 표준 Python 패키지를 이용하여 Numpy 배열로 불러오면 된다.
    그 후 그 배열을 torch.Tensor로 변환
    - 이미지: Pillow, OpenCV
    - 오디오: SciPy, LibROSA
    - 텍스트: Python, Cython, NLTK, SpaCy

    * TorchVision: 영상 분야를 위한 패키지
    datasets, DataLoader 등과 같은 기능 포함

    CIFAR10
    이미지 크기: 3x32x32
    32x32 픽셀 크기의 이미지가 3개 채널(channel)의 색상으로 이루어져 있음

    *** 이미지 분류기 학습하기 ***
    1. torchvision 을 사용 - CIFAR10의 학습용/시험용 데이터셋을 불러오고, 정규화(Normalizing)한다.
    2. 합성곱 신경망(CNN) 정의
    3. 손실 함수 정의
    4. 학습용 데이터를 사용하여 신경망 학습
    5. 시험용 데이터를 사용하여 신경망 검사

"""

import torch
import torchvision
import torchvision.transforms as transforms
import torch.optim as optim
import matplotlib.pyplot as plt
import numpy as np
import torch.nn as nn
import torch.nn.functional as F



def imshow(img):
    img = img / 2 + 0.5     # unnormalize
    npimg = img.numpy()
    plt.imshow(np.transpose(npimg, (1, 2, 0)))
    plt.show()


# 2. CNN 정의하기

class Net(nn.Module):

    def __init__(self):
        super(Net, self).__init__()
        self.conv1 = nn.Conv2d(3, 6, 5)
        self.pool = nn.MaxPool2d(2, 2)
        self.conv2 = nn.Conv2d(6, 16, 5)
        self.fc1 = nn.Linear(16 * 5 * 5, 120)
        self.fc2 = nn.Linear(120, 84)
        self.fc3 = nn.Linear(84, 10)

    def forward(self, x):
        x = self.pool(F.relu(self.conv1(x)))
        x = self.pool(F.relu(self.conv2(x)))
        x = x.view(-1, 16 * 5 * 5)
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)

        return x





if __name__ == '__main__':


    # GPU로 학습하기

    device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

    # 1. CIFAR10을 불러오고 정규화

    transforms = transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))
    ])

    trainset = torchvision.datasets.CIFAR10(root='./data', train=True,
                                            download=True, transform=transforms)

    trainloader = torch.utils.data.DataLoader(trainset, batch_size=4,
                                              shuffle=True, num_workers=2)

    testset = torchvision.datasets.CIFAR10(root='./data', train=False,
                                           download=True, transform=transforms)

    testloader = torch.utils.data.DataLoader(trainset, batch_size=4,
                                             shuffle=False, num_workers=2)

    classes = ('plane', 'car', 'bird', 'cat',
               'deer', 'dog', 'frog', 'horse', 'ship', 'truck')

    # 학습용 이미지를 무작위로 가져오기
    dataiter = iter(trainloader)
    images, labels = dataiter.next()

    # 이미지 보여주기
    imshow(torchvision.utils.make_grid(images))
    # 정답 label 출력
    print(' '.join('%5s' % classes[labels[j]] for j in range(4)))

    # 2. CNN 정의하기

    net = Net().to(device)
    print(net)

    # 3. 손실함수와 Optimizer 정의

    criterion = nn.CrossEntropyLoss()
    optimizer = optim.SGD(net.parameters(), lr=0.001, momentum=0.9)

    # 4. 신경망 학습하기

    for epoch in range(2):

        running_loss = 0.0
        for i, data in enumerate(trainloader, 0):
            # [inputs, labels]의 목록인 data로부터 입력을 받은 후;
            inputs, labels = data[0].to(device), data[1].to(device)

            # 변화도(Gradient) 매개변수를 0으로 만들고
            optimizer.zero_grad()

            # 순전파 + 역전파 + 최적화를 한 후
            outputs = net(inputs)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            # 통계를 출력한다
            running_loss += loss.item()
            if i % 2000 == 1999:  # print every 2000 mini-batches
                print('[%d %5d] loss: %.3f' %
                      (epoch + 1, i + 1, running_loss / 2000))
                running_loss = 0

    print('Finished Training')

    # 학습한 모델 save
    PATH = './cifar_net.pth'
    torch.save(net.state_dict(), PATH)

    # 5. 시험용 데이터로 신경망 검사

    dataiter = iter(testloader)
    images, labels = dataiter.next()

    # 이미지 출력
    imshow(torchvision.utils.make_grid(images))

    # 저장한 모델 다시 불러오기.
    net = Net()
    net.load_state_dict(torch.load(PATH))

    outputs = net(images)

    _, predicted = torch.max(outputs, 1)

    print('Predicted: ', ' '.join('%5s' % classes[predicted[j]]
                                  for j in range(4)))

    correct = 0
    total = 0
    with torch.no_grad():
        for data in testloader:
            images, labels = data
            outputs = net(images)
            _, predicted = torch.max(outputs.data, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item()

    print('Accuracy of the network on the 10000 test images: %d %%' % (
            100 * correct / total))

    class_correct = list(0. for i in range(10))
    class_total = list(0. for i in range(10))
    with torch.no_grad():
        for data in testloader:
            images, labels = data
            outputs = net(images)
            _, predicted = torch.max(outputs, 1)
            c = (predicted == labels).squeeze()
            for i in range(4):
                label = labels[i]
                class_correct[label] += c[i].item()
                class_total[label] += 1

    for i in range(10):
        print('Accuracy of %5s : %2d %%' % (
            classes[i], 100 * class_correct[i] / class_total[i]))