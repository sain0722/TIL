import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras import layers
from tensorflow.keras.layers import Dense
import numpy as np
from tensorflow import keras


# define NN
# 1 layer with 1 neuron, 1 input value
model = keras.Sequential([keras.layers.Dense(units=1, input_shape=[1])])
# model = Sequential([Dense(units=1, input_shape=[1])])

# Define loss function and optimizer
# measure how well or how badly the guess performed before trying again on the next epoch.
model.compile(optimizer='sgd', loss='mean_squared_error')

# Given a data, known x, unknown y
# We want to know the relationship between them.
xs = np.array([-1.0, 0.0, 1.0, 2.0, 3.0, 4.0], dtype=float)
ys = np.array([-3.0, -1.0, 1.0, 3.0, 5.0, 7.0], dtype=float)

model.fit(xs, ys, epochs=500)

# 새로운 데이터 10을 넣으면, 19에 거의 근접한 값으로 출력된다.
print(model.predict([10.0]))

# Q. 왜 정확히 19라고 예측하지 못하는 걸까?
# A. 데이터가 적어서(6 points), 두 데이터의 상관관계는 예측을 했지만, 아주 높은 확률로 19인 것은 알고 있지만 데이터가 적기 때문에 정확한 예측은 하지 못한다.

########################################################

