package org.velikokhatko.part2.blocking.queue;

import java.util.*;

public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueueExample blockingQueueExample = new BlockingQueueExample();
        BlockingQueue blockingQueue = blockingQueueExample.new BlockingQueue();

        new Thread(() -> {
            while (true) {
                new Thread(blockingQueue.get()).start();
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            boolean offer = blockingQueue.offer(getRunnable());
            if (!offer) {
                System.out.println("Queue is full");
            }
        }
    }

    private static Runnable getRunnable() {
        return () -> {
            String uuid = UUID.randomUUID().toString();
            String taskId = uuid.substring(uuid.lastIndexOf('-') + 1);
            System.out.printf("Task %s started%n", taskId);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Task %s finished%n", taskId);
        };
    }

    public class BlockingQueue {
        private static final int QUEUE_SIZE = 2;
        private final Queue<Runnable> queue = new LinkedList<>();

        public synchronized Runnable get() {
            if (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return queue.poll();
        }

        public synchronized boolean offer(Runnable task) {
            if (queue.size() >= QUEUE_SIZE) {
                return false;
            }
            queue.add(task);
            notify();
            return true;
        }
    }
}
