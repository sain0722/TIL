import tensorflow as tf

device = 'cuda' if tf.test.is_gpu_available() else 'cpu'
print(device)