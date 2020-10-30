import torch
import torch.nn as nn
import torch.nn.functional as F

"""
        신경망(NEURAL NETWORKS)
        
        nn 은 모델을 정의하고 미분하는 데 autograd 를 사용한다.
        nn.Module은 게층(layer)과 output을 반환하는 forward(input) 메서드 포함.
        
        신경망의 일반적인 학습 과정
        - 학습 가능한 매개변수(또는 가중치(weight))를 갖는 신경망 정의
        - 데이터셋(dataset) 입력을 반복
        - 입력을 신경망에서 전파(process)
        - 손실(loss; 출력이 정답으로부터 얼마나 떨어져있는지)를 계산
        - 변화도(gradient)를 신경망의 매개변수들에 역으로 전파
        - 신경망의 가중치 갱신, 일반적으로 다음과 같은 간단한 규칙 사용
            - 가중치(weight) = 가중치(weight) - 학습율(learning rate) * 변화도(gradient)
            - weight -= learning_rate * gradient

"""

# 신경망 정의
class Net(nn.Module):

    def __init__(self):
        super(Net, self).__init__()
        # 1 input image channel, 6 output channels, 3x3 square convolution
        # kernel
        self.conv1 = nn.Conv2d(1, 6, 3)
        self.conv2 = nn.Conv2d(6, 16, 3)
        # an affine operation: y = Wx + b
        self.fc1 = nn.Linear(16 * 6 * 6, 120)   # 6 x 6 from image dimension
        self.fc2 = nn.Linear(120, 84)
        self.fc3 = nn.Linear(84, 10)

    def forward(self, x):
        # Max pooling over a (2, 2) window
        x = F.max_pool2d(F.relu(self.conv1(x)), (2, 2))
        # If the size is a square you can specify a single number
        x = F.max_pool2d(F.relu(self.conv2(x)), 2)
        x = x.view(-1, self.num_flat_features(x))
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)

        return x

    def num_flat_features(self, x):
        size = x.size()[1:]     # all dimensions except the batch dimension
        num_features = 1
        for s in size:
            num_features *= s
        return num_features

net = Net()
print(net)

# forward  함수만 정의하고 나면 (변화도를 계산하는) backward 함수는 autograd 를 사용하여 자동으로 정의된다.
# forward 함수에서는 어떠한 Tensor 연산을 사용해도 된다.

# 모델의 학습가능한 매개변수들은 net.parameters()에 의해 반환된다.
params = list(net.parameters())
print(len(params))
print(params[0].size())     # conv1's .weight

# 임의의 32x32 값 넣기
# 이 신경망(LeNet)의 예상되는 입력 크기는 32x32 이다.
# 이 신경망에 MNIST 데이터셋을 사용하기 위해서는, 데이터셋의 의미지 크기를 32x32로 변경해야 한다.

input = torch.randn(1, 1, 32, 32)
out = net(input)
print(out)

# 모든 매개변수의 변화도 버퍼(gradient buffer)를 0으로 설정하고, 무작위 값으로 역전파를 한다.
net.zero_grad()
out.backward(torch.randn(1, 10))

output = net(input)
target = torch.randn(10)
print(target)

target = target.view(1, -1)     # make it the same shape as output
print(target)
criterion = nn.MSELoss()

loss = criterion(output, target)
print(loss)

# params = list(net.parameters())
#
# for param in params:
#     param.requires_grad = False
#
# params[-1].requires_grad = True
#
# print(params)

input = torch.randn(1, 1, 32, 32)

output = net(input)
target = torch.randn(10)        # a dummy target, for example
target = target.view(1, -1)     # make it the same shape as output
criterion = nn.MSELoss()

loss = criterion(output, target)
print(loss)

print(loss.grad_fn)
print(loss.grad_fn.next_functions[0][0])    # Linear
print(loss.grad_fn.next_functions[0][0].next_functions[0][0])   # ReLU

# BackProp

net.zero_grad()

print('conv1.bias.grad before backward')
print(net.conv1.bias.grad)

loss.backward()

print('conv1.bias.brad after backward')
print(net.conv1.bias.grad)

# 가중치 갱신

learning_rate = 0.01

for f in net.parameters():
    f.data.sub_(f.grad.data * learning_rate)

import torch.optim as optim

# optimizer 생성
optimizer = optim.SGD(net.parameters(), lr=learning_rate)

# 학습 과정(training loop)

epochs = 50

for epoch in range(epochs):
    optimizer.zero_grad()   # zero the gradient buffers
    output = net(input)
    loss = criterion(output, target)
    loss.backward()
    optimizer.step()    # Does the update

    print("{}: {}".format(epoch+1, loss.item()))