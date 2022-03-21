package org.velikokhatko.part2.blocking.queue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.velikokhatko.part2.blocking.queue.Utils.getRunnable;

public class RealExecutorExample {

    public static void main(String[] args) {
        RealExecutorExample realExecutorExample = new RealExecutorExample();
        RealExecutor realExecutor = realExecutorExample.new RealExecutor();
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            realExecutor.execute(getRunnable());
        }
    }

    private class RealExecutor implements java.util.concurrent.Executor {
        private static final int THREADS_MAX_SIZE = 2;
        private static final int QUEUE_MAX_SIZE = 2;
        private final Map<Thread, String> threads = new ConcurrentHashMap<>();
        private final Queue<Runnable> queue = new ConcurrentLinkedQueue<>();

        @Override
        public void execute(Runnable runnable) {
            try {
                if (queue.size() < QUEUE_MAX_SIZE) {
                    queue.add(runnable);
                } else {
                    System.out.println("Queue is full");
                }
            } finally {
                new Thread(this::runNextRunnable).start();
            }
        }

        private void runNextRunnable() {
            if (!queue.isEmpty()) {
                if (threads.size() < THREADS_MAX_SIZE) {
                    Thread newThread = new Thread(queue.poll());
                    newThread.start();
                    threads.put(newThread, "");
                } else {
                    while (!removeFromCHMap(threads)) {}
                    runNextRunnable();
                }
            }
        }

        private boolean removeFromCHMap(Map<Thread, String> threads) {
            Set<Thread> toRemove = threads.keySet().stream()
                    .filter(t -> !t.isAlive() || t.isInterrupted())
                    .collect(Collectors.toSet());
            if (toRemove.isEmpty()) {
                return false;
            } else {
                toRemove.forEach(threads::remove);
                return true;
            }
        }
    }
}
