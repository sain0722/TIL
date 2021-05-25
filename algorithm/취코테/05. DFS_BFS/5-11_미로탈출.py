# 5 6
# 101010
# 111111
# 000001
# 111111
# 111111

from collections import deque
import sys

n, m = map(int, sys.stdin.readline().split())
board = [list(map(int, sys.stdin.readline().strip())) for _ in range(n)]
direction = [[1, 0], [-1, 0], [0, 1], [0, -1]]


def bfs(x, y):
    queue = deque()
    queue.append([x, y])

    while queue:
        x, y = queue.popleft()

        for d in direction:
            nx, ny = x+d[0], y+d[1]

            if nx < 0 or nx >= n or ny < 0 or ny >= m:
                continue

            if board[nx][ny] == 0:
                continue

            if board[nx][ny] == 1:
                board[nx][ny] = board[x][y] + 1
                queue.append([nx, ny])
    return board[-1][-1]


print(bfs(0, 0))