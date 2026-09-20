# Custom Iterators

Three non-trivial `Iterator` implementations in Java that traverse spatial data in orders
the underlying collections don't natively support.

## What it does

Given pools of drivers positioned in 2D space, these iterators walk them in useful but
awkward orders — nearest-first, outward in expanding rings, or zigzagging across several
pools at once. Each one is a lazy `Iterator<Driver>`: nothing is precomputed, nothing is
copied into an intermediate list, and traversal state lives entirely in the iterator.

- **ProximityIterator** — visits drivers in order of distance from a reference position
- **ExpandingProximityIterator** — sweeps outward in widening rings, yielding everything
  within each radius before growing
- **SnakeOrderAcrossPoolsIterator** — interleaves several driver pools, reversing direction
  at each end so traversal snakes back and forth rather than restarting

## Architecture

```
Driver, DriverImpl        a driver with a position
Vehicle, VehicleImpl      vehicle state
Position, PositionImpl    2D coordinate with distance calculation

ProximityIterator                implements Iterator<Driver>
ExpandingProximityIterator       implements Iterator<Driver>
SnakeOrderAcrossPoolsIterator    implements Iterator<Driver>
```

The snake iterator is the interesting one. It holds a `List<Iterator<Driver>>` — one per
pool — plus an index and a direction flag. Each `next()` advances the current pool's
iterator, then steps the index; when it runs off either end it flips direction rather than
wrapping. Exhausted pools are skipped without disturbing the alternation. Because it composes
other iterators rather than materialising their contents, it works over pools of any size,
including ones being produced lazily.

All three throw `NoSuchElementException` on overrun, as the `Iterator` contract requires.

## Running it

Requires Java 17+ and Maven.

```bash
mvn clean test
```
