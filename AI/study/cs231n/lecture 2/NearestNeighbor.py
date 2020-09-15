import numpy as np

class NearestNeighbor:
    def __init__(self):
        pass

    # 단지 학습 데이터를 기억하는 역할을 한다.
    def train(self, X, y):
        """
        X is N x D where each row is an example.
        Y is 1-dimension of size N
        """
        # the nearest neighbor classifier simply remembers all the training data.
        self.Xtr = X
        self.ytr = y

    def predict(self, X):

        num_test = X.shape[0]

        """
        X is N x D where each row is an example we wish to predict label for
        """
        # lets make sure that the output type matches the input type
        Ypred = np.zeros(num_test, dtype= self.ytr.dtype)

        # loop over all test rows

        for i in range(num_test):
            # find the nearest training image to the i'th test image
            # using the L1 distance (sum of absolute value differences)
            distances = np.sum(np.abs(self.Xtr - X[i, :]), axis=1)
            min_index = np.argmin(distances)
            Ypred[i] = self.ytr[min_index]
        return Ypred


NN = NearestNeighbor()

X = np.array([
    [0, 1, 2],
    [1, 2, 3],
    [3, 4, 5]])

y = np.array([1, 3, 5])

NN.train(X, y)
pred_result = NN.predict(X)
print(pred_result)


"""
Q1. With N examples, how fast are training and prediction?

: Train = O(1), 데이터를 기억만 하면 된다.
  Predict = O(N), N개의 학습 데이터 전부를 테스트 이미지와 비교해야 한다.

실제로 우리는, "Train Time"은 조금 느려도 되지만, "Test Time"이 빠르게 동작하기를 원합니다.
따라서, NN은 real world에 적용하기에 적절하지 않습니다.
"""