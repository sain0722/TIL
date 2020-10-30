import numpy as np

# N은 배치 크기, D_in은 입력의 차원
# H는 은닉층의 차원, D_out은 출력 차원
N, D_in, H, D_out = 64, 1000, 100, 10

# 무작위의 입력과 출력 데이터 생성
x = np.random.randn(N, D_in)
y = np.random.randn(N, D_out)

# 무작위로 가중치를 초기화
w1 = np.random.randn(D_in, H)
w2 = np.random.randn(H, D_out)

learning_rate = 1e-6
for t in range(1):
    # 순전파 단계: 예측값 y를 계산
    h = x.dot(w1)
    h_relu = np.maximum(h, 0)
    y_pred = h_relu.dot(w2)

    # 손실(loss)를 계산하고 출력
    loss = np.square(y_pred - y).sum()
    print(t, loss)

    # 손실에 따른 w1, w2의 변화도를 계산하고 역전파한다.
    grad_y_pred = 2.0 * (y_pred - y)
    grad_w2 = h_relu.T.dot(grad_y_pred)
    grad_h_relu = grad_y_pred.dot(w2.T)
    grad_h = grad_h_relu.copy()
    grad_h[h < 0] = 0
    grad_w1 = x.T.dot(grad_h)

    # 가중치 갱신
    w1 -= learning_rate * grad_w1
    w2 -= learning_rate * grad_w2

# Pytorch: Tensors
# Numpy로는 GPU를 사용하여 수치 연산을 가속화할 수가 없다.
# Numpy배열과 달리 Pytorch Tensor는 N차원의 배열이며, 딥러닝이나 연산 그래프, 변화도는 알지 못하며, 과학적 분야의 연산을 위한 포괄적인 도구이다
# 하지만, Tensor는 GPU 활용이 가능. -> 새로운 자료형으로 변환(cast)만 해주면 GPU 활용 가능

import torch

dtype = torch.float

device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

# N은 배치 크기, D_in은 입력의 차원
# H는 은닉층의 차원, D_out은 출력 차원
N, D_in, H, D_out = 64, 1000, 100, 10

# 무작위의 입력과 출력 데이터 생성
x = torch.randn(N, D_in, device=device, dtype=dtype)
y = torch.randn(N, D_out, device=device, dtype=dtype)

# 무작위로 가중치를 초기화
w1 = torch.randn(D_in, H, device=device, dtype=dtype)
w2 = torch.randn(H, D_out, device=device, dtype=dtype)

learning_rate = 1e-6
for t in range(500):
    # 순전파 단계: 예측값 y를 계산한다.
    h = x.mm(w1)
    h_relu = h.clamp(min=0)
    y_pred = h_relu.mm(w2)

    # 손실(loss)을 계산하고 출력
    loss = (y_pred - y).pow(2).sum().item()
    if t % 100 == 99:
        print(t, loss)

    # 손실에 따른 w1, w2의 변화도를 계산하고 역전파한다
    grad_y_pred = 2.0 * (y_pred - y)
    grad_w2 = h_relu.t().mm(grad_y_pred)
    grad_h_relu = grad_y_pred.mm(w2.t())
    grad_h = grad_h_relu.clone()
    grad_h[h < 0] = 0
    grad_w1 = x.t().mm(grad_h)

    # 경사하강법(gradient descent)를 사용하여 가중치를 갱신
    w1 -= learning_rate * grad_w1
    w2 -= learning_rate * grad_w2


