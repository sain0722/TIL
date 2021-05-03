# My Code
n, k = 25, 3
count = 0
# 1. n에서 1을 뺀다.
# 2. n을 k로 나눈다.
while n != 1:
    if n % k == 0:
        n /= k
    else:
        n -= 1
    count += 1

print(count)

# 해설 1

n, k = 25, 3
result = 0
while n >= k:
    while n % k != 0:
        n -= 1
        result += 1
    # K로 나누기
    n //= k
    result += 1

while n > 1:
    n -= 1
    result += 1
print(result)


# 해설 2

n, k = 25, 3
result = 0

while True:
    # n == K로 나누어떨어지는 수가 될때까지 1씩 뺴기
    target = (n // k) * k
    result += (n - target)
    n = target
    # N이 K보다 작을 때, 반복문 탈출(더이상 나눌 수 없을 때)
    if n < k:
        break
    result += 1
    n //= k

# 마지막으로 남은 수에 대하여 1씩 빼기
result += (n-1)
print(result)