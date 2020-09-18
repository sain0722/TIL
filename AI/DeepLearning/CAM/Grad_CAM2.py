from keras.applications.resnet50 import ResNet50
from keras.applications.resnet50 import preprocess_input, decode_predictions
from keras.preprocessing import image

import keras.backend as K

import numpy as np
import matplotlib.pyplot as plt
import cv2


def load_image(path, target_size=(224, 224)):
    x = image.load_img(path, target_size=target_size)
    x = image.img_to_array(x)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)

    return x

def generate_cam(img_tensor, model, class_index, activation_layer):
    model_input = model.input

    # A_k : 마지막 conv layer의 출력 feature map
    f_k = model.get_layer(activation_layer).output

    # model의 입력에 대해서, 마지막 conv layer의 출력(f_k)과
    # 모델의 최종 출력(Prediction) 계산.
    get_output = K.function([model_input], [f_k])
    [last_conv_output] = get_output([img_tensor])

    # batch_size가 포함되어 shape가 (1, width, height, k) 이므로
    # (width, height, k)로 shape 변경
    # 여기서 width, height는 마지막 conv layer인 A_k feature map(conv output)의 width와 height
    last_conv_output = last_conv_output[0]

    # Softmax(+ dense) layer와 GAP layer 사이의 weight matrix에서
    # class_index에 해당하는 weights(a^c_k = w^c_k) 계산
    # weights = model.layers[-1].get_weights()[0][:, class_index]

    class_weight_k = model.layers[-1].get_weights()[0][:, class_index]

    # 마지막 conv layer의 출력 feature map(conv_output) 과
    # class_index에 해당하는 weights(w^c_k)를 k에 대응해서 weighted combination을 구함.

    # feature map(last_conv_output)의 (width, height)로 초기화.
    cam = np.zeros(dtype=np.float32, shape=last_conv_output.shape[0:2])
    print(np.shape(cam))
    for k, w in enumerate(class_weight_k):
        cam += w * last_conv_output[:, :, k]

    return cam, class_weight_k

def generate_gradcam(img_tensor, model, class_index, activation_layer):

    """ Grad_CAM """
    model_input = model.input

    # y_c : class_index에 해당하는 CNN 마지막 layer op(Softmax, liner ... 등)의 입력
    y_c = model.outputs[0].op.inputs[0][0, class_index]
    print(y_c)

    # A_k : activation conv layer의 출력 feature map
    A_k = model.get_layer(activation_layer).output

    # model의 입력에 대해서, 마지막 conv layer의 출력(A_k)과
    # 최종 layer activation 입력(y_c)의 A_k에 대한 gradient,
    # 모델의 최종 출력(Prediction) 계산.
    get_output = K.function([model_input], [A_k, K.gradients(y_c, A_k)[0]])
    [conv_output, grad_val] = get_output([img_tensor])

    # batch_size가 포함되어 shape가 (1, width, height, k) 이므로
    # (width, height, k)로 shape 변경
    # 여기서 width, height는 마지막 conv layer인 A_k feature map(conv output)의 width와 height
    conv_output = conv_output[0]
    grad_val = grad_val[0]

    # GAP 연산
    # gradient의 width, height에 대해 평균을 구해서 (1/Z) weights(a^c_k) 계산
    weights = np.mean(grad_val, axis=(0, 1))

    # activation conv layer의 출력 feature map(conv_output)과
    # class_index에 해당하는 weights(a^c_k)를 k에 대응해서 weighted combination 계산

    # feature map(conv_output)의 (width, height)로 초기화.
    grad_cam = np.zeros(dtype=np.float32, shape=conv_output.shape[:2])
    for k, w in enumerate(weights):
        grad_cam += w * conv_output[:, :, k]

    # 계산된 weighted combination 에 ReLU 적용
    grad_cam = np.maximum(grad_cam, 0)

    return grad_cam, weights


if __name__ == "__main__":

    img_width = 224
    img_height = 224

    model = ResNet50(weights='imagenet')
    model.summary()

    img_path = './catdog/husky.jpg'
    img = load_image(img_path, target_size=(img_width, img_height))

    preds = model.predict(img)
    predicted_class = preds.argmax(axis=1)[0]
    # decode the results into a list of tuples (class, description, probability)
    # (one such list for each sample in the batch)
    print("predicted top1 class:", predicted_class)
    print('Predicted:', decode_predictions(preds, top=1)[0])

    conv_name = 'activation_49'

    grad_cam, grad_val = generate_gradcam(img, model, predicted_class, conv_name)
    cam, cam_val = generate_cam(img, model, predicted_class, conv_name)


    # print(grad_cam)

    img = cv2.imread(img_path)
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    img = cv2.resize(img, (224, 224))

    cam = cv2.resize(cam, (224, 224))
    plt.figure(0)
    plt.imshow(img)
    plt.imshow(cam, cmap='jet', alpha=0.5)
    plt.axis('off')
    plt.show()

    grad_cam = cv2.resize(grad_cam, (224, 224))
    plt.figure(1)
    # plt.imshow(img)
    plt.imshow(grad_cam, cmap='jet')
    plt.axis('off')
    plt.show()


    # validate
    activation_layer = conv_name
    print(model.get_layer(activation_layer).output_shape[1:3])
    Z = model.get_layer(activation_layer).output_shape[1] * model.get_layer(activation_layer).output_shape[2]

    print("CAM vs Grad-CAM")
    print("CAM: ", cam_val)
    print("Grad-CAM: ", grad_val * Z)