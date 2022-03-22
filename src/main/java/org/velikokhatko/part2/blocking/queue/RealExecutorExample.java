package org.velikokhatko.part2.blocking.queue;

import java.util.*;
import java.util.concurrent.*;

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
            try {
                realExecutor.execute(getRunnable());
            } catch (RejectedExecutionException e) {
                System.out.println("Queue if full");
            }
        }
        realExecutor.shutDown();
    }

    private class RealExecutor implements java.util.concurrent.Executor {
        private static final int THREADS_MAX_SIZE = 2;
        private static final int QUEUE_MAX_SIZE = 2;
        private final Map<Thread, String> threads = new ConcurrentHashMap<>();
        private final Set<Worker> workers = new HashSet<>();
        private final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(QUEUE_MAX_SIZE);

        RealExecutor() {
            for (int i = 0; i < THREADS_MAX_SIZE; i++) {
                Worker worker = new Worker();
                workers.add(worker);
                threads.put(new Thread(worker), "");
            }
            threads.keySet().forEach(Thread::start);
        }

        public void shutDown() {
            workers.forEach(Worker::interrupt);
        }

        @Override
        public void execute(Runnable runnable) throws RejectedExecutionException, NullPointerException {
            if (!queue.offer(runnable)) {
                throw new RejectedExecutionException();
            }
        }

        private final class Worker implements Runnable {

            private volatile boolean isInterrupt = false;

            @Override
            public void run() {
                while (!isInterrupt) {
                    try {
                        queue.take().run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void interrupt() {
                isInterrupt = true;
            }
        }
    }
}
