# 왕실의 나이트
# 8 X 8 좌표 평면, 나이트는 L자 형태로만 이동할 수 있으며, 정원 밖으로는 나갈 수 없다.
# 나이트는 특정한 위치에서 2가지 경우로 이동할 수 있다.
# 1. 수평으로 두 칸 이동한 뒤에 수직으로 한 칸 이동하기
# 2. 수직으로 두 칸 이동한 뒤에 수평으로 한 칸 이동하기
# 나이트가 이동할 수 있는 경우의 수 출력.
# 왕실의 정원에서 행을 표현할 때는 1부터 8, 열을 표현할 때는 a부터 h로 표현한다.

# My Code
dxs = [2, -2, 1, -1, 2, -2, 1, -1]
dys = [1, 1, 2, 2, -1, -1, -2, -2]
ys = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']
input_data = input()

x, y = int(input_data[1])-1, ys.index(input_data[0])

cnt = 0
for dx, dy in zip(dxs, dys):
    nx = x + dx
    ny = y + dy
    if nx < 0 or ny < 0 or nx > 8 or ny > 8:
        continue
    cnt += 1
print(cnt)

# 해설
input_data = input()
row = int(input_data[1])
column = int(ord(input_data[0])) - int(ord('a')) + 1
# 나이트가 이동할 수 있는 8가지 방향 정의
steps = [(-2, -1), (-1, -2), (1, -2), (2, -1), (2, 1), (1, 2), (-1, 2), (-2, 1)]

# 8가지 방향에 대하여 각 위치로 이동이 가능한지 확인
result = 0
for step in steps:
    # 이동하고자 하는 위치 확인
    next_row = row + step[0]
    next_column = column + step[1]
    # 해당 위치로 이동이 가능하다면 카운트 증가
    if next_row >= 1 and next_row <= 8 and next_column >= 1 and next_column <= 8:
        result += 1
print(result)