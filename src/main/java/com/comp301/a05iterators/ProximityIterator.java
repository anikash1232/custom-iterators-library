package com.comp301.a05iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ProximityIterator implements Iterator<Driver> {
    private Iterator<Driver> driverPool;
    private Position clientPosition;
    private int proximityRange;
    private Driver nextDriver;

    public ProximityIterator (Iterable<Driver> driverPool, Position clientPosition, int proximityRange){
        if(driverPool == null|| clientPosition == null){
            throw new IllegalArgumentException();
        }

        this.driverPool = driverPool.iterator();
        this.clientPosition = clientPosition;
        this.proximityRange = proximityRange;

        this.nextDriver = null;
    }

    private void loadNextDriver(){
        if (nextDriver != null){
            return;
        }
        else{
            while(driverPool.hasNext()){
                Driver driver = driverPool.next();
                Position driverPos = driver.getVehicle().getPosition();
                if (driverPos.getManhattanDistanceTo(clientPosition) <= proximityRange){
                    nextDriver = driver;
                    return;
                }
            }
            nextDriver = null;
        }
    }
    @Override
    public boolean hasNext(){
        loadNextDriver();
        if (nextDriver != null){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Driver next(){
        if (hasNext() == false){
            throw new NoSuchElementException();
        }
        else{
            Driver current = nextDriver;
            nextDriver = null;
            return current;

        }

    }
}
