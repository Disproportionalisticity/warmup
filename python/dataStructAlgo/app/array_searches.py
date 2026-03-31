sorted_array = [1, 3, 4, 5, 7, 8, 9, 11, 13, 16, 17, 18, 19]

def binary_search(array, target):
    left = 0
    right = len(array) - 1
    while left <= right:
        index = (left + right)//2
        if target < array[index]:
            right = index -1
        elif target > array[index]:
            left = index + 1
        else:
            return True
    return False

print(binary_search(sorted_array, 2))

unsorted_array = [6, 1, 3, 7, 4, 5, 9, 2]

def linear_search(array, target):
    for x in array:
        if target == x:
            return True
    return False

print(linear_search(unsorted_array, 10))