d = [1, 3, 2, 5, 4]
budget = 9
d.sort()

result = 0
for i in d:
    budget -= i
    if budget < 0:
        break
    result += 1

print(result)