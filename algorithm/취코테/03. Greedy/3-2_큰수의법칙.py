# My Code
N, M, K = map(int, input().split())
data = list(map(int, input().split()))
data.sort()
s = 0
cnt = 0
for i in range(M):
    if cnt < K:
        s += data[-1]
    else:
        s += data[-2]
        cnt = 0
    cnt += 1
print(s)

################################################################

# 해설 1
n, m, k = map(int, input().split())
data = list(map(int, input().split()))

data.sort()
first = data[n-1]
second = data[n-2]

result = 0

while True:
    for i in range(k):  # 가장 큰 수를 k번 더하기
        if m == 0:
            break
        result += first
        m -= 1
    if m == 0:
        break
    result += second    # 두번쨰로 큰 수를 한 번 더하기
    m -= 1

print(result)

# 해설 2
# M의 크기가 100억 이상처럼 커진다면 시간초과.
# 수학적 해설
n, m, k = map(int, input().split())
data = list(map(int, input().split()))

data.sort()
first = data[-1]
second = data[-2]

count = int(m / (k+1)) * k
count += m % (k + 1)

result = 0
result += first * count
result += second * (m - count)

print(result)