package com.josamtechie.api.controller;

import com.josamtechie.api.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class NotificationController
{
    private final NotificationService service;

    public NotificationController(NotificationService service)
    {
        this.service = service;
    }

    /**
     * Fire-and-forget: Returns immediately while email sends in background.
     * GET /api/send-email?email=user@example.com
     */
    @GetMapping("/send-email")
    public Map<String, String> sendEmail(@RequestParam String email) throws InterruptedException
    {
        System.out.println("[MainThread] Received request — triggering async email...");

        service.sendEmailNotification(email); // Non-blocking

        System.out.println("[MainThread] Returned response immediately (email still sending in background)");
        return Map.of(
                "status", "accepted",
                "message", "Email is being sent asynchronously to: " + email
        );
    }

    /**
     * Async with result: Waits for CompletableFuture to complete.
     * GET /api/process-report?name=Q1Sales
     */
    @GetMapping("/process-report")
    public Map<String, String> processReport(@RequestParam String name) throws Exception
    {
        System.out.println("[MainThread] Starting async report processing...");

        CompletableFuture<String> future = service.processReport(name);

        // .get() blocks until the async task finishes — useful when result is needed
        String result = future.get();

        return Map.of(
                "status", "completed",
                "result", result
        );
    }

    /**
     * Parallel async: Fires TWO async tasks simultaneously, waits for both.
     * GET /api/parallel
     */
    @GetMapping("/parallel")
    public Map<String, String> runParallel() throws Exception
    {
        System.out.println("[MainThread] Firing two async tasks in parallel...");

        CompletableFuture<String> task1 = service.processReport("Q1-Revenue");
        CompletableFuture<String> task2 = service.processReport("Q2-Revenue");

        // Wait for both to complete
        CompletableFuture.allOf(task1, task2).join();

        return Map.of(
                "task1", task1.get(),
                "task2", task2.get()
        );
    }
}
