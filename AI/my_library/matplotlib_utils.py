import matplotlib.font_manager as fm
import matplotlib.pyplot as plt
import seaborn as sns

# 한글폰트 설정
# 'Gothic'
# 'Nanum'
font_lists = [(f.name, f.fname) for f in fm.fontManager.ttflist if 'Gothic' in f.name]

# font_lists의 path 복사 후 저장
path = 'C:\\Windows\\Fonts\\malgunbd.ttf'
plt.rcParams['font.family'] = 'Malgun Gothic'

# 스타일 설정
sns.set_style('darkgrid')
plt.rcParams['font.family'] = 'Malgun Gothic'
plt.rcParams['font.size'] = 20
plt.figure(figsize=(20, 12))
plt.plot()
# plt.title(f'{score_type} by Defect on {data_type}')
plt.ylim(0.0, 1.0)
