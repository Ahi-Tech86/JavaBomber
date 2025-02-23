package observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Subject {

    List<Observer> observers = new CopyOnWriteArrayList<>();

    default void addObserver(Observer observer) {
        observers.add(observer);
    }

    default void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    default void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
