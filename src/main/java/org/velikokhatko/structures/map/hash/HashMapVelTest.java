package org.velikokhatko.structures.map.hash;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

public class HashMapVelTest {

    public static void main(String[] args) {
        int capacity = 1000;
        HashMapVel<String, UUID> hashMapVel = new HashMapVel<>();

        ArrayList<String> keys = new ArrayList<>(capacity);

        IntStream.range(0, capacity).forEach(i -> {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMapVel.put(key, value);
            keys.add(key);
        });

        keys.forEach(key -> Assert.isTrue(UUID.fromString(key).equals(hashMapVel.get(key))));
    }
}
