from numpy.random import exponential

num_attackers = 100
learning_rates = [1, 2, 3, 5, 8]
update_periods = [60, 120, 240]
attack_per_attacker = 20

simulation_length = 500


for update_period in update_periods:
    for learning_rate in learning_rates:
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

        with open('dPID-LR' + str(learning_rate) + '-UP' + str(update_period) + '.dat', 'w') as file:
            file.write('time(seconds)\taggragete Attack(Mbps)\n')
            for i in range(len(x)):
                file.write(f'{x[i]}\t{y[i]}\n')
