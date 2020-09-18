from datetime import datetime


st = datetime.now()
a = 0
for i in range(1000):
    for j in range(1000):
        a += 10
end = datetime.now()

print(end-st)