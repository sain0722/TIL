N = 5
# 00:00:00 ~ 5:59:59
cnt = 0
for h in range(N+1):
    for m in range(60):
        for s in range(60):
            time = "{}:{}:{}".format(h, m, s)
            if '3' in time:
                cnt += 1
print(cnt)