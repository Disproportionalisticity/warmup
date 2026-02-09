package Behavioral;

interface IDevice {
    void turnOn();
    void turnOff();
}

class SmartLights implements IDevice {

    @Override
    public void turnOn() {
        System.out.println("Smart Lights is on");
    }

    @Override
    public void turnOff() {
        System.out.println("Smart Lights is off");
    }

    public void setBrightness() {
        System.out.println("Set brightness to medium");
    }

    public void unsetBrightness() {
        System.out.println("Set brightness to low");
    }
}

class SmartFan implements IDevice {

    @Override
    public void turnOn() {
        System.out.println("SmartFan is on");
    }

    @Override
    public void turnOff() {
        System.out.println("SmartFan is off");
    }

    public void setFanPower() {
        System.out.println("SmartFan power is set to high");
    }

    public void unsetFanPower() {
        System.out.println("SmartFan power is set to low");
    }
}

class SmartDoor {
    public void openDoors() {
        System.out.println("SmartDoor opened");
    }

    public void closeDoors() {
        System.out.println("SmartDoor closed");
    }   
}

interface ICommand {
    void execute();
    void undo();
}

class SmartLightsOnCommand implements ICommand {
    private SmartLights lights;

    public SmartLightsOnCommand(SmartLights lights) {
        this.lights = lights;
    }

    @Override
    public void execute() {
        this.lights.turnOn();
    }

    @Override
    public void undo() {
        this.lights.turnOff();
    }
}

class SmartLightsOffCommand implements ICommand {
    private SmartLights lights;

    public SmartLightsOffCommand(SmartLights lights) {
        this.lights = lights;
    }

    @Override
    public void execute() {
        this.lights.turnOff();
    }

    @Override
    public void undo() {
        System.out.println("Nothing to undo here");
    }
}

class SmartLightsSetBrightnessCommand implements ICommand {
    private SmartLights lights;

    public SmartLightsSetBrightnessCommand(SmartLights lights) {
        this.lights = lights;
    }

    @Override
    public void execute() {
        this.lights.setBrightness();
    }

    @Override
    public void undo() {
        this.lights.unsetBrightness();
    }
}

class SmartFanOnCommand implements ICommand {
    private SmartFan fan;

    public SmartFanOnCommand(SmartFan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        this.fan.turnOn();
    }

    @Override
    public void undo() {
        this.fan.turnOff();
    }
}

class SmartFanOffCommand implements ICommand {
    private SmartFan fan;

    public SmartFanOffCommand(SmartFan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        this.fan.turnOff();
    }

    @Override
    public void undo() {
        System.out.println("Nothing to undo here");
    }
}

class SmartFanSetPowerCommand implements ICommand {
    private SmartFan fan;

    public SmartFanSetPowerCommand(SmartFan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        this.fan.setFanPower();
    }

    @Override
    public void undo() {
        this.fan.unsetFanPower();
    }
}

class SmartDoorOpenCommand implements ICommand {
    private SmartDoor door;

    public SmartDoorOpenCommand(SmartDoor door) {
        this.door = door;
    }

    @Override
    public void execute() {
        this.door.openDoors();
    }

    @Override
    public void undo() {
        this.door.closeDoors();
    }    
}

class SmartDoorCloseCommand implements ICommand {
    private SmartDoor door;

    public SmartDoorCloseCommand(SmartDoor door) {
        this.door = door;
    }

    @Override
    public void execute() {
        this.door.closeDoors();
    }

    @Override
    public void undo() {
        this.door.openDoors();
    }    
}

class Remote {
    private ICommand command;

    public Remote(ICommand command) {
        this.command = command;
    }

    public void setCommand(ICommand command) {
        this.command = command;
    }

    public void pressButton() {
        this.command.execute();
    }

    public void PressUndoButton() {
        this.command.undo();
    }
}

class CommandTest {
    public static void main(String[] args) {
        SmartLights smartLights = new SmartLights();
        SmartDoor smartDoor = new SmartDoor();

        Remote remote = new Remote(new SmartLightsOnCommand(smartLights));
        remote.pressButton();

        remote.setCommand(new SmartLightsSetBrightnessCommand(smartLights));
        remote.pressButton();
        remote.PressUndoButton();

        remote.setCommand(new SmartLightsOffCommand(smartLights));
        remote.pressButton();

        remote.setCommand(new SmartDoorOpenCommand(smartDoor));
        remote.pressButton();
        remote.PressUndoButton();
        remote.pressButton();

        remote.setCommand(new SmartDoorCloseCommand(smartDoor));
        remote.pressButton();
    }
}