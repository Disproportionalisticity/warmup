unsorted_array = [42, 7, 89, 23, 56, 12, 98, 4, 71, 33, 65, 19, 81, 50, 2, 37, 77, 15, 92, 44]

def bubble_sort(array):
    n = len(array)
    for i in range(n):
        swapped = False
        for j in range(0, n - i - 1):
            if array[j] > array[j+1]:
                swapped = True
                array[j], array[j+1] = array[j+1], array[j]
        if not swapped:
            break
    return array
print(bubble_sort(unsorted_array.copy()))

def insertion_sort(array):
    n = len(array)
    
    for i in range(1, n):
        key = array[i]
        j = i - 1
        
        while j>= 0 and array[j] > key:
            array[j+1] = array[j]
            j -= 1
            
        array[j+1] = key
    
    return array
print(insertion_sort(unsorted_array.copy()))