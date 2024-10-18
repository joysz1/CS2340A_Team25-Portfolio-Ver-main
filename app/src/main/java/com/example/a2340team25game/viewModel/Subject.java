package com.example.a2340team25game.viewModel;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String collisionStatus);
}
