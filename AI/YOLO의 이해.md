# YOLO(You Only Look Once)

## YOLO의 이해 01 - YOLO 개요와 YOLO Version 1 상세

`Speed + Accuracy`  

*Abstract*
- YOLO는 충분히 빠르고, 충분히 좋은 성능을 가지고 있습니다. 단지, RetinaNet에 비해 mAP가 떨어지는 것은, COCO dataset의 IoU(0.5~0.95) 평가기준방식이기 때문입니다.  
- Pascal VOC와 같이 0.5를 기준으로 한다면 YOLO는 RetinaNet보다 3.8배 가량 빠릅니다. 

|버전|내용|
|:---:|---|
|v1|빠른 Detection 시간, 그러나 낮은 정확도|
|v2|수행시간과 성능 모두 개선|
|v3|수행시간은 조금 느려졌으나 **성능 대폭 개선**|

## YOLO - v1

- Yolo-v1은 입력 이미지를 S X S Grid로 나누고, **각 Grid의 Cell이 하나의 Object에 대한 Detection 수행**
- 각 Grid Cell이 2개의 Bounding Box 후보를 기반으로 Object의 Bounding Box를 예측

수행속도에 굉장히 많은 집중을 하여 연구 진행.  
`Feature map`에 기준한게 아니라, `입력 이미지의 크기`를 기준으로 잡았다.  
예를 들어 448x448의 이미지를 7x7의 그리드로 나누고, 각 개별 그리드 cell이 Object Detection을 담당한다.  
각 개별 cell은 2개의 Bounding box 후보를 갖고, Object 중심점에 있는 Cell이 2개의 B.Box를 이용해서 Object Detection 수행.  
But, Anchor Box기반이 아님. 해당 Cell이 Box를 추측을 해나가는 것.  

***원본 이미지에 Grid를 나누고, 개별 Cell에서 2개의 B.Box를 이용을 해서 예측을 하는 방식!***

- 이런 방식은 필연적으로 문제가 생길 수 밖에 없다!
  - 각 cell이 하나의 Object만 Detect한다.
  - 하나의 cell에 여러 object가 있다면 문제 발생.

![image](https://user-images.githubusercontent.com/52433248/116507092-8fee1500-a8f9-11eb-999a-f1ca99250f5c.png)  

*하나의 Grid cell에는 30개의 정보가 담겨 있다.*  
  - A. 2개의 Bounding Box 후보의 좌표와 해당 Box별 Confidence Score
    - Confidence Score, w, y, w, h 5개 X 2 = 10개
  - B. 클래스 확률, Softmax 값 20개
  - 따라서, Grid cell에 30개이므로, 마지막 레이어가 7x7x30의 형태로 정보를 담고 있음.
  - v2부터는 구조가 바뀜.

## NMS(Non Max Suppression)

1-stage Detector들은 굉장히 많은 Bounding Box를 만들 수 밖에 없다. -> Region Proposal과 같이 걸러주는 작업이 없기 때문이다.  
따라서, NMS를 이용하여 필요없는 Box를 거른다.  

**개별 Class 별 NMS 수행**
```
1. 특정 Confidence 값 이하는 모두 제거
2. 가장 높은 Confidence값을 가진 순으로 Bbox 정렬
3. 가장 높은 Confidence를 가진 Bbox와 IOU와 겹치는 부분이 IOU Threshold보다 큰 Bbox는 모두 제거
4. 남아 있는 Bbox에 대해 3번 Step 반복
```
*Object Confidence와 IOU Threshold로 Filtering 조절*

> Confidence 값에 따라 내림차순으로 정렬된 Bbox들을 순차적으로 탐색한다. 탐색 방법은 가장 높은 Confidence 값을 갖는 Bbox와의 IOU가 특정 Threshold보다 큰 Bbox를 모두 제거한다.  
> 더 많은 B.Box를 제거하기 위해 Confidence를 높이거나, IOU Threshold를 낮추어서 Filtering을 조절한다.  
 
## Issue
Detection 시간은 빠르나, 성능이 떨어짐. 특히 작은 Object에 대한 성능이 나쁨.

