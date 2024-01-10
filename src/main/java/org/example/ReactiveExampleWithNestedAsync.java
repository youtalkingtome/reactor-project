package org.example;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class ReactiveExampleWithNestedAsync {

    public static void main(String[] args) {
        Mono<Mono<User>> nestedMono = fetchUserData("userId123");

        nestedMono.flatMap(mono -> mono)  // Flatten the nested Mono
                .subscribe(
                        user -> handleUser(user),              // onNext: Handle the user data
                        error -> handleError(error),           // onError: Handle errors
                        () -> System.out.println("Completed")  // onComplete: Completion signal
                );
    }

    private static Mono<Mono<User>> fetchUserData(String userId) {
        // Simulate an asynchronous operation returning CompletableFuture<Mono<User>>
        CompletableFuture<Mono<User>> future = CompletableFuture.supplyAsync(() -> {
            if (userId.equals("error")) {
                return Mono.error(new RuntimeException("User not found"));
            }
            return Mono.just(new User(userId, "John Doe"));
        });

        return Mono.fromFuture(future);
    }

    private static void handleUser(User user) {
        // Process user data
        System.out.println("User data: " + user);
    }

    private static void handleError(Throwable error) {
        // Handle error scenario
        System.err.println("Error occurred: " + error.getMessage());
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

