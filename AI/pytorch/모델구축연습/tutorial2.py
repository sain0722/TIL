import torch

"""
        AUTOGRAD: 자동미분
        
        PyTorch의 모든 신경망의 중심에는 autograd 패키지가 있다.
        autograd 패키지는 Tensor의 모든 연산에 대해 자동 미분을 제공
        실행-기반-정의(define-by-run) 프레임워크
        - 코드를 어떻게 작성하여 실행하느냐에 따라 역전파가 정의된다는 뜻
        - 역전파는 학습 과정의 매 단계마다 달라진다
        
        * Tensor
        torch.Tensor 클래스
        - .requires_grad = True 로 설정하면?
            - 그 tensor에서 이뤄진 모든 연산들을 추적(track)하기 시작한다.
            - 계산이 완료된 후, .backward()를 호출하여 모든 변화도(gradient)를 자동으로 계산할 수 있다.
            - 이 Tensor의 변화도는 .grad 속성에 누적된다
        - .detach() : Tensor가 기록을 추적하는 것을 중단하게 하려면, .detach()를 호출
        - with torch.no_grad(): 기록을 추적하는 것(과 메모리를 사용하는 것)을 방지하기 위해, 코드 블럭을 with torch.no_grad(): 로 감싼다.
            - 이는 특히 변화도(gradient)는 필요없지만, requires_grad=True 가 설정되어 학습 가능한 매개변수를 갖는 모델을 평가(evaluate)할 때 유용하다.
        
        - Function 클래스
            Tensor와 Function은 서로 연결되어 있으며, 모든 연산 과정을 부호화(encode)하여 순환하지 않는 그래프를 생성한다.
            - 각 tensor는 .grad_fn 속성을 갖고 있다.
                - 이는 Tensor를 생성한 Function 을 참조하고 있다.
                - 단, 사용자가 만든 Tensor는 예외로, 이 때 grad_fn 은 None 이다.
            - 도함수를 계산하기 위해서는 Tensor의 .backward() 를 호출한다.
                - 만약 Tensor가 스칼라(scalar)인 경우에는 backward에 인자를 정해줄 필요가 없다.
                - 여러 개의 요소를 갖고 있을 때는, tensor의 모양을 gradient의 인자로 지정해 줄 필요가 있다.
                
"""

# tensor를 생성하고, requires_grad=True를 설정하여 연산을 기록한다.
x = torch.ones(2, 2, requires_grad=True)
print(x)

# tensor에 연산을 수행한다.
y = x + 2
print(y)

# y는 연산의 결과로 생성된 것이므로, grad_fn을 갖는다.
print(y.grad_fn)

# y에 다른 연산을 수행한다.
z = y * y * 3
out = z.mean()

print(z, out)

# .requires_grad_( ... ) 는 기존 Tensor의 requires_grad 값을 바꿔치기(in-place)하여 변경한다.
# 입력값이 지정되지 않으면 기본값은 False이다.

a = torch.randn(2, 2)
a = ((a*3) / (a-1))
print(a.requires_grad)
a.requires_grad_(True)
print(a.requires_grad)
b = (a * a).sum()
print(b.grad_fn)

### 변화도(Gradient)
# 역전파(backprop)
# out 은 하나의 스칼라 값만 갖고있기 때문에, out.backward()는 out.backward(torch.tensor(1.))과 동일하다.

out.backward()

# 변화도 d(out)/dx 를 출력
print(x.grad)

# 4.5로 이루어진 행렬이 출력된다.
# 수학적으로 벡터 함수 y⃗ =f(x⃗ ) 에서 x⃗  에 대한 y⃗  의 변화도는 야코비안 행렬(Jacobian Matrix)입니다:
# 일반적으로, torch.autograd 는 벡터-야코비안 곱을 계산하는 엔진이다.
# , 어떤 벡터 v=(v1 v2 ⋯ vm).T 에 대해 v.T⋅J 을 연산합니다.
# 만약 v 가 스칼라 함수 l=g(y⃗ ) 의 기울기인 경우, v=(∂l/∂y1 ⋯ ∂l/∂ym).T 이며, 연쇄법칙(chain rule)에 따라
# 벡터-야코비안 곱은 x⃗ 에 대한 l 의 기울기가 됩니다:


# 벡터-야코비안 곱 예제
x = torch.randn(3, requires_grad=True)

y = x * 2
while y.data.norm() < 1000:
    y = y * 2

print(y)

# 이 경우, y는 더이상 스칼라값이 아니다.
# torch.autograd 는 전체 야코비안을 직접 계산할 수는 없지만, 벡터-야코비안 곱은 간단히 backward 에 해당 벡터를 인자로 제공하여 얻을 수 있다.

v = torch.tensor([0.1, 1.0, 0.0001], dtype=torch.float)
y.backward(v)


# with torch.no_grad(): 로 코드 블럭을 감싸서 autograd가 .requires_grad=True 인 Tensor들의 연산 기록을 추적하는 것을 멈출 수 있다.
print(x.grad)

print(x.requires_grad)
print((x ** 2).requires_grad)

with torch.no_grad():
    print((x ** 2).requires_grad)

# .detach() 호출
# 내용물(content)은 같지만 require_grad가 다른 새로운 Tensor를 가져온다.
print(x.requires_grad)
y = x.detach()
print(y.requires_grad)
print(x.eq(y).all())
