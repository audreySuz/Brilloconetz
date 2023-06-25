package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConcurrentVersion {

    private static final int MAX_PANCAKES_PER_USER = 5;
    private static final int MAX_PANCAKES_PER_PREPARATION = 12;
    private static int NO_OF_PANCAKES_LEFT = 12;
    private static final String[] users = {"John", "Mary", "Bob"};
    private static final long TIME_PER_PANCAKE = 6000; // 6 seconds per pancake

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<CompletableFuture<Void>> eatTasks = new ArrayList<>();
        System.out.println("Start time: " + System.currentTimeMillis());

        System.out.println("Users Eating.....");
        for (String user : users) {
            eatTasks.add(eatPancakes(user, startTime));
        }
        CompletableFuture.allOf(eatTasks.toArray(new CompletableFuture[0])).join();

        System.out.println("End time: " + System.currentTimeMillis());

        if (NO_OF_PANCAKES_LEFT >= 0) {
            System.out.println("Shopkeeper was able to meet the needs of all users");
            System.out.println("No of pancakes left: " + NO_OF_PANCAKES_LEFT);
        } else {
            System.out.println("Shopkeeper was not able to meet the needs of all users, "
                    + "he needs: " + Math.abs(NO_OF_PANCAKES_LEFT));
        }
    }

    private static CompletableFuture<Void> eatPancakes(String user, long startTime) {
        int pancakesPerUser = (int) (Math.random() * MAX_PANCAKES_PER_USER + 1);
        long eatingTime = pancakesPerUser * TIME_PER_PANCAKE;
        long finishingTime = startTime + eatingTime;
        while (System.currentTimeMillis() < finishingTime) {
            // Simulating eating time
        }
        System.out.println(user + " ate " + pancakesPerUser + " pancakes");
        NO_OF_PANCAKES_LEFT -= pancakesPerUser;
        return CompletableFuture.runAsync(() -> {});
    }
}
