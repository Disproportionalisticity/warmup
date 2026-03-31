import array

# static array
static_arr = array.array('i', [0] * 5)
static_arr[0] = 10
print(static_arr) # array('i', [10, 0, 0, 0, 0])

# lists are dynamic arrays
dyn_array = []
dyn_array.append(1)
dyn_array.append(2)
print(dyn_array) # [1, 2]
dyn_array.pop()
print(dyn_array) # [1]

# dictionaries
lookup = {"apple": 5, "orange": 10}
lookup["banana"] = 2
print(lookup) # {'apple': 5, 'orange': 10, 'banana': 2}
del lookup["apple"]
print(lookup) # {'orange': 10, 'banana': 2}

# Check existence
if "apple" in lookup:
    print(lookup["apple"])
    
# linked lists
class Node:
    def __init__(self, data):
        self.data = data
        self.next = None
        
head = Node(1)
head.next = Node(3)
head.next.next = Node(5)
head.next.next.next = Node(7)

# linked list printer
def print_linked_list(head):
    while(head is not None):
        print(head.data)
        head = head.next
   
print_linked_list(head)   
    
# create a function that takes a list of integers and returns a list only with even integers by using list comprehension
def return_even_numbers_list(array):
    return [x for x in array if x % 2 == 0]

print(return_even_numbers_list([1,2,3,4,8,9,10])) 

# given a string (e.g., "banana"), use a dictionary to count the occurrences of each character.
def letter_count(string):
    result = {}
    for letter in string:
        result[letter] = result.get(letter, 0) + 1
    return result

print(letter_count("balalayka"))

# given a simple Node class, write a function reverse(head) that reverses the linked list and returns the new head.
def reverse_linked_list(head):
    prev = None
    current = head
    while current:
        next = current.next
        current.next = prev
        prev = current
        current = next
    return prev
        

print_linked_list(reverse_linked_list(head))