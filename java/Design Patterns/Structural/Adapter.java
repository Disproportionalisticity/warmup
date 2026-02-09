package Structural;

interface CelsiusSensor {
    double getTemperatureC();
}

class Furnace {
    private CelsiusSensor sensor;

    public Furnace (CelsiusSensor sensor) {
        this.sensor = sensor;
    }

    public void regulate() {
        double tempC = sensor.getTemperatureC();

        if (tempC < 18.0) {
            System.out.println("Furnace is heating on, temperature: " + tempC);
        } else if (tempC > 24.0) {
            System.out.println("Furnace is cooling off, temperature: " + tempC);
        } else {
            System.out.println("Furnace is idle, temperature: " + tempC);
        }
    }
}

class FahrenheitSensor {
    private double temperatureF;

    public FahrenheitSensor(double temperatureF) {
        this.temperatureF = temperatureF;
    }

    public void setTemperatureF(double temperatureF) {
        this.temperatureF = temperatureF;
    }

    public double getTemperatureF() {
        return this.temperatureF;
    }
}

class FahrenheitToCelsiusAdapter implements CelsiusSensor {
    private FahrenheitSensor fahrenheitSensor;

    public FahrenheitToCelsiusAdapter(FahrenheitSensor fahrenheitSensor) {
        this.fahrenheitSensor = fahrenheitSensor;
    }

    @Override
    public double getTemperatureC() {
        double temperatureF = fahrenheitSensor.getTemperatureF();
        return (temperatureF - 32) * 5.0 / 9.0;
    }
}

class AdapterTest {
    public static void main(String[] args) {
        FahrenheitSensor fs = new FahrenheitSensor(40);
        CelsiusSensor adapter = new FahrenheitToCelsiusAdapter(fs);

        Furnace furnace = new Furnace(adapter);
        furnace.regulate();
        fs.setTemperatureF(-40);
        furnace.regulate();
        fs.setTemperatureF(32);
        furnace.regulate();
        fs.setTemperatureF(212);
        furnace.regulate();
    }
}
