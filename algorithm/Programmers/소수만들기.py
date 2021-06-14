from itertools import combinations as cb


def is_prime(n):

    for i in range(2, n):
        if n % i == 0:
            return False
    return True

nums = [1, 2, 7, 6, 4]

answer = sum([True for i in list(cb(nums, 3)) if is_prime(sum(i))])

print(answer)

# answer = 0
# [for i in combi if is_prime(sum(i))]
# for i in combi:
#     target = sum(i)
#     if is_prime(target):
#         answer += 1
# print(answer)