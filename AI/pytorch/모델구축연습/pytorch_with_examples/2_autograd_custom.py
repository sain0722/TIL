# Pytorch: 새 autograd 함수 정의
# 내부적으로 autograd의 기본 연산자는 실제로 Tensor를 조작하는 2개의 함수이다.
# forward 함수
# - 입력 Tensor로부터 출력 Tensor를 계산
# backward 함수
# - 어떤 스칼라 값에 대한 출력 Tensor의 변화도를 전달받고, 동일한 스칼라 값에 대한 입력 Tensor의 변화도 계산
# torch.autograd.Function의 서브클래스를 정의하고 forward와 backward 함수를 구현함으로써
# 사용자 정의 autograd 연산자를 손쉽게 정의할 수 있다.
# 그 후, 인스턴스를 생성하고 이를 함수처럼 호출하여 입력 데이터를 갖는 Tensor를 전달하는 식으로 새로운 autograd 연산자를 사용할 수 있다.

# ReLU로 비선형적(nonlinearity)으로 동작하는 사용자 정의 autograd함수 정의 및 2-계층 신경망에 적용

import torch
import torch.optim as optim


class MyReLU(torch.autograd.Function):
    """
    torch.autograd.Function을 상속받아 사용자 정의 autograd Function을 구현하고,
    Tensor 연산을 하는 순전파와 역전파 단계 구현
    """

    @staticmethod
    def forward(ctx, input):
        """
        순전파 단계에서는 입력을 갖는 Tensor를 받아 출력을 갖는 Tensor 반환
        ctx는 컨텍스트 객체(context object)로 역전파 연산을 위한 정보 저장에 사용한다.
        ctx.save_for_backward method를 사용하여 역전파 단계에서 사용할 어떠한 객체도 저장(cache)해 둘 수 있다.
        """
        ctx.save_for_backward(input)
        return input.clamp(min=0)

    def backward(ctx, grad_output):
        """
        역전파 단계에서는 출력에 대한 손실의 변화도를 갖는 Tensor를 받고, 입력에 대한 손실의 변화도를 계산
        """

        input, = ctx.saved_tensors
        grad_input = grad_output.clone()
        grad_input[input < 0] = 0
        return grad_input



dtype = torch.float
device = torch.device("cuda:0")

N, D_in, H, D_out = 64, 1000, 100, 10

# 입력과 출력을 랜덤하게
x = torch.randn(N, D_in, device=device, dtype=dtype)
y = torch.randn(N, D_out, device=device, dtype=dtype)

# 가중치를 랜덤하게
w1 = torch.randn(D_in, H, device=device, dtype=dtype, requires_grad=True)
w2 = torch.randn(H, D_out, device=device, dtype=dtype, requires_grad=True)

learning_rate = 1e-6

# params = [x, y, w1, w2]
# optimizer = optim.SGD(params, lr=learning_rate)

for t in range(500):

    # 사용자 정의 Function을 적용하기 위해 Function.apply 메소드 사용
    relu = MyReLU.apply

    y_pred = relu(x.mm(w1)).mm(w2)

    # 손실 계산 및 출력
    loss = (y_pred - y).pow(2).sum()
    if t % 100 == 99:
        print(t, loss.item())

    loss.backward()

    # optimizer.step()
    # optimizer.zero_grad()
    with torch.no_grad():
        w1 -= learning_rate * w1.grad
        w2 -= learning_rate * w2.grad

        w1.grad.zero_()
        w2.grad.zero_()


