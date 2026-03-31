from collections import deque

# square (circular)
graph1 = {
    0: [1, 3],
    1: [0, 2],
    2: [1, 3],
    3: [0, 2]
}

# circle (circular)
graph2 = {
    0: [1, 5],
    1: [0, 2],
    2: [1, 3],
    3: [2, 4],
    4: [3, 5],
    5: [4, 0]
}

# tree (not circular)
graph3 = {
    0: [1, 2, 3],
    1: [0, 4, 5],
    2: [0, 6],
    3: [0, 7, 8, 9],
    4: [1, 10],
    5: [1, 11],
    6: [2, 12],
    7: [3, 13, 14, 15],
    8: [3],
    9: [3],
    10: [4],
    11: [5],
    12: [6],
    13: [7],
    14: [7],
    15: [7, 16],
    16: [15, 17],
    17: [16]
}

# complex graph (circular)
graph4 = {
    0: [1, 2, 5],
    1: [0, 2, 3, 4, 8],
    2: [0, 1, 10],
    3: [1, 4, 14],
    4: [1, 3, 5, 8],
    5: [0, 4, 6, 7, 8],
    6: [5],
    7: [5],
    9: [5, 10, 11],
    10: [2, 9],
    11: [9, 12],
    12: [11, 13],
    13: [12],
    14: [3, 15, 16],
    15: [14, 16],
    16: [14, 15]
}

def bfs_traversal(graph, start_node):
    visited = set()
    queue = deque([start_node])
    visited.add(start_node)
    
    while queue:
        current = queue.popleft()
        print(current, end=" ")
        
        if current in graph:
            for neighbor in graph[current]:
                if neighbor not in visited:
                    visited.add(neighbor)
                    queue.append(neighbor)
    print()

def dfs_traversal(graph, start_node):
    visited = set()
    visited.add(start_node)
    stack = [start_node]
    
    while stack:
        current = stack.pop()
        print(current, end=" ")
        
        if current in graph:
            for neighbor in graph[current]:
                if neighbor not in visited:
                    visited.add(neighbor)
                    stack.append(neighbor)
    print()
            
def bfs_shortest_path(graph, start_node, target):
    visited = set()
    queue = deque([start_node])
    visited.add(start_node)
    
    parent = {start_node: None}
    
    while queue:
        current = queue.popleft()
        
        if current == target:
            path = []
            while current is not None:
                path.append(current)
                current = parent[current]
            return path[::-1]
        
        if current in graph:
            for neighbor in graph[current]:
                if neighbor not in visited:
                    visited.add(neighbor)
                    parent[neighbor] = current
                    queue.append(neighbor)
                  
    return "Not found"
                
bfs_traversal(graph1, 0) # 0 1 3 2 
bfs_traversal(graph2, 0) # 0 1 5 2 4 3
bfs_traversal(graph3, 0) # 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
bfs_traversal(graph4, 0) # 0 1 2 5 3 4 8 10 6 7 9
                
dfs_traversal(graph1, 0) # 0 3 2 1
dfs_traversal(graph2, 0) # 0 5 4 3 2 1
dfs_traversal(graph3, 0) # 0 3 9 8 7 15 16 17 14 13 2 6 12 1 5 11 4 10
dfs_traversal(graph4, 0) # 0 5 8 7 6 4 3 2 10 9 1

print(bfs_shortest_path(graph4, 13, 16)) # [13, 12, 11, 9, 5, 4, 3, 14, 16]