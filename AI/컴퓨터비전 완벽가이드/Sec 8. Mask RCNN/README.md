# Mask RCNN

## Segmentation 개요

**Sementic Segmentation**
- 각 개별 클래스별로 masking하여 segmentation
- Pixel-Wise Classification 이라고도 부름.
- 개별 픽셀들이 어떤 클래스에 속하는지를 결정하는 것.

**Mask R-CNN**
- Faster R-CNN + FCN
- Masking을 하기 위해 FCN(Fully Convolutional Network) 추가.
- Faster R-CNN에서는 ROI Pooling을 했는데, segmentation하기에는 정확도가 떨어짐.
- Mask R-CNN에서는 ROI Align을 적용한다.

## Semantic Segmentation FCN의 이해

**FCN**:FCL이 없이 모든 레이어가 Convolutional layer인 것을 뜻함.  

**Semantic Segmentation Encoder-Decoder Model**
- 원본 이미지를 Convolution으로 차원 축소(Dimension Reduction)하여 응축된 정보를 가지고, 이를 다시 복원하면서 필요한 정보를 학습
- 이렇게 학습된 정보를 기반으로 Segmentation 수행


## Mask RCNN의 이해 01

= Faster R-CNN + FCN 

- Faster R-CNN 부분에서 Bounding box regression + Classification 진행
- FCN 영역에서 binary masking 진행.
  - FCN: pixel-wise classification
  - classification은 faster r-cnn 부분에서 진행하고, binary masking은 binary mask prediction으로 수행

#### Mask RCNN
- Faster RCNN 과 FCN 기법 개선 및 결합
  - ROI-Align
  - 기존 Bounding box regression과 Classification에 Binary Mask Prediction 추가
- 비교적 빠른 Detection 시간과 높은 정확도
- 직관적이고 상대적으로 쉬운 구현
- Selective Search를 Neural Network 구조로 변경
  - GPU 사용으로 빠른 학습/Inference
  - End to End Network 학습

#### Mask RCNN 구조
![image](https://user-images.githubusercontent.com/52433248/119763295-37a33680-beea-11eb-904f-ebbe216bc12e.png)

> ROI Pooling: Quantization, Input으로 들어온 Feature map과 Pooling 하려는 Feature map의 사이즈가 맞지 않을 수 있다.  
> 7x7이 목표인데, input이 20x20이면, 7x7로 만들 때, 소수점은 무시하고 2.8배의 차이가 난다고 치면, 2배율로 맞추게 된다.  
> 그런 단점을 보완하기 위하여 ROI Align 기법을 적용.

Classification이 되었다면, 그 해당 영역(Bounding box)에 대해 Binary Mask Prediction을 통해 Masking을 한다.  

![image](https://user-images.githubusercontent.com/52433248/119763671-eb0c2b00-beea-11eb-8983-9647970fb39e.png)

> Masking을 해야 하기 때문에 보다 더 정확해야 하는데, ROI Pooling은 quantization을 하기 떄문에 문제 발생.


## Mask RCNN의 이해 02
