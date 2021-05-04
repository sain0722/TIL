# YOLO(You Only Look Once)

# YOLO의 이해 01 - YOLO 개요와 YOLO Version 1 상세

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

# YOLO의 이해 02 - YOLO 개요와 YOLO Version 2 상세

## YOLO - v1, v2, v3 비교

|항목|v1|v2|v3|
|:---:|:---:|:---:|:---:|
|Feature Extractor|Inception 변형|Darknet 19|Darknet 53|
|Grid당 Anchor Box 수|2개|5개|Output Feature Map당 3개, 서로 다른 크기와 스케일로 총 9개|
|Anchor Box 결정 방법||K-Means Clustering|K-Means Clustering|
|Output Feature Map 크기  (Depth 제외)||13x13|13x13, 26x26, 52x52 3개의 Feature Map 사용|
|Feature Map Scaling 기법|||FPN(Feature Pyramid Network)|

> YOLO v2의 가장 큰 변화: Anchor Box 기반의 Object Detection을 한다!  
> Anchor Box 크기와 Ratio를 구할 때, 고정된 사이즈나 Ratio가 아닌, K-means Clustering을 이용하여 이미지 안에 있는 Object들의 GT Box를 분석하여 군집화를 통해 결정한다.  
> v2: 13x13 Feature map에 Anchor Box 5개가 개별 Grid 별로 적용이 됨. (Depth 제외)  
> v3: 13x13, 26x26, 52x52 3개의 Feature map으로 각각 서로다른 output size를 가지고 Feature Map Scaling -> FPN을 사용함.  

## YOLO-v2 Detection 시간 및 성능

