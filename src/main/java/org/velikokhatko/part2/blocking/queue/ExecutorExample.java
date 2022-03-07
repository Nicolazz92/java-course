package org.velikokhatko.part2.blocking.queue;

import java.util.*;

public class ExecutorExample {

    public static void main(String[] args) {
        ExecutorExample executorExample = new ExecutorExample();
        Executor executor = executorExample.new Executor();
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean offer = executor.offer(getRunnable());
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

    public class Executor {
        private static final int THREAD_POOL_SIZE = 2;
        private static final int QUEUE_SIZE = 2;
        private final Queue<Runnable> queue = new LinkedList<>();
        private final Set<Thread> threadPool = new HashSet<>();

        public Executor() {
            new Thread(() -> {
                while (true) {
                    get();
                }
            }).start();
        }

        public synchronized void get() {
            threadPool.removeIf(thread -> !thread.isAlive() || thread.isInterrupted());
            if (threadPool.size() >= THREAD_POOL_SIZE) {
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
                    threadPool.add(thread);
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
