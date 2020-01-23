import random

topology_file_name = 'AS-topology.txt'
trans_as_file_name = 'transAS.txt'

node_ids = [num for num in range(500)]
transient_node_ids = random.sample(node_ids, 50)

num_path = 2500
paths = set()

while num_path > 0:
    path = tuple(random.sample(node_ids, 2))
    reverse_path = (path[0], path[1])
    if path not in paths and reverse_path not in paths:
        paths.add(path)
        num_path -= 1

with open(topology_file_name, 'x') as topology_file:
    lines = [f'{path[0]} {path[1]}' for path in paths]
    topology_file.write('\n'.join(lines))

with open(trans_as_file_name, 'x') as trans_as_file:
    str_node_ids = [str(num) for num in transient_node_ids]
    trans_as_file.write('\n'.join(str_node_ids))
