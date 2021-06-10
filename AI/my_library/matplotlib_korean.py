import matplotlib.font_manager as fm

# 'Gothic'
# 'Nanum'
font_lists = [(f.name, f.fname) for f in fm.fontManager.ttflist if 'Gothic' in f.name]

# font_lists의 path 복사 후 저장
path = 'C:\\Windows\\Fonts\\malgunbd.ttf'
plt.rcParams['font.family'] = 'Malgun Gothic'
