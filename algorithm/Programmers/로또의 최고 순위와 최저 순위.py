def solution(lottos, win_nums):
    answer = 0
    for num in lottos:
        if num in win_nums:
            answer += 1
    best = answer + lottos.count(0)
    worst = answer
    rank = {
        6: 1,
        5: 2,
        4: 3,
        3: 4,
        2: 5,
        1: 6,
        0: 6
    }
    return [rank[best], rank[worst]]