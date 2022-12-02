package org.velikokhatko.structures.map.hash;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HashMapVel<K, V> {
    private AtomicInteger currentCapacity = new AtomicInteger(0);
    private final double expansionFactor = 1.5;

    private List<Pair<K, V>>[] buckets;

    public HashMapVel() {
        int defaultCapacity = 1 << 4;
        buckets = new List[defaultCapacity];
    }

    public V get(K key) {
        int keyHash = key.hashCode();
        int bucketNumber = getBucketNumber(keyHash, buckets);
        List<Pair<K, V>> bucket = buckets[bucketNumber];
        for (int i = 0; i < bucket.size(); i++) {
            Pair<K, V> kvPair = bucket.get(i);
            if (kvPair.key.equals(key)) {
                return kvPair.value();
            }
        }
        return null;
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
        return keyHash & (buckets.length - 1);
    }

    private void expansion() {
        List<Pair<K, V>>[] newBuckets = new List[buckets.length << 1];
        AtomicInteger newCapacity = new AtomicInteger();
        for (int i = 0, bucketsLength = buckets.length; i < bucketsLength; i++) {
            List<Pair<K, V>> bucket = buckets[i];
            if (bucket != null) {
                for (int j = 0; j < bucket.size(); j++) {
                    Pair<K, V> pair = bucket.get(j);
                    put(newBuckets, newCapacity, pair.key, pair.value);
                }
            }
        }
        buckets = newBuckets;
        currentCapacity = newCapacity;
    }

    private record Pair<K, V>(K key, V value) {
    }
}
