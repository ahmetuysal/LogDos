from numpy.random import exponential
import matplotlib.pyplot as plt

num_attackers = 100
learning_rate = 1
update_period = 60
attack_per_attacker = 20

simulation_length = 200

x = []
y = []

current_time = 0
current_number_of_learned_paths = 0

while current_time < simulation_length:
    time_to_learn = exponential(1 / learning_rate)
    if current_number_of_learned_paths < num_attackers:
        current_number_of_learned_paths += 1

    if current_time % update_period > (current_time + time_to_learn) % update_period:
        current_number_of_learned_paths = 0

    current_time += time_to_learn
    x.append(current_time)
    y.append(current_number_of_learned_paths * attack_per_attacker)

plt.plot(x, y)

plt.show()
