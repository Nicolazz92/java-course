package org.velikokhatko.part2.blocking.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import static org.velikokhatko.part2.blocking.queue.Utils.getRunnable;

public class BlockingQueueExample {

    public static void main(String[] args) {
        BlockingQueueExample blockingQueueExample = new BlockingQueueExample();
        BlockingQueue<Runnable> blockingQueue = blockingQueueExample.new BlockingQueue<>();

        new Thread(() -> {
            while (true) {
                new Thread(blockingQueue.poll()).start();
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            boolean offer = blockingQueue.offer(getRunnable());
            if (!offer) {
                System.out.println("Queue is full");
            }
        }
    }

    public class BlockingQueue<R extends Runnable> extends AbstractBlockingQueue<R> {
        private static final int QUEUE_SIZE = 2;
        private final Queue<R> queue = new LinkedList<>();

        @Override
        public synchronized R poll() {
            if (queue.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return queue.poll();
        }

        @Override
        public synchronized boolean offer(R task) {
            if (queue.size() >= QUEUE_SIZE) {
                return false;
            }
            queue.add(task);
            notifyAll();
            return true;
        }
    }
}
