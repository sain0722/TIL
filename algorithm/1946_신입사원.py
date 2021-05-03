import sys
T = int(input())
for i in range(T):
    N = int(input())
    test = [list(map(int, sys.stdin.readline().split())) for _ in range(N)]

    test.sort(key=lambda x: x[0])
    max_value = test[0][1]
    cnt = 1
    for resume, interview in test[:]:

        if interview < max_value:
            max_value = interview
            cnt += 1

    print(cnt)