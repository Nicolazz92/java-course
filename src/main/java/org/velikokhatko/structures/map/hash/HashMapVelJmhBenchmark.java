package org.velikokhatko.structures.map.hash;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.IntStream;

public class HashMapVelJmhBenchmark {

    public static void main(String[] args) throws IOException {
        Main.main(new String[]{"HashMapVelJmhBenchmark"});
    }

    @Benchmark()
    @Fork(value = 1, warmups = 1)
    public void normMethodCall() {
        HashMap<String, UUID> hashMap = new HashMap<>();
        ArrayList<String> keys = new ArrayList<>(1000000);
        IntStream.range(0, 1000000).forEach(i -> {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMap.put(key, value);
            keys.add(key);
        });
        keys.forEach(key -> Assert.isTrue(UUID.fromString(key).equals(hashMap.get(key))));
    }

    @Benchmark()
    @Fork(value = 1, warmups = 1)
    public void velMathodCall() {
        HashMapVel<String, UUID> hashMapVel = new HashMapVel<>();
        ArrayList<String> keysVel = new ArrayList<>(1000000);
        IntStream.range(0, 1000000).forEach(i -> {
            UUID value = UUID.randomUUID();
            String key = value.toString();
            hashMapVel.put(key, value);
            keysVel.add(key);
        });
        keysVel.forEach(key -> Assert.isTrue(UUID.fromString(key).equals(hashMapVel.get(key))));
    }
}
