package org.velikokhatko.part2.blocking.queue;

import java.util.UUID;

public final class Utils {

    public static Runnable getRunnable() {
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
}