![image](https://user-images.githubusercontent.com/52433248/116510275-a1d2b680-a8ff-11eb-8ec8-1bb8d5be6a2b.png)


> SSD보다 더 빠르다.  
> Detection 성능은 SSD보다 낮다.  
> Small Object에서도 성능차이  
> 

## YOLO v2의 특징

- Batch Normalization
- High Resolution Classifier : Network의 Classifer 단을 보다 높은 resolution(448x448)로 fine tuning.
- **13x13 feature map 기반에서 개별 Grid cell별 5개의 Anchor box에서 Object Detection**
- Darknet-19 Classification 모델 채택
- 서로 다른 크기의 image들로 Network 학습

> Batch Normalization을 네트워크에 붙여 사용하였음.  
> v2에서도 처음엔 224x224로 하다가, 448x448로 fine tuning을 하여 좀 더 resolution을 높였더니 mAP 상승!  
> SSD하고 유사하게, Anchor Box 기반으로 Object Detection을 진행함. SSD와 다른 점은 Multi-Scale Feature Map이지만, YOLO-v2는 13x13 Feature map 기반.  
> 독자적인 Darknet-19 모델 채택  
> 작은 Object에 대한 Detection 성능을 높이기 위해, 서로 다른 크기의 이미지를 학습시키는 것으로 해결하려는 노력  
> 

## YOLO v2 Network 구조

![image](https://user-images.githubusercontent.com/52433248/116510328-b616b380-a8ff-11eb-9cfc-c97a51725adb.png)

> v1의 Fully Connected 부분이 없어지고, Convolution 구조로 바뀜.  
> 13x13 Feature map에 대해서 Anchor box별로 Object Detection 수행.
> 

## YOLO v2 Anchor Box로 1 Cell에서 여러개 Object Detection

SSD와 마찬가지로 1개의 cell에서 여러 개의 Anchor를 통해 개별 Cell에서 여러 개 Object Detection 가능  
K-Means Clustering을 통해 데이터 세트의 이미지 크기와 Shape Ratio에 따른 5개의 군집화 분류를 하여 Anchor Box 계산.  

![image](https://user-images.githubusercontent.com/52433248/116510837-861be000-a900-11eb-9c5f-0e451a2238f8.png)


> 한 Cell에서 여러 개의 Object Detection이 가능.  
> 이전 v1에서는 Grid Cell이 Object Detection의 주체였기 때문에, 한 Cell에서 하나의 Object만 Detect 가능했었음.  
> 이제는 Anchor Box 기반으로 하기 때문에, 각 cell의 중심을 기준으로 5개의 Anchor Box가 만들어진다.  
> Anchor Box의 크기와 Ratio는 K-Means Clustering을 통해 GT의 크기들을 군집화하여 결정한다.  
> 

## YOLO v2 Output Feature Map

> 13x13 각 Grid Cell 별로 5개의 Anchor Box 생성  
> Anchor Box에서는 Bounding Box별 25개의 정보를 담고 있음.  
> 5개니까 125개가 됨 = depth=125 -> 13 x 13 x 125 (Output Feature map)
> 
```
$$
Box coordinates(t_x, t_y, t_w, t_h), 
Objectness Score(P_o), 
Class Scores(p_1, ... , p_c) 
X B
$$
```

# YOLO의 이해 03 - YOLO 개요와 YOLO Version 3 상세

## One Stage Detector

시간순으로 정렬  
YOLO v1 -> SSD -> YOLO v2 -> (Feature Pyramid Network) -> RetinaNet -> YOLO v3  

> FPN : Feature Extractor  
> FPN + Focal Loss = RetinaNet  
> YOLO v3: 앞의 모델들의 장점을 융합한 것  
> 

## YOLO v3 특징
- Feature Pyramid Network와 유사한 기법을 적용하여 3개의 Feature Map Output에서 각각 3개의 서로 다른 크기와 scale을 가진 anchor box로 Detection
- 보다 높은 Classification을 가지는 Darknet-53
- Multi Labels 예측: Softmax가 아닌 Sigmoid 기반의 Logistic Classifier로 개별 Object의 Multi Labels 예측  

> 서로 다른 크기의 Feature Map (13x13, 26x26, 52x52) 3개를 만들고, 서로 다른 크기를 가진 Anchor Box를 3개씩 만들게 된다.  
> 총 9개의 서로 다른 Anchor Box가 만들어지게 됨.
> Darknet-53 채택  
> Multi Labels 예측: Object Detection은 Class가 하나인 경우가 많지만, Open Image같은 경우에는 person-woman과 같은 multi-labels이 있음.  
> 간단히 말해서 Softmax -> Logistic Regression
> 

## YOLO v3 Network 구조

 ![YOLO-v3_Network](https://miro.medium.com/max/1000/1*d4Eg17IVJ0L41e7CTWLLSg.png)

> DarkNet에도 ResNet과 비슷하게 Skip-Connection과 같이 되어있는 부분이 일부 있음.  
> 61 -> 79 layer로 갈 때, Feature map size가 줄어들어있음.  
> 82번 layer: 13x13 Feature Map. 여기에서 다른 유형의 3개의 Anchor Box 생성.  
> 79번에서 다시 횡으로 가면, upsampling이 된다. -> Feature Map의 크기가 두배로 커지게 됨.  
> 그 다음에 61번 layer에서 skip-connection이 되면서, 줄어들기 전의 정보가 들어가게 된다.  
> 그리고 그것들이 다 합쳐져서 91번 layer에서 94번 layer까지 가서 26x26의 Feature Map.  
> 그리고 또 upsampling해서 36번 layer에서 정보를 전달한다.  
> 106번 layer: 52x52 Feature Map.  

> 주로, YOLO는 416x416으로 Input Image를 받는다.  

## YOLO와 SSD의 비교

> 유사한 부분이 많지만, Feature Pyramid 기법으로 Skip-Connection으로 앞단의 Layer에서 위치정보를 전달해주는 차이가 있음.
> 

## YOLO v3 Output Feature Map

각 Feature Map마다 3개의 서로 다른 Anchor box.  
```
- Box 좌표(t_x, t_y, t_w, t_h)
- Objectness Score(p_o)
- Class Scores(p_1 ... p_c)
  - COCO dataset 사용으로 c=80, 80개의 스코어
```
- 13x13 Feature Map
  - 13x13x3x85
- 26x26 Feature Map
  - 26x26x3x85
- 52x52 Feature Map
  - 52x52x3x85

## Darknet-53 특성

> v2->v3으로 가면서, 속도를 조금 낮추더라도 성능을 개선시켜보자는 의도.  
> ResNet과 유사한 구조. 왜 DarkNet을 별도로 만들었는가??  
> Detection 시간을 줄이기 위한 노력.  
> ResNet-152와 성능은 거의 유사한데, 속도가 훨씬 더 빠르다.  
> 

## Training

multi-scale

# GPU를 활용한 Object Detection 모델의 Training 수행 시 유의사항

대량의 이미지 학습 시 유의사항  
- 개별 이미지를 배열(Tensor)로 변환해야함.
- 예를 들어, 500x500 이미지라면, 500x500x3 -> 250k x 3
- 개별 이미지 하나씩 넣으면, 메모리 문제 발생!
- GPU를 사용하는 이유 중 하나가, __batch 처리__가 가능하다.
- 이미지 개수가 1000개라고 하면, 1000 x [500x500x3] 을 CNN에 한방에 집어넣을 수 있다.
- 하지만, 이건 메모리 초과. -> Keras에서는 `fit_generator()`를 이용하여 학습
- 1000장의 이미지가 있으면, batch_size=10 -> 10장의 이미지만 변환을 해서 네트워크의 입력으로 넣고, 이 작업을 100회 반복.


## Python Generator

![image](https://user-images.githubusercontent.com/52433248/116851817-17a38e80-ac2e-11eb-9fdd-cb28d7014be1.png)

- Caller
  - 일반 함수
    - 일반 함수 def A는 자신의 로직을 수행하고, return을 caller에게 한다.
    - 가령, 일반함수가 리턴하는 값이 10만개(큰 값)라고 한다면, Caller가 10만개를 받아서 처리를 하기에는 메모리 사이즈가 너무 부족하다.
    - return하면 종료.
  - Generator 함수
    - Caller가 함수를 호출.
    - 작업을 수행하다가, yield라는 키워드를 만남. yield에서 1000개의 array라고 지정이 되어있다면, 1000개에 해당하는 값을 caller에게 반환.
    - caller는 1000개를 처리한 뒤에 함수를 다시 호출하여 다음 step으로 감.
    - 다시 yield를 만나면, caller로 감.
    - Generator는 Caller에게 반환을 해도, 종료되지 않음.
    - __함수 내 로직을 수행 후 값을 반환하고 종료하는 것이 아니라, yield로 값을 순차적으로 반환하면서 메모리를 절약할 수 있게 만듦.__

## Keras fit_generator()를 이용한 학습

Python Generator를 적용.  
- batch_size를 늘리면 속도가 무조건 빨라진다? -> NO
- 이미지 사이즈가 하드웨어의 제약을 받듯이, batch size도 하드웨어의 제약을 받음.
- batch_size = 10 -> 100
  - 100장의 이미지를 네트워크에 집어넣으면, 병목이 걸림
  - batch size 설정은 cpu코어 및 gpu 메모리 등의 균형을 맞춰서 설정

#### Data Generator

```python
from keras.preprocessing.image import ImageDataGenerator

# 학습용/검증용 data generator 생성
train_datagen = ImageDataGenerator(rescale=1./255)
train_generator = train_datagen.flow_from_directory('/path/to/train_image', target_size=(240, 240), batch_size=10, class_mode='categorical')
valid_datagen = ImageDataGenerator(rescale=1./255)
valid_generator = train_datagen.flow_from_directory('/path/to/valid_image', target_size=(240, 240), batch_size=10, class_mode='categorical')

# 모델 생성
model = Sequential([
  Conv2D(32, (3, 3), activation='relu', input_shape=(240, 240, 3)),
  ...
  Dense(3, activation='softmax')
])
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

# data generator를 이용한 학습
model.fit_generator(train_generator, steps_per_epoch=100, epochs=10, validation_data=valid_generator, validation_steps=2)
```

## Object Detection 모델에서 Batch size 설정

- Batch size가 크다고 무조건 수행 속도를 향상시키지 않음.
- Batch size가 너무 크면 GPU 메모리 부족 문제를 가져오고 학습이 되지 않음.(OOM, Segment Fault)
- CPU 4코어, GPU P100 서버 기준, Batch size 4~8이 적합.
