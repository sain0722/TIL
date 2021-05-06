# RetinaNet

## RetinaNet의 특징

- One Stage Detector의 빠른 Detection 시간의 장점을 가지면서 One Stage Detector의 Detection 성능 저하 문제 개선
- 수행 시간은 YOLO나 SSD보다 느리지만, FasterRCNN보다 빠름.
- 수행 성능은 타 Detection 모델보다 뛰어남. 특히 타 One Stage Detector보다 작은 오브젝트에 대한 Detection 능력이 뛰어남.

__Focal Loss + Feature Pyramid Network__

> 2017년도, Facebook AI Research 발표.  
> 당시 분위기가 One Stage Detector가 빠른건 좋은데, 성능이 너무 뒤쳐진다는 인식이 많았다.  
> RetinaNet이 나오면서, One Stage Detector가 빠른 Detection 성능과 더 정확한 Object Detection이 가능하다는 인식이 생겼다.
> 작은 Object에 대한 Detection 성능을 개선한게 가장 큰 특징.  
> Focal Loss: CrossEntropy를 대체한, 가중치를 부여한 새로운 Loss Function  
> Feature Pyramid Network를 Backbone에 적용을 함.  
> 

## RetinaNet 성능

![image](https://user-images.githubusercontent.com/52433248/117247797-e4edd600-ae79-11eb-92da-59c3e154c33b.png)

> 세밀한 Detect는 YOLO보다 RetinaNet이 더 낫다.
> 

# RetinaNet의 이해 - Focal Loss

## Focal Loss의 필요성 - Cross Entropy

> Focal Loss: Cross Entropy의 변형  
> CrossEntropy: 계산된 Loss값을 줄이는 방향으로 계속 학습이 된다.  
> 이렇게 Loss값을 줄이는 방법으로는, 잘하는 애들을 더 잘하게, 못하는 애들을 조금만 못하게 하는 방법이 있다.
> 

## Cross Entropy 곡선
![image](https://user-images.githubusercontent.com/52433248/117248461-f7b4da80-ae7a-11eb-9ab3-14fbc9ebab03.png)

- p<sub>i</sub> = 예측 확률값
- 0.6 이상 = well classified라고 가정할 수 있음.
- loss의 값이 0.2? 1.2? -> case by case, 절대적으로 작은값, 큰값이라고 보기 힘들다.
- 이것은 Object Detection에서는 문제가 될 수 있음. -> `Class Imbalance` 이슈

## Object Detection의 Class imbalance 이슈

![image](https://user-images.githubusercontent.com/52433248/117249849-3b104880-ae7d-11eb-8cd5-ffd852c7e3be.png)

> Detect할 대상보다 Background가 대부분의 영역을 차지함. (하늘, 바다)  
> One Stage Detector같은 경우는 Anchor Box를 사용을 하는데, Object가 있을만한 곳을 추천받기 때문에, 굉장히 많은 Anchor Box가 생성됨.  
> 결국 대부분의 Anchor들이 Background에 치중되게 됨.  
> 그 결과, 중요한 건 foreground(positive) exmaple인데, background(negative) example에 압도가 되어버림.  

- Easy Example: 찾기 쉬운 대상들. Background나 크고 선명한 대상 오브젝트. 이미 높은 예측 확률을 가지고 있음.
- Hard Example: 찾기 어려운 대상들. 작고 형태가 불분명하여 낮은 예측 확률을 가지고 있음.

#### One Stage Object Detector의 Class imbalance 문제

- Easy Example이 많고 Hard Example이 적은 Class imbalance 이슈는 Object Detectcion이 안고 있는 고유 문제.
- Two Stage Detector같은 경우는 Region Proposal Network에서 오브젝트가 있을만한 높은 확률 순으로 필터링을 먼저 수행할 수 있음.
- 하지만 One Stage에서는 Region Proposal과 Detection을 같이 수행하므로 매우 많은 오브젝트 후보들에 대해서 Detection을 수행해야 하므로 Class imbalance로 인한 성능 저하 영향이 큼.

## Class imbalance 상태에서 Cross Entropy의 문제점

예측이 불확실한 Object들을 개선하려는 방향성으로 학습이 진행되는 것이 아니라 이미 높은 예측 확률을 가진 easy example 즉, Background나 확실한
Object들에 대해서 더 정확한 예측을 하기 위해 학습이 진행됨.


|분류|개수|Loss|
|---|---|---|
|Easy Example|1|0.1|
|Easy Example|10000|__1000__|
|Hard Example|1|2|
|Hard Example|50|__100__|

- 약 10배 차이로 10000개의 easy example들이 Loss가 더 크다.

> easy example은 처음부터 낮은 loss를 갖고있음.  
> hard example은 높은 loss를 갖고있음. -> loss를 감소시키려는 방향으로 학습이 진행될 것.  
> 하지만, easy example이 굉장히 많아버리니까 easy example의 loss가 더 높아지는 현상이 발생.  
> 예측이 불확실한 Object들에 대한 개선이 아닌, 이미 높은 예측확률을 가진 easy example들에 대해서 더 정확한 예측을 하기 위해 학습이 진행되는 문제.
> 

## Class imbalance 해결 방안

- 기존 One Stage Detector의 해결 방안
  - 학습 시 경험치에 기반한 샘플링이나 data augmentation에 집중
  - foreground와 background의 비율을 섞는 것
  - 작은 object같은 경우, crop을 하고, 다시 확대를 시킴.
  - 사실상 맨땅에 해당수준.

- RetinaNet의 해결 방안
  - 동적으로 Cross Entropy를 해결할 방법을 찾자.

##  Focal Loss - Cross Entropy에 가중치를 부여

![image](https://user-images.githubusercontent.com/52433248/117251803-179acd00-ae80-11eb-97a1-6e20466c6c33.png)

- 굉장히 확실할 경우
  - p<sub>i</sub> = 0.99 라고 가정,
  - &gamma; 제곱을 하게되면 값이 훨씬 더 작아짐.
- 전반적으로 CrossEntropy에 비하면 떨어짐.
- 상대적으로 확률이 낮은 쪽은 덜 떨어지고, 확률이 높은 쪽은 훨씬 더 많이 떨어진다.


## Focal Loss는 확실한 Object들에 매우 작은 loss를 부여


- 쉽게 판별 가능한 objecte들에 CrossEntropy보다 훨씬 더 낮은 loss를 부여한다.
- p = 0.9, 쉽게 판별, CE 대비 384배 작은 loss
- p = 0.99, 매우 쉽게 판별, CE 대비 40,000배 작은 loss
- p = 0.1, 잘못 판별, CE 대비 5배 작은 loss
  - loss가 더 낮아지긴 했지만, 다른 경우의 수보다 적게 낮아졌음.

# RetinaNet의 이해 - Feature Pyramid Network

## FPN(Feature Pyramid Network)

![image](https://user-images.githubusercontent.com/52433248/117252663-3fd6fb80-ae81-11eb-9301-e0981e01018e.png)

서로 다른 크기를 가지는 Object들을 효과적으로 Detection하기 위하여 bottom up과 top down 방식으로 추출된 feature map들을 lateral connection으로 연결하는 방식.

- [a]: 이미지의 사이즈를 계속 줄여나가는 방식, 큰 Object를 Detection할 순 있지만, Computing & Detecting 시간이 너무 오래걸림.
- [b]: Feature map 기반. 너무 최상위에 있는 Feature map은 굉장히 많이 추상화가 되어있고, 핵심적인 feature들을 가지고는 있지만, 위치적인 특성들을 많이 잃기 때문에 정확도가 떨어짐.
- [c]: 각 layer에 있는 feature map들을 prediction한다. ex) SSD
  - 각 layer들이 가지고있는 특성들이 상위로 갈 수록 소실되는 부분들 때문에 Detection 정확도가 떨어지는 문제
- [d]: 위 문제를 해결하기 위해 보완성을 추구한다.
  - bottom up: 아래에서부터 위로 올라가서, 일반적인 CNN Classification 방식.
  - 위로 올라갈수록 낮아지는 Resolution을 Skip Connection으로 보완한다.
  - upsampling을 하면서, Skip Connection으로 보완이 된 Feature map을 통해 predict를 한다.