# Autograd
# Pytorch: Tensor와 autograd
# 위에서 신경망의 순전파 단계와 역전파 단계를 직접 구현하였다.
# 작은 2계층 신경망에서 역전파 단계를 직접 구현하는 것은 큰 일은 아니지만, 대규모의 복잡한 신경망에서는 매우 아슬아슬한 일일것이다.
# 다행히도, 자동미분(autograd)을 사용하여 신경망에서 역전파 단계의 연산을 자동화할 수 있다.
# autograd 패키지는 정확히 이런 기능을 제공한다.
# - Autograd를 사용할 때, 신경망의 순전파 단계는 연산 그래프를 정의하게 된다.
#   - 이 그래프의 노드(Node)는 Tensor, 엣지(Edge)는 입력 Tensor로부터 출력 Tensor를 만들어내는 함수가 된다.
#   - 이 그래프를 통해 역전파를 하게 되면 변화도를 쉽게 계산할 수 있다.
# 이는 복잡하게 들리지만, 사용하는 것은 매우 간단하다.
# 각 Tensor는 연산 그래프에서 노드로 표현된다.
#   - 만약, x가 x.requires_grad=True 인 Tensor면, x.grad는 어떤 스칼라 값에 대한 x의 변화도를 갖는 또 다른 Tensor이다.

# autograd를 사용하여 2계층 신경망 구현, 이제 역전파 단계를 직접 구현할 필요가 없다


# 입력과 출력을 저장하기 위해 무작위 값을 갖는 Tensor를 생성합니다.
# requires_grad=False로 설정하여 역전파 중에 이 Tensor들에 대한 변화도를 계산할
# 필요가 없음을 나타냅니다. (requres_grad의 기본값이 False이므로 아래 코드에는
# 이를 반영하지 않았습니다.)
x = torch.randn(N, D_in, device=device, dtype=dtype)
y = torch.randn(N, D_out, device=device, dtype=dtype)

# 가중치를 저장하기 위해 무작위 값을 갖는 Tensor를 생성합니다.
# requires_grad=True로 설정하여 역전파 중에 이 Tensor들에 대한
# 변화도를 계산할 필요가 있음을 나타냅니다.
w1 = torch.randn(D_in, H, device=device, dtype=dtype, requires_grad=True)
w2 = torch.randn(H, D_out, device=device, dtype=dtype, requires_grad=True)

learning_rate = 1e-6
for t in range(500):
    # 순전파 단계: Tensor 연산을 사용하여 예상되는 y 값을 계산한다. 이는 Tensor를
    # 사용한 순전파 단계와 완전히 동일하지만, 역전파 단계를 별도로 구현하지 않아도
    # 되므로, 중간값들에 대한 참조(reference)를 갖고 있을 필요가 없다.
    y_pred = x.mm(w1).clamp(min=0).mm(w2)

    # Tensor 연산을 사용하여 손실을 계산하고 출력한다.
    # loss는 (1, ) 형태의 Tensor이며, loss.item()은 loss의 스칼라 값이다.
    loss = (y_pred - y).pow(2).sum()
    if t % 100 == 99:
        print(t, loss.item())

    # autograd를 사용하여 역전파 단계를 계산한다. 이는 requires_grad=True를
    # 갖는 모든 Tensor에 대해 손실의 변화도를 계산한다. 이후 w1.grad와 w2.grad는
    # w1, w2 각각에 대한 손실의 변화도를 갖는 Tensor가 된다.
    loss.backward()

    # 경사하강법(gradient descent)을 사용하여 가중치를 수동으로 계산한다.
    # torch.no_grad()로 감싸는 이유는 가중치들이 requirers_grad=True이지만,
    # autograd에서는 이를 추적할 필요가 없기 때문이다.
    # 다른 방법은 weight.data 및 weight.grad.data를 조작하는 방법이다.
    # tensor.data가 tensor의 저장공간을 공유하기는 하지만, 이력을
    # 추적하지 않는다는 것을 기억해야 한다.
    # 또한, 이를 위해 torch.optim.SGD 를 사용할 수도 있다.
    with torch.no_grad():
        w1 -= learning_rate * w1.grad
        w2 -= learning_rate * w2.grad

        # 가중치 갱신 후에는 수동으로 변화도를 0으로 만든다.
        w1.grad.zero_()
        w2.grad.zero_()


