import matplotlib.pyplot as plt
import pandas as pd

# Чтение данных из CSV файла
data = pd.read_csv('jdbc_rt.csv')

# Уникальные тесты
tests = data['Test'].unique()

# Построение графика
plt.figure(figsize=(12, 8))

for test in tests:
    subset = data[data['Test'] == test]
    plt.plot(subset['Users'], subset['Response time'], marker='o', label=test)

plt.xlabel('Connections', fontsize=18, labelpad=20)
plt.ylabel('Average response time, ms', fontsize=18, labelpad=20)
plt.title('Response time at different users count', fontsize=18)
plt.legend(fontsize=18)
plt.grid(True)
plt.xticks(fontsize=14)
plt.yticks(fontsize=14)
plt.tight_layout()
plt.show()
