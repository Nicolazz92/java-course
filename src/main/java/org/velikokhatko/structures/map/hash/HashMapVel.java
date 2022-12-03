package org.velikokhatko.structures.map.hash;

import java.util.concurrent.atomic.AtomicInteger;

public class HashMapVel<K, V> {
    private AtomicInteger currentCapacity = new AtomicInteger(0);
    private final double expansionFactor = 1.5;

    private SimpleLinkedList<K, V>[] buckets;

    public HashMapVel() {
        int defaultCapacity = 1 << 4;
        buckets = new SimpleLinkedList[defaultCapacity];
    }

    public V get(K key) {
        int keyHash = key.hashCode();
        int bucketNumber = getBucketNumber(keyHash, buckets);
        SimpleLinkedList<K, V> bucket = buckets[bucketNumber];
        return bucket.get(key);
    }

    public void put(K key, V value) {
        put(buckets, currentCapacity, key, value);
    }

    private void put(SimpleLinkedList<K, V>[] currentBuckets, AtomicInteger capacity, K newKey, V newValue) {
        int keyHash = newKey.hashCode();
        int bucketNumber = getBucketNumber(keyHash, currentBuckets);
        if (currentBuckets[bucketNumber] == null) {
            currentBuckets[bucketNumber] = new SimpleLinkedList<K, V>();
        }
        currentBuckets[bucketNumber].add(new Pair<>(newKey, newValue));
        capacity.incrementAndGet();

        if (capacity.get() * expansionFactor >= currentBuckets.length) {
            expansion();
        }
    }

    private int getBucketNumber(int keyHash, SimpleLinkedList[] buckets) {
        return keyHash & (buckets.length - 1);
    }

    private void expansion() {
        SimpleLinkedList<K, V>[] newBuckets = new SimpleLinkedList[buckets.length << 1];
        AtomicInteger newCapacity = new AtomicInteger();
        for (int i = 0, bucketsLength = buckets.length; i < bucketsLength; i++) {
            SimpleLinkedList<K, V> bucket = buckets[i];
            if (bucket != null) {
                SimpleLinkedList.Node currentNode = bucket.head;
                do {
                    put(newBuckets, newCapacity, (K) currentNode.data.key, (V) currentNode.data.value);
                    currentNode = currentNode.next;
                } while (currentNode != null);
            }
        }
        buckets = newBuckets;
        currentCapacity = newCapacity;
    }

    private record Pair<K, V>(K key, V value) {
    }

    private static class SimpleLinkedList<KV, LV> {
        Node head;
        Node tail;

        class Node {
            Pair<KV, LV> data;
            Node next;
        }

        void add(Pair<KV, LV> newPair) {
            Node newNode = new Node();
            newNode.data = newPair;
            if (head == null) {
                head = newNode;
            }
            if (tail == null) {
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }

        LV get(KV key) {
            Node currentNode = head;
            do {
                if (currentNode.data.key.equals(key)) {
                    return currentNode.data.value;
                }
                currentNode = currentNode.next;
            } while (currentNode != null);
            return null;
        }
    }
}
