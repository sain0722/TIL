import numpy as np
from tensorflow.keras.applications.resnet50 import ResNet50
from tensorflow.keras.applications.vgg16 import VGG16
from keras.preprocessing import image
from imagenet_utils import preprocess_input

model = ResNet50(weights='imagenet')
# model = VGG16(weights='imagenet', include_top=False)
# model.summary()

img_path = 'elephant.jpg'
img = image.load_img(img_path, target_size=(224, 224))
x = image.img_to_array(img)
x = np.expand_dims(x, axis=0)
x = preprocess_input(x)

features = model.predict(x)
print(features)