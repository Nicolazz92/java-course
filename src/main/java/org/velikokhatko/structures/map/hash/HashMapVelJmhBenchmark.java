package org.velikokhatko.structures.map.hash;

import org.apache.commons.math3.util.Pair;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HashMapVelJmhBenchmark {
    public static final int INITIAL_CAPACITY = 10000;

    public static void main(String[] args) throws IOException {
        Main.main(new String[]{"HashMapVelJmhBenchmark"});
    }

    @Benchmark()
    @Fork(value = 1, warmups = 1)
    public void normMethodCall() {
        HashMap<String, UUID> hashMap = new HashMap<>();
        List<Pair<String, UUID>> keys = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMap.put(key, value);
            keys.add(new Pair<>(key, value));
        }
        for (int i = 0; i < keys.size(); i++) {
            Pair<String, UUID> pair = keys.get(i);
            Assert.isTrue(pair.getValue().equals(hashMap.get(pair.getKey())));
        }
    }

    @Benchmark()
    @Fork(value = 1, warmups = 1)
    public void velMathodCall() {
        HashMapVel<String, UUID> hashMapVel = new HashMapVel<>();
        List<Pair<String, UUID>> keysVel = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMapVel.put(key, value);
            keysVel.add(new Pair<>(key, value));
        }
        for (int i = 0; i < keysVel.size(); i++) {
            Pair<String, UUID> pair = keysVel.get(i);
            Assert.isTrue(pair.getValue().equals(hashMapVel.get(pair.getKey())));
        }
    }
}
