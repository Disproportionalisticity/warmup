package Structural;

interface IDevice {
    String enableLight();
    String disableLight();
    String setLightBrighness(int level);
}

class PhilipsHueLight implements IDevice {
    public PhilipsHueLight() {}

    @Override
    public String enableLight() {
        return "Turn on Philips light";
    }

    @Override
    public String disableLight() {
        return "Turn off Philips light";
    }

    @Override
    public String setLightBrighness(int level) {
        if (level < 0 || level > 10) {
            return "Invalid light level. Light level can be an integer between 0 and 10";
        } else {
            return "Set Philips light to " + level;
        }
    }
}

class LIFXLight implements IDevice {
    public LIFXLight() {}

    @Override
    public String enableLight() {
        return "Turn on LIFX light";
    }

    @Override
    public String disableLight() {
        return "Turn off LIFX light";
    }

    @Override
    public String setLightBrighness(int level) {
        if (level < 0 || level > 10) {
            return "Invalid light level. Light level can be an integer between 0 and 10";
        } else {
            return "Set LIFX light to " + level;
        }
    }
}

class XiaomiLight implements IDevice {
    public XiaomiLight() {}

    @Override
    public String enableLight() {
        return "Turn on Xiaomi light";
    }

    @Override
    public String disableLight() {
        return "Turn off Xiaomi light";
    }

    @Override
    public String setLightBrighness(int level) {
        if (level < 0 || level > 10) {
            return "Invalid light level. Light level can be an integer between 0 and 10";
        } else {
            return "Set Xiaomi light to " + level;
        }
    }
}

interface ILightControl {
    String turnOn();
    String turnOff();
    String setBrightness(int level);
    public void setLightDevice(IDevice lightDevice); // it breakes ISP but this is done for the purposes of studying bridge interface
}

interface ILightControlDeviceSwitcher {
    public void setLightDevice(IDevice lightDevice);
}

abstract class ALightControl implements ILightControl {
    protected IDevice lightDevice;

    public ALightControl(IDevice lightDevice) {
        this.lightDevice = lightDevice;
    }

    public void setLightDevice(IDevice lightDevice) {
        this.lightDevice = lightDevice;
    }
}

class ManualControl extends ALightControl {
    public ManualControl(IDevice lightDevice) {
        super(lightDevice);
    }

    @Override
    public String turnOn() {
        return this.lightDevice.enableLight() + " via manual control";
    }

    @Override
    public String turnOff() {
        return this.lightDevice.disableLight() + " via manual control";
    }

    @Override
    public String setBrightness(int level) {
        return this.lightDevice.setLightBrighness(level) + " via manual control";
    }
}

class RemoteControl extends ALightControl {
    public RemoteControl(IDevice lightDevice) {
        super(lightDevice);
    }

    @Override
    public String turnOn() {
        return this.lightDevice.enableLight() + " via remote control";
    }

    @Override
    public String turnOff() {
        return this.lightDevice.disableLight() + " via remote control";
    }

    @Override
    public String setBrightness(int level) {
        return this.lightDevice.setLightBrighness(level) + " via remote control";
    }
}

class AutomatedControl extends ALightControl {
    public AutomatedControl(IDevice lightDevice) {
        super(lightDevice);
    }

    @Override
    public String turnOn() {
        return this.lightDevice.enableLight() + " via automated control";
    }

    @Override
    public String turnOff() {
        return this.lightDevice.disableLight() + " via automated control";
    }

    @Override
    public String setBrightness(int level) {
        return this.lightDevice.setLightBrighness(level) + " via automated control";
    }
}

class BridgeTest {
    public static void main(String[] args) {
        ILightControl manualControl = new ManualControl(new XiaomiLight());

        System.out.println(manualControl.turnOn());
        System.out.println(manualControl.setBrightness(7));
        System.out.println(manualControl.turnOff());

        manualControl.setLightDevice(new LIFXLight());

        System.out.println(manualControl.turnOn());
        System.out.println(manualControl.setBrightness(11));
        System.out.println(manualControl.setBrightness(5));
        System.out.println(manualControl.turnOff());
    }
}
