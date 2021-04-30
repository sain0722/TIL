import re

def solution(new_id):
    new_id = new_id.lower()
    new_id = re.sub('[~!@#$%^&*()=+\[\]{}:?,<>?\/]', '', new_id)
    while True:
        temp = re.sub('[.]{2}', '.', new_id)
        if new_id == temp:
            break
        new_id = temp
    if new_id and new_id[0] == '.':
        new_id = new_id[1:]
    if new_id and new_id[-1] == '.':
        new_id = new_id[:-1]
    if not new_id:
        new_id = 'a'
    if len(new_id) >= 16:
        new_id = new_id[:15]
        if new_id[-1] == '.':
            new_id = new_id[:-1]
    if len(new_id) <= 2:
        while len(new_id) < 3:
            new_id += new_id[-1]

    return new_id