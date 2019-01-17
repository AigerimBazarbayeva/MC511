package fh.ooe.mcm.inactivitytracker.interfaces;

public interface Observable {
    void notifyAll(Object object);
    void addObserver(Observer observer);
}
