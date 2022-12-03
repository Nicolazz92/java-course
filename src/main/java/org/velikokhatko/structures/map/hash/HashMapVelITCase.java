package org.velikokhatko.structures.map.hash;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.IntStream;

public class HashMapVelITCase {

    public static void main(String[] args) {
        int capacity = 10000;

        velMathodCall(capacity);
        normMethodCall(capacity);
    }

    private static long normMethodCall(int capacity) {
        long beforeNorm = System.nanoTime();

        HashMap<String, UUID> hashMap = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>(capacity);
        IntStream.range(0, capacity).forEach(i -> {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMap.put(key, value);
            keys.add(key);
        });
        keys.forEach(key -> Assert.isTrue(UUID.fromString(key).equals(hashMap.get(key))));
        return beforeNorm;
    }

    private static long velMathodCall(int capacity) {
        HashMapVel<String, UUID> hashMapVel = new HashMapVel<>();
        ArrayList<String> keysVel = new ArrayList<>(capacity);
        IntStream.range(0, capacity).forEach(i -> {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMapVel.put(key, value);
            keysVel.add(key);
        });
        keysVel.forEach(key -> Assert.isTrue(UUID.fromString(key).equals(hashMapVel.get(key))));
        long afterVel = System.nanoTime();
        return afterVel;
    }
}
