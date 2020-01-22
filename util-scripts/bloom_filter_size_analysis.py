import math


def optimal_number_of_bits(expected_insertions, false_positive_rate):
    if false_positive_rate == 0:
        return -float('inf')
    else:
        return -expected_insertions * math.log(false_positive_rate) / (math.log(2) * math.log(2))


def optimal_num_of_hash_functions(expected_insertions, num_bits):
    # (m / n) * log(2), but avoid truncation due to division!
    return max(1, int(round(num_bits / expected_insertions * math.log(2))))


def size_in_bits(expected_insertions, false_positive_rate, number_of_hash_functions=None):
    if number_of_hash_functions is None:
        number_of_hash_functions = optimal_num_of_hash_functions(
            expected_insertions, optimal_number_of_bits(expected_insertions, false_positive_rate))

    size_in_bits = math.ceil(1 / (1 - (1 - false_positive_rate ** (1 / number_of_hash_functions))
                                  ** (1 / (number_of_hash_functions * expected_insertions))))

    return size_in_bits


def create_data_files_for_optimal_number_of_hash_functions():
    number_of_items_list = [500000, 1000000, 1500000, 2000000]
    fp_rates = [0.0001, 0.001, 0.01, 0.05]
    for expected_number_of_items in number_of_items_list:
        with open('optimal-' + str(expected_number_of_items) + '.dat', 'w') as file:
            file.write('fp_rate\tfilter_size(MB)\n')
            for fp_rate in fp_rates:
                size = size_in_bits(expected_number_of_items, fp_rate)
                file.write(f'{fp_rate}\t{size/(1024*1024)}\n')


def create_data_files_for_constant_number_of_hash_functions():
    number_of_hash_functions_list = [3, 4]
    number_of_items_list = [500000, 1000000, 1500000, 2000000]
    fp_rates = [0.0001, 0.001, 0.01, 0.05]
    for expected_number_of_items in number_of_items_list:
        for number_of_hash_functions in number_of_hash_functions_list:
            with open('constant-' + str(number_of_hash_functions) + '-' + str(expected_number_of_items) + '.dat', 'w') as file:
                file.write('fp_rate\tfilter_size(MB)\n')
                for fp_rate in fp_rates:
                    size = size_in_bits(
                        expected_number_of_items, fp_rate, number_of_hash_functions)
                    file.write(f'{fp_rate}\t{size/(1024*1024)}\n')


if __name__ == "__main__":
    create_data_files_for_constant_number_of_hash_functions()
    create_data_files_for_optimal_number_of_hash_functions()
