import tensorflow as tf
import matplotlib.pyplot as plt
import numpy as np

mnist = tf.keras.datasets.fashion_mnist
(training_images, training_labels), (test_images, test_labels) = mnist.load_data()

training_images = training_images.reshape(60000, 28, 28, 1)
test_images = test_images.reshape(10000, 28, 28, 1)

training_images, test_images = training_images / 255.0, test_images / 255.0


model = tf.keras.models.Sequential([

    # 3X3 크기의 64개의 필터, activation = relu, input shape은 28 x 28
    # extra '1' just means that we are tallying using a single byte for color depth
    # gray scale 이므로 1을 쓰는 것.
    # 필터를 64개로 지정한 것은 랜덤으로 지정하는 것은 아니다.
    tf.keras.layers.Conv2D(64, (3, 3), activation='relu',
                           input_shape=(28, 28, 1)),
    tf.keras.layers.MaxPool2D(2, 2),
    tf.keras.layers.Conv2D(64, (3, 3), activation='relu'),
    tf.keras.layers.MaxPool2D(2, 2),
    tf.keras.layers.Flatten(),
    tf.keras.layers.Dense(128, activation='relu'),
    tf.keras.layers.Dense(10, activation='softmax')
])

model.compile(
    optimizer='Adam',
    loss='sparse_categorical_crossentropy',
    metrics=['accuracy']
)

model.summary()
model.fit(training_images, training_labels, epochs=20)

test_loss = model.evaluate(test_images, test_labels)

print(test_loss)

# Visualizing the Convolutions and Pooling

print(test_labels[:100])

