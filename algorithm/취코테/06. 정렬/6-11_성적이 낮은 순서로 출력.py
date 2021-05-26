n = int(input())

arr = []
for i in range(n):
    stud_name_score = input().split()
    arr.append(stud_name_score)
arr.sort(key=lambda x: int(x[1]))

for i in arr:
    print(i[0], end=" ")
