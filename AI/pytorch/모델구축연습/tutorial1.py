import torch

# 초기화되지 않은 5x3 행렬 생성
"""
초기화되지 않은 행렬이 선언되었지만, 사용하기 전에는 명확히 알려진 값을 포함하고 있지는 않습니다. 
초기화되지 않은 행렬이 생성되면 그 시점에 할당된 메모리에 존재하던 값들이 초기값으로 나타납니다.
"""
x = torch.empty(5, 3)
print(x)

# 무작위로 초기화된 행렬 생성
x = torch.rand(5, 3)
print(x)

# dtype이 long이고, 0으로 채워진 행렬 생성
x = torch.zeros(5, 3, dtype=torch.long)
print(x)

# 데이터로부터 tensor를 직접 생성
x = torch.tensor([5.5, 3])
print(x)

# 기존 텐서를 바탕으로 새로운 tensor를 만든다.
# 이들 method는 사용자로부터 새로운 값을 제공받지 않은 한, 입력 tensor의 속성들(예: dtype)을 재사용한다.
x = x.new_ones(5, 3, dtype=torch.double)    # new_* 메소드는 크기를 받는다.
print(x)

x = torch.randn_like(x, dtype=torch.float)  # dtype을 Override 한다.
print(x)                                    # 결과는 위와 동일한 크기를 갖는다.

# 행렬의 크기
print(x.size())

"""
                    연산 (Operations)
"""

# 덧셈: 문법1
y = torch.rand(5, 3)
print(x + y)

# 덧셈: 문법2
print(torch.add(x, y))

# 덧셈: 결과 tensor를 인자로 제공
result = torch.empty(5, 3)
torch.add(x, y, out=result)
print(result)

# 덧셈: 바꿔치기(in-place) 방식
# y에 x 더하기
y.add_(x)
print(y)

"""
바꿔치기(in-place) 방식으로 tensor의 값을 변경하는 연산 뒤에는 _가 붙습니다. 
예: x.copy_(y), x.t_() 는 x 를 변경합니다.
"""

# Numpy 스러운 인덱싱 표기 방법 사용 가능
print(x[:, 1])

# 크기변경: tensor의 크기(size)나 모양(shape)을 변경하고 싶다면 torch.view를 사용
x = torch.randn(4, 4)
y = x.view(16)
z = x.view(-1, 8)   # -1은 다른 차원을 유추합니다.
print(x.size(), y.size(), z.size())

# 만약, tensor에 하나의 값만 존재한다면, .item() 을 사용하여 숫자 값을 얻을 수 있음
x = torch.randn(1)
print(x)
print(x.item())


"""
                    Numpy 변환 (Bridge)

Torch Tensor를 Numpy 배열(array)로 변환하거나, 그 반대로 하는 것.
(Torch Tensor가 CpU상에 있다면) Torch Tensor와 Numpy 배열은 메모리 공간을 공유하기 때문에,
하나를 변경하면 다른 하나도 변경된다.

"""

# Torch Tensor를 Numpy 배열로 변환하기
a = torch.ones(5)
print(a)

b = a.numpy()
print(b)

a.add_(1)
print(a)
print(b)

# Numpy 배열을 Torch Tensor로 변경하기
import numpy as np
a = np.ones(5)
b = torch.from_numpy(a)
np.add(a, 1, out=a)
print(a)
print(b)

### CUDA Tensors

# 이 코드는 CUDA가 사용 가능한 환경에서만 실행합니다.
# ``torch.device`` 를 사용하여 tensor를 GPU 안팎으로 이동해보겠습니다.
if torch.cuda.is_available():
    device = torch.device("cuda")          # CUDA 장치 객체(device object)로
    y = torch.ones_like(x, device=device)  # GPU 상에 직접적으로 tensor를 생성하거나
    x = x.to(device)                       # ``.to("cuda")`` 를 사용하면 됩니다.
    z = x + y
    print(z)
    print(z.to("cpu", torch.double))       # ``.to`` 는 dtype도 함께 변경합니다!

