package com.comp301.a05iterators;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class SnakeOrderAcrossPoolsIterator implements Iterator<Driver> {
    private List<Iterator<Driver>> iterators;
    private int index;
    private boolean forward;

    public SnakeOrderAcrossPoolsIterator(List<Iterable<Driver>> driverPools) {
        iterators = new ArrayList<Iterator<Driver>>();
        for (Iterable<Driver> pool : driverPools) {
            iterators.add(pool.iterator());
        }
        index = 0;
        forward = true;
    }

    @Override
    public boolean hasNext() {
        for (Iterator<Driver> it : iterators) {
            if (it.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Driver next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        while (true) {
            Iterator<Driver> current = iterators.get(index);

            if (current.hasNext()) {
                Driver driver = current.next();

                if (forward) {
                    index++;
                    if (index == iterators.size()) {
                        index = iterators.size() - 1;
                        forward = false;
                    }
                } else {
                    index--;
                    if (index < 0) {
                        index = 0;
                        forward = true;
                    }
                }
                return driver;
            } else {
                if (forward) {
                    index++;
                    if (index == iterators.size()) {
                        index = iterators.size() - 1;
                        forward = false;
                    }
                } else {
                    index--;
                    if (index < 0) {
                        index = 0;
                        forward = true;
                    }
                }
            }
        }
    }
}
