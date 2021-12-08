from math import exp
import networkx as nx
import random
import seaborn as sns
import pandas as pd

def experiment(n_nodes):
    graph = nx.Graph()
    for i in range(n_nodes):
        graph.add_node(i)
    
    while not nx.is_connected(graph):
        edge = random.choice(list(nx.non_edges(graph)))
        graph.add_edge(edge[0], edge[1])
    
    return graph.number_of_edges()


nodes = [i for i in range(5, 50)]
samples = 50
edges = []

for n in nodes:
    total_edges = 0.0
    for i in range(samples):
        total_edges += experiment(n)
    edges.append(total_edges/samples)
    print("NODES: %d - EDGES: %.2f" % (n, edges[-1]))


df = pd.DataFrame()
df['Nodes'] = nodes
df['Edges'] = edges


plot = sns.relplot(
    data=df, 
    x="Nodes", y="Edges", 
    kind="line", height=5, aspect=2
)

fig = plot.fig

fig.savefig('plot.png')







