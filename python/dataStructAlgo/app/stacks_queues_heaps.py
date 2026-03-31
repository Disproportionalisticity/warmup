from collections import deque
import heapq

# stack LIFO
stack = []
stack.append(1)
stack.append(2)
print(stack.pop()) # 2
print(stack.pop()) # 1


# queue FIFO
queue = deque([])
queue.append(1)
queue.append(2)
print(queue.popleft()) # 1
print(queue.popleft()) # 2

# python built-in min heap
min_heap = [5, 4, 8, 3]
heapq.heapify(min_heap) # transforms list to minheap in O(n)

heapq.heappush(min_heap, 2)
print(heapq.heappop(min_heap)) # 2
print(heapq.heappop(min_heap)) # 3

# Max-heap: multiply by -1
max_heap = []
heapq.heappush(max_heap, -5)
heapq.heappush(max_heap, -2)
heapq.heappush(max_heap, -3)
heapq.heappush(max_heap, -9)
print(-heapq.heappop(max_heap)) # 9

# Function Balanced parantheses
def balanced_parantheses(string):
    stack = []
    for char in string:
        if char in ("(", "[", "{"):
            stack.append(char)
        elif stack and ((char == ")" and stack[-1] == "(") or (char == "]" and stack[-1] == "[") or (char == "}" and stack[-1] == "{")):
            stack.pop()
        else:
            return False
    return True

print(balanced_parantheses(")"))  

# K-Largest Element (Heap)
def k_largest_element(array, k):
    max_heap = [-x for x in array]
    heapq.heapify(max_heap)
    for i in range(k-1):
        heapq.heappop(max_heap)
    return -heapq.heappop(max_heap)

print(k_largest_element([3, 10, 5, 20, 2], 3))
    
# Moving Average (Queue)
class MovingAverage:
    def __init__(self, size):
        self.size = size
        self.queue = deque([])
        self.sum = 0
        
    def next(self, val):
        self.queue.append(val)
        if (len(self.queue) > self.size):
            self.sum -= self.queue.popleft()
        self.sum += val
        return self.sum/len(self.queue)
    
moving_average = MovingAverage(5)
print(moving_average.next(1)) # 1.0
print(moving_average.next(2)) # 1.5
print(moving_average.next(3)) # 2.0
print(moving_average.next(4)) # 2.5
print(moving_average.next(5)) # 3.0
print(moving_average.next(6)) # 4.0
print(moving_average.next(7)) # 5.0