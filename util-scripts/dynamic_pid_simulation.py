from scipy.stats import poisson
import matplotlib.pyplot as plt

num_attackers = 100
learning_rate = 50
update_period = 60
attack_per_attacker = 20

simulation_length = 200
x = range(simulation_length)
y = [attack_per_attacker *
     int(num_attackers * poisson.cdf(i % update_period, learning_rate)) for i in x]

plt.plot(x, y)
plt.show()
