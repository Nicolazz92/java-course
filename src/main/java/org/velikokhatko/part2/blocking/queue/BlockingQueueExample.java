package org.velikokhatko.part2.blocking.queue;

import java.util.*;

public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueueExample blockingQueueExample = new BlockingQueueExample();
        BlockingQueue blockingQueue = blockingQueueExample.new BlockingQueue();
        blockingQueue.start();
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean offer = blockingQueue.offer(() -> {
                String uuid = UUID.randomUUID().toString();
                String taskId = uuid.substring(uuid.lastIndexOf('-') + 1);
                System.out.printf("Task %s started%n", taskId);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Task %s finished%n", taskId);
            });
            if (!offer) {
                System.out.println("Queue is full");
            }
        }
    }

    public class BlockingQueue {
        private static final int THREADS_SIZE = 2;
        private static final int QUEUE_SIZE = 2;
        private final Queue<Runnable> queue = new LinkedList<>();
        private final Set<Thread> threads = new HashSet<>();

        public void start() {
            new Thread(() -> {
                while (true) {
                    get();
                }
            }).start();
        }

        public synchronized void get() {
            threads.removeIf(thread -> !thread.isAlive() || thread.isInterrupted());
            if (threads.size() >= THREADS_SIZE) {
                return;
            }
            if (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Runnable poll = queue.poll();
                if (poll != null) {
                    Thread thread = new Thread(poll);
                    threads.add(thread);
                    thread.start();
                }
            }
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
