# 이미지 분류
# 데이터 전처리단계에서 Train / Validation 나누기
# Model Checkpoint
# 데이터 전처리 - 정규화
# 0 ~ 1 사이로 Normalization을 하는 이유 : 성능상승(96 -> 98), 수렴하는 속도가 빨라져서 정확도 상승
# 원핫인코딩
# 활성화 함수
# 선형 X 선형 = 선형 이므로, (Dense)
# 선형함수 사이사이에 비선형함수를 넣어준다(Activation Function)

# Fashion MNIST 데이터 셋에 대한 분류기 생성 테스트는 10 개의 클래스를 분류할 것으로 예상하고
# 입력 모양은 Fashion MNIST 데이터 세트의 기본 크기여야 합니다. 28 x 28 단색.
# 데이터 크기를 조정하지 마십시오. input_shape는 (28, 28)을 input shape으로만 사용합니다.

import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf

from tensorflow.keras.layers import Dense, Flatten, ReLU
from tensorflow.keras.models import Sequential
from tensorflow.keras.callbacks import ModelCheckpoint
from tensorflow.keras.datasets import mnist

(x_train, y_train), (x_test, y_test) = mnist.load_data()

x_train = x_train / 255.0

model = Sequential([
    Flatten(input_shape=(28, 28)),
    Dense(512, activation='relu'),
    Dense(256, activation='relu'),
    Dense(128, activation='relu'),
    Dense(64, activation='relu'),
    Dense(32, activation='relu'),
    Dense(10, activation='softmax')
])

model.compile(optimizer='adam', loss='sparse_categorical_crossentropy')

history = model.fit(x_train, y_train, epochs=6, verbose=0)

