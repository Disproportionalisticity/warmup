package Behavioral;

interface ISortingStrategy {
    int[] sort(int[] array);
}

class BubbleSortStrategy implements ISortingStrategy {
    @Override
    public int[] sort(int[] array) {
        System.out.println("Sorting with BubbleSort"); // sort logic
        return array;
    }
}

class InsertionSort implements ISortingStrategy {
    @Override
    public int[] sort(int[] array) {
        System.out.println("Sorting with InsertionSort"); // sort logic
        return array;
    }
}

class QuickSort implements ISortingStrategy {
    @Override
    public int[] sort(int[] array) {
        System.out.println("Sorting with QuickSort"); // sort logic
        return array;
    }
}

interface ISortingManager {
    public void setSortingStrategy(ISortingStrategy sortingStrategy);
    int[] performSort(int[] array);
}

class SortingManager implements ISortingManager {
    private ISortingStrategy sortingStrategy;

    public SortingManager(ISortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public void setSortingStrategy(ISortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public int[] performSort(int[] array) {
        return this.sortingStrategy.sort(array);
    }
}

class StrategySameInputTest {
    public static void main(String[] args) {
        int[] array = {3, 2, 5, 1, 7};

        ISortingManager sortingManager = new SortingManager(new BubbleSortStrategy());
        array = sortingManager.performSort(array);
        sortingManager.setSortingStrategy(new InsertionSort());
        array = sortingManager.performSort(array);
        sortingManager.setSortingStrategy(new QuickSort());
        array = sortingManager.performSort(array);
    }
}
