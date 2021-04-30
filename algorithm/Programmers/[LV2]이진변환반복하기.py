def invert(s):
    cnt = len([0 for i in s if i == '0'])
    new_s = format(len(['1' for i in range(len(s) - cnt)]), 'b')
    return new_s, cnt


def solution(s):
    result, cnt_zero = invert(s)
    cnt_repeat = 1

    while result != "1":
        result, cnt = invert(result)
        cnt_repeat += 1
        cnt_zero += cnt
    answer = [cnt_repeat, cnt_zero]
    return answer