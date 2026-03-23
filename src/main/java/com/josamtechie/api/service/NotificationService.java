package com.josamtechie.api.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService
{
    /**
     * ✅ Fire-and-forget: No return value.
     * Caller doesn't wait — method runs in a separate thread.
     */
    public void sendEmailNotification(String email) throws InterruptedException
    {
        System.out.printf("[%s] 📧 Sending email to: %s%n", Thread.currentThread().getName(), email);
        Thread.sleep(2000); // Simulates delay (e.g., SMTP call)
        System.out.printf("[%s] ✅ Email sent to: %s%n", Thread.currentThread().getName(), email);
    }

    /**
     * ✅ Async with result: Returns CompletableFuture.
     * Caller can .get() the result or chain callbacks.
     */
    @Async("taskExecutor")
    public CompletableFuture<String> processReport(String reportName) throws InterruptedException
    {
        System.out.printf("[%s] 📊 Processing report: %s%n", Thread.currentThread().getName(), reportName);
        Thread.sleep(3000); // Simulates heavy processing
        String result = "Report '%s' processed successfully".formatted(reportName);
        System.out.printf("[%s] ✅ Done: %s%n", Thread.currentThread().getName(), result);
        return CompletableFuture.completedFuture(result);
    }
}
