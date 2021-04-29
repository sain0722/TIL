# 1번 문제
# 간단한 선형 회귀
# 단순한 데이터를 풀 때에는 모델링도 단순하게 해야함.
#

import numpy as np

import tensorflow as tf
from tensorflow.keras.layers import Dense
from tensorflow.keras.models import Sequential

xs = np.array([-1.0, 0.0, 1.0, 2.0, 3.0, 4.0], dtype=float)
ys = np.array([5.0, 6.0, 7.0, 8.0, 9.0, 10.0], dtype=float)
#
# model = Sequential([
#     Dense(3, input_shape=[1]),
#     Dense(4),
#     Dense(4),
#     Dense(1)
# ])

model = Sequential([
    Dense(1, input_shape=[1])
])

# compile
# MSE

model.compile(optimizer='sgd', loss='MSE')

model.fit(xs, ys, epochs=1200, verbose=0)

pred = model.predict([10.0])

print(pred)

