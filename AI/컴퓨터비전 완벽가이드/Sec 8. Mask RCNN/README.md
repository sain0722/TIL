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

## Mask RCNN의 이해 02
