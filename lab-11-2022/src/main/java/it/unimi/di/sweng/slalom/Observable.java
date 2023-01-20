package it.unimi.di.sweng.slalom;

import org.jetbrains.annotations.NotNull;

public interface Observable<T> {
    void notifyObservers();

    void addObserver(@NotNull Observer<T> obs);

    void removeObserver(@NotNull Observer<T> obs);

    @NotNull T getState();
}
