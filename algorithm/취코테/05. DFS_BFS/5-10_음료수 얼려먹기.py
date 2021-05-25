# N X M 크기의 얼음 틀이 있다.
# 구멍이 뚫려 있는 부분은 0, 칸막이가 존재하는 부분은 1로 표시된다.
# 구멍이 뚫려 있는 부분끼리 상, 하, 좌, 우로 붙어 있는 경우 서로 연결되어 있는 것으로 간주한다.
# 얼음 틀의 모양이 주어졌을 때 생성되는 총 아이스크림의 개수를 구하는 프로그램을 작성하시오.

# 문제 해설
# 1. 특정한 지점의 주변 상, 하, 좌, 우를 살펴본 뒤에 주변 지점 중에서 값이 '0'이면서, 아직 방문하지 않은 지점이 있다면 해당 지점을 방문한다.
# 2. 방문한 지점에서 다시 상, 하, 좌, 우를 살펴보면서 다시 방문을 진행하면, 연결된 모든 지점을 방문할 수 있다.
# 3. 1~2번 과정을 모든 노드에 반복하며 방문하지 않은 지점의 수를 센다.
# 4 5
# 00110
# 00011
# 11111
# 00000

def dfs(x, y):

    # 주어진 범위를 벗어나면 즉시 종료
    if x < 0 or x >= N or y < 0 or y >= M:
        return False

    # 현재 노드를 아직 방문하지 않았다면
    if graph[x][y] == 0:
        # 해당 노드 방문 처리
        graph[x][y] = 1

        # 상, 하, 좌, 우의 위치도 모두 재귀적으로 호출
        dfs(x-1, y)
        dfs(x, y-1)
        dfs(x+1, y)
        dfs(x, y+1)
        return True
    return False

N, M = map(int, input().split())
graph = [list(map(int, input())) for i in range(N)]

result = 0
for i in range(N):
    for j in range(M):
        # 현재 위치에서 dfs 수행
        if dfs(i, j):
            result += 1

print(result)