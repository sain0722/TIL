# My Code
n, m = map(int, input().split())
cards = [list(map(int, input().split())) for _ in range(n)]
result = max([min(card) for card in cards])
# result = 0
# for card in cards:
#     if min(card) > result:
#         result = min(card)
print(result)

################################################################

# 해설 1
# min() 함수 이용

result = 0
for i in range(n):
    data = list(map(int, input().split()))
    min_value = min(data)
    result = max(result, min_value)
print(result)


# 해설 2
# 2중 반복문
for i in range(n):
    data = list(map(int, input().split()))
    min_value = 10001
    for a in data:
        min_value = min(min_value, a)
    result = max(result, min_value)
print(result)
