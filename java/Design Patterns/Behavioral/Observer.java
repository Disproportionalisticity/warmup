package Behavioral;

import java.util.ArrayList;
import java.util.List;

interface IObserver {
    void update(String weather);
}

class PhoneDisplay implements IObserver {
    private String weather;

    @Override
    public void update(String weather) {
        this.weather = weather;
        this.display();
    }

    private void display() {
        System.out.println("Phone display: " + this.weather);
    }
}

class PCDisplay implements IObserver {
    private String weather;

    @Override
    public void update(String weather) {
        this.weather = weather;
        this.display();
    }

    private void display() {
        System.out.println("Phone display " + this.weather);
    }
}

interface IService {
    void addObserver(IObserver observer);
    void removeObserver(IObserver observer);
    void nofityObservers();
}

class WeatherStation implements IService {
    private List<IObserver> observers = new ArrayList<>();
    private String weather;

    public void setWeather(String weather) {
        this.weather = weather;
        this.nofityObservers();
    }

    @Override
    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void nofityObservers() {
        for(IObserver observer : this.observers) {
            observer.update(this.weather);
        }
    }
}

class ObserverTest {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        weatherStation.addObserver(new PCDisplay());
        weatherStation.addObserver(new PhoneDisplay());

        
        weatherStation.setWeather("Sunny");
        weatherStation.setWeather("Rainy");
        weatherStation.setWeather("Cloudy");
    }
}
