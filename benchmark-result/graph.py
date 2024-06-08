import matplotlib.pyplot as plt
import pandas as pd

# Чтение данных из CSV файла
data = pd.read_csv('test_results.csv')

# Уникальные тесты
tests = data['Test'].unique()

# Построение графика
plt.figure(figsize=(12, 8))

for test in tests:
    subset = data[data['Test'] == test]
    plt.plot(subset['Parameters range'], subset['Requests per second'], marker='o', label=test)

plt.xlabel('Parameters range', fontsize=18, labelpad=20)
plt.ylabel('Requests per second', fontsize=18, labelpad=20)
plt.title('Throughput at different request parameters, 16 threads', fontsize=18)
plt.legend(fontsize=18)
plt.grid(True)
plt.xticks(rotation=30, fontsize=14)
plt.yticks(fontsize=14)
plt.tight_layout()
plt.show()
