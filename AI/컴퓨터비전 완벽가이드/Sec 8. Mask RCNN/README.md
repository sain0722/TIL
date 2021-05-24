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

#### Mask RCNN 구조


## Mask RCNN의 이해 02
