package observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Subject {

    List<UpdatableObserver> UPDATABLE_OBSERVERS = new CopyOnWriteArrayList<>();

    default void addObserver(UpdatableObserver updatableObserver) {
        UPDATABLE_OBSERVERS.add(updatableObserver);
    }

    default void removeObserver(UpdatableObserver updatableObserver) {
        UPDATABLE_OBSERVERS.remove(updatableObserver);
    }

    default void notifyObservers() {
        for (UpdatableObserver updatableObserver : UPDATABLE_OBSERVERS) {
            updatableObserver.update();
        }
    }
}
