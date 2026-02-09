package Behavioral;

interface IDoorState {
    void lockDoor();
    void unlockDoor();
    void openDoor();
    void closeDoor();
}

abstract class ADoorState implements IDoorState {
    protected Door.InternalDoorStateController controller;

    protected ADoorState(Door.InternalDoorStateController controller) {
        this.controller = controller;
    }

    protected void InvalidAction(String action) {
        System.out.println("Cannot " + action + " in current state: " + this.getClass().getSimpleName());
    }

    @Override
    public void lockDoor() {
        this.InvalidAction("lock");
    }

    @Override
    public void unlockDoor() {
        this.InvalidAction("unlock");
    }

    @Override
    public void openDoor() {
        this.InvalidAction("open");
    }

    @Override
    public void closeDoor() {
        this.InvalidAction("close");
    }
}

class LockedDoorState extends ADoorState {
    public LockedDoorState(Door.InternalDoorStateController controller) {
        super(controller);
    }

    @Override
    public void unlockDoor() {
        System.out.println("Door unlocked");
        this.controller.changeState(new UnlockedDoorState(controller));
    }
}

class UnlockedDoorState extends ADoorState {
    public UnlockedDoorState(Door.InternalDoorStateController controller) {
        super(controller);
    }

    @Override
    public void lockDoor() {
        System.out.println("Door locked");
        this.controller.changeState(new LockedDoorState(this.controller));
    }

    @Override
    public void openDoor() {
        System.out.println("Door opened");
        this.controller.changeState(new OpenedDoorState(this.controller));
    }
}

class OpenedDoorState extends ADoorState {
    public OpenedDoorState(Door.InternalDoorStateController controller) {
        super(controller);
    }

    @Override
    public void closeDoor() {
        System.out.println("Door is closed");
        this.controller.changeState(new ClosedDoorState(this.controller));
    }
}

class ClosedDoorState extends ADoorState {
    public ClosedDoorState(Door.InternalDoorStateController controller) {
        super(controller);
    }

    @Override
    public void lockDoor() {
        System.out.println("Door is locked");
        this.controller.changeState(new LockedDoorState(this.controller));
    }
    @Override
    public void openDoor() {
        System.out.println("Door is opened");
        this.controller.changeState(new OpenedDoorState(this.controller));
    }
}

class Door {
    interface InternalDoorStateController {
        void changeState(IDoorState newState);
    }

    private class DoorStateController implements InternalDoorStateController {
        public void changeState(IDoorState newState) {
            Door.this.currentState = newState;
        }
    }

    private DoorStateController controller;
    private IDoorState currentState;

    public Door() {
        this.controller = new DoorStateController();
        this.currentState = new LockedDoorState(this.controller);
    }

    public void openDoor() {
        this.currentState.openDoor();
    }

    public void closeDoor() {
        this.currentState.closeDoor();
    }

    public void lockDoor() {
        this.currentState.lockDoor();
    }

    public void unlockDoor() {
        this.currentState.unlockDoor();
    }
}

class StateTest {
    public static void main(String[] args) {
        Door door = new Door();

        door.lockDoor(); // fail
        door.openDoor(); // fail
        door.closeDoor(); // fail
        door.unlockDoor(); //success

        door.unlockDoor(); // fail
        door.closeDoor(); // fail
        door.openDoor(); // success

        door.openDoor(); // fail
        door.lockDoor(); // fail
        door.unlockDoor(); // fail
        door.closeDoor(); // success

        door.closeDoor(); // fail
        door.unlockDoor(); // fail
        door.lockDoor(); // success
    }
}
