def solution(absolutes, signs):
    return sum([value if sign else -value for value, sign in zip(absolutes, signs)])