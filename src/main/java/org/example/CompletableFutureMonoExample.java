package org.example;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureMonoExample {

    public static void main(String[] args) {
        Mono<Mono<User>> nestedMono = fetchUserData("userId123");

        Mono<User> userMono = nestedMono.flatMap(userMonoInner -> userMonoInner);

        userMono.subscribe(
                user -> System.out.println("User retrieved: " + user), // Handle the user
                error -> System.err.println("Error: " + error),         // Handle error
                () -> System.out.println("Operation completed")         // On completion
        );
    }

    private static Mono<Mono<User>> fetchUserData(String userId) {
        // Simulate fetching Mono<User> asynchronously using CompletableFuture
        CompletableFuture<Mono<User>> future = CompletableFuture.supplyAsync(() -> {
            if (userId.equals("error")) {
                return Mono.error(new RuntimeException("User not found"));
            }
            return Mono.just(new User(userId, "John Doe"));
        });

        return Mono.fromFuture(future);
    }

    static class User {
        String userId;
        String name;

        User(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

