package org.velikokhatko.structures.map.hash;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.IntStream;

public class HashMapVelTest {

    public static void main(String[] args) {
        int capacity = 1000000;

        velMathodCall(capacity);
        velMathodCall(capacity);
        normMethodCall(capacity);
        normMethodCall(capacity);

        long beforeVel = System.nanoTime();
        long afterVel = velMathodCall(capacity);
        System.out.println((afterVel - beforeVel));

        long beforeNorm = normMethodCall(capacity);
        long afterNorm = System.nanoTime();
        System.out.println((afterNorm - beforeNorm));
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
