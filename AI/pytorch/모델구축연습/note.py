import torch
import torch.nn as nn
import torch.optim as optim
from torch.optim import lr_scheduler
import torch.nn.functional as F
from torchvision.models import vgg16, inception_v3

model = vgg16(pretrained=True)
print(model)
device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

CLASS_NUM = 6
model.classifier = nn.Sequential(
    nn.Linear(25088, 4096),
    nn.ReLU(inplace=True),
    nn.Dropout(0.5),
    nn.Linear(4096, 1024),
    nn.ReLU(inplace=True),
    nn.Dropout(0.5),
    nn.Linear(1024, CLASS_NUM)
)
print(model)

model = model.to(device)

criterion = nn.CrossEntropyLoss()
optimizer = optim.Adam(model.parameters())
exp_lr_scheduler = lr_scheduler.StepLR(optimizer, step_size=3, gamma=0.8)

for t in range(100):

    optimizer.zero_grad()
