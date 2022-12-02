package org.velikokhatko.structures.map.hash;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class HashMapVel<K, V> {
    private AtomicInteger currentCapacity = new AtomicInteger(0);
    private final double expansionFactor = 1.5;

    private List<Pair<K, V>>[] buckets;

    public HashMapVel(int capacity) {
        buckets = new List[capacity];
    }

    public HashMapVel() {
        int defaultCapacity = 16;
        buckets = new List[defaultCapacity];
    }

    public V get(K key) {
        int keyHash = key.hashCode();
        int bucketNumber = getBucketNumber(keyHash, buckets);
        return buckets[bucketNumber].stream()
                .filter(kvPair -> kvPair.key.equals(key))
                .map(Pair::value)
                .findFirst().orElse(null);
    }

    public void put(K key, V value) {
        put(buckets, currentCapacity, key, value);
    }

    private void put(List<Pair<K, V>>[] currentBuckets, AtomicInteger capacity, K newKey, V newValue) {
        int keyHash = newKey.hashCode();
        int bucketNumber = getBucketNumber(keyHash, currentBuckets);
        if (currentBuckets[bucketNumber] == null) {
            currentBuckets[bucketNumber] = new LinkedList<>();
        }
        currentBuckets[bucketNumber].add(new Pair<>(newKey, newValue));
        capacity.incrementAndGet();

        if (capacity.get() * expansionFactor >= currentBuckets.length) {
            expansion();
        }
    }

    private int getBucketNumber(int keyHash, List<Pair<K, V>>[] buckets) {
        return Math.abs(keyHash % buckets.length);
    }

    private void expansion() {
        List<Pair<K, V>>[] newBuckets = new List[buckets.length * 2];
        AtomicInteger newCapacity = new AtomicInteger();
        Arrays.stream(buckets).filter(Objects::nonNull).forEach(bucket -> {
            for (Pair<K, V> pair : bucket) {
                put(newBuckets, newCapacity, pair.key, pair.value);
            }
        });
        buckets = newBuckets;
        currentCapacity = newCapacity;
    }

    private record Pair<K, V>(K key, V value) {
    }
}
