package Creational;

class SingletonInstance {
    private static SingletonInstance instance;

    private int x;

    private SingletonInstance() {

    }

    public static SingletonInstance getInstance() {
        if (SingletonInstance.instance == null) {
            SingletonInstance.instance = new SingletonInstance();
        }

        return SingletonInstance.instance;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }
}

class Singleton {
    public static void main(String[] args) {
        SingletonInstance objA = SingletonInstance.getInstance();
        objA.setX(12);
        System.out.println(objA.getX());

        SingletonInstance objB = SingletonInstance.getInstance();
        System.out.println(objB.getX());

        objB.setX(144);
        System.out.println(objA.getX());
        System.out.println(objB.getX());
    }
}
