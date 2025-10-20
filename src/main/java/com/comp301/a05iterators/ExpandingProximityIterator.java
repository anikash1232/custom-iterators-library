package com.comp301.a05iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExpandingProximityIterator implements Iterator<Driver> {
    private Iterable<Driver> driverPool;
    private Iterator<Driver> poolIterator;
    private Position clientPosition;
    private int expansionStep;
    private int currentRange;
    private Driver nextDriver;
    private int totalVisited;
    private int totalDrivers;

    public ExpandingProximityIterator(Iterable<Driver> driverPool, Position clientPosition, int expansionStep) {
        this.driverPool = driverPool;
        this.clientPosition = clientPosition;
        this.expansionStep = expansionStep;
        this.currentRange = 1;
        this.poolIterator = driverPool.iterator();
        this.nextDriver = null;
        this.totalVisited = 0;

        int count = 0;
        for (Driver d : driverPool) {
            count++;
        }
        this.totalDrivers = count;
    }

    private void loadNextDriver() {
        if (nextDriver != null) {
            return;
        }

        while (true) {
            while (poolIterator.hasNext()) {
                Driver driver = poolIterator.next();
                int distance = driver.getVehicle().getPosition().getManhattanDistanceTo(clientPosition);

                if (distance <= currentRange && distance > currentRange - expansionStep) {
                    nextDriver = driver;
                    totalVisited++;
                    return;
                }
            }

            if (totalVisited < totalDrivers) {
                poolIterator = driverPool.iterator();
                currentRange += expansionStep;
            } else {
                nextDriver = null;
                return;
            }
        }
    }

    @Override
    public boolean hasNext() {
        loadNextDriver();
        return nextDriver != null;
    }

    @Override
    public Driver next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Driver current = nextDriver;
        nextDriver = null;
        return current;
    }
}
