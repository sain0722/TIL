import torch
import torch.nn as nn
import torch.functional as F
import torchvision
from torchvision.models import resnet50
from torchsummary import summary

class My_resnet50(nn.Module):

    def __init__(self):
        super(My_resnet50, self).__init__()

        self.conv1 = nn.Conv2d(3, 64, 7, stride=2, padding=3, bias=False)
        self.bn1 = nn.BatchNorm2d(64)
        self.relu = nn.ReLU()
        self.pool = nn.MaxPool2d(3, stride=2, padding=1)

    def forward(self, x):

        x = nn.MaxPool2d(F.relu(self.conv1(x)), (2, 2))

        return x


input = (224, 224, 3)
model = resnet50()
print(model)

my_model = My_resnet50()
print(my_model)
