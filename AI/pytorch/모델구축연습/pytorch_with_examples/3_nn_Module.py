# nn 모듈
# Pytorch: nn
# 연산 그래프와 autograd는 복잡한 연산자를 정의하고 도함수(derivative)를 자동으로 계산하는 강력한 패러다임이다.
# 하지만, 규모가 큰 신경망에서는 autograd 그 자체만으로는 너무 low-level일 수 있다.
# 신경망을 구성할 때 종종 연산을 여러 계층에 배열(arrange)하는 것으로 생각하는데,
# 이 중 일부는 *학습 도중 최적화가 될 학습 가능한 매개변수*를 갖고 있다.

# Tensorflow는 Keras, TensorFlow-Slim, TFLearn 같은 패키지들이 연산 그래프를 더 높은 수준으로 추상화(higher-level abstraction)하여
# 제공하므로, 신경망을 구축하는데 있어 유용하다

# PyTorch에서는 nn패키지가 동일한 목적으로 제공된다.
# nn 패키지는 신경망 계층(layer)들과 거의 동일한 Module의 집합을 정의한다.
# Module은 입력 Tensor를 받고 출력 Tensor를 계산하는 한편, 학습 가능한 매개변수를 갖는 Tensor 같은 내부 상태(internal state)를 갖는다.
# nn 패키지는 또한 신경망을 학습시킬 때 주로 사용하는 유용한 손실 함수를 정의하고 있다.

import torch

N, D_in, H, D_out = 64, 1000, 100, 10
x = torch.randn(N, D_in)
y = torch.randn(N, D_out)

# nn 패키지를 사용하여 모델을 순차적 계층(sequence of layers)으로 정의한다.
# nn.Sequential은 다른 Module을 포함하는 Module로, 그 Module들을 순차적으로
# 적용하여 출력을 생성한다. 각각의 Linear Module은 선형 함수를 사용하여
# 입력으로부터 출력을 계산하고, 내부 Tensor에 가중치와 편향을 저장한다.

model = torch.nn.Sequential(
    torch.nn.Linear(D_in, H),
    torch.nn.ReLU(),
    torch.nn.Linear(H, D_out)
)

# 또한, nn 패키지에는 널리 사용하는 손실 함수에 대한 정의도 포함하고 있다.
# 여기에서는 MSE 사용
loss_fn = torch.nn.MSELoss(reduction='sum')

learning_rate = 1e-4
for t in range(500):

    y_pred = model(x)

    loss = loss_fn(y_pred, y)
    if t % 100 == 99:
        print(t, loss.item())

    loss.backward()

    with torch.no_grad():
        for param in model.parameters():
            param -= learning_rate * param.grad


# pytorch: 사용자 정의 nn.Module
# 기존 모듈의 구성보다 더 복잡한 모델을 구성해야 할 때, nn.Module의 서브클래스로 새 모듈 정의
# 입력 Tensor를 받아 다른 모듈 또는 Tensor의 autograd 연산을 사용하여 출력 Tensor를 만드는 forward 정의

# 2 layer 신경망을 직접 정의한 nn.Module sub class

class TwoLayerNet(torch.nn.Module):

    def __init__(self, D_in, H, D_out):
        """
        생성자에서 2개의 nn.Linear 모듈을 생성하고, 멤버 변수로 지정
        """
        super(TwoLayerNet, self).__init__()
        self.linear1 = torch.nn.Linear(D_in, H)
        self.linear2 = torch.nn.Linear(H, D_out)

    def forward(self, x):
        """
        순전파 함수에서는 입력 데이터의 tensor를 받고 출력 데이터의 tensor를 반환해야 한다.
        Tensor 상의 임의의 연산자뿐만 아니라 생성자에서 정의한 Module도 사용할 수 있다.
        """
        h_relu = self.linear1(x).clamp(min=0)
        y_pred = self.linear2(h_relu)

        return y_pred

N, D_in, H, D_out = 64, 1000, 100, 10
x = torch.randn(N, D_in)
y = torch.randn(N, D_out)

model = TwoLayerNet(D_in, H, D_out)

criterion = torch.nn.MSELoss(reduction='sum')
optimizer = torch.optim.SGD(model.parameters(), lr=1e-4)

for t in range(500):

    y_pred = model(x)

    loss = criterion(y_pred, y)

    if t % 100 == 99:
        print(t, loss.item())

    optimizer.zero_grad()
    loss.backward()
    optimizer.step()
