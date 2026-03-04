// package com.bank.agent.credit_card_service.config;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import org.springframework.context.event.EventListener;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;

// import jakarta.annotation.PreDestroy;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.concurrent.Executors;
// import java.util.concurrent.ScheduledExecutorService;
// import java.util.concurrent.TimeUnit;

// @Component
// public class ServiceRegistryClient {

//     @Value("${service.registry.url}")
//     private String serviceRegistryUrl;

//     @Value("${spring.application.name}")
//     private String serviceName;

//     @Value("${server.port}")
//     private int serverPort;

//     private final RestTemplate restTemplate = new RestTemplate();
//     private ScheduledExecutorService scheduler;

//     @EventListener(ApplicationReadyEvent.class)
//     public void registerService() {
//         String serviceUrl = String.format("http://localhost:%d", serverPort);
//         Map<String, String> registration = new HashMap<>();
//         registration.put("name", serviceName);
//         registration.put("url", serviceUrl);

//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);
//         HttpEntity<Map<String, String>> request = new HttpEntity<>(registration, headers);

//         try {
//             restTemplate.postForEntity(serviceRegistryUrl + "/register", request, String.class);
//             System.out.println("Service " + serviceName + " registered successfully with registry.");
//             startHeartbeat();
//         } catch (Exception e) {
//             System.err.println("Failed to register service " + serviceName + " with registry: " + e.getMessage());
//         }
//     }

//     private void startHeartbeat() {
//         scheduler = Executors.newSingleThreadScheduledExecutor();
//         scheduler.scheduleAtFixedRate(() -> {
//             try {
//                 String serviceUrl = String.format("http://localhost:%d", serverPort);
//                 Map<String, String> heartbeat = new HashMap<>();
//                 heartbeat.put("name", serviceName);
//                 heartbeat.put("url", serviceUrl);

//                 HttpHeaders headers = new HttpHeaders();
//                 headers.setContentType(MediaType.APPLICATION_JSON);
//                 HttpEntity<Map<String, String>> request = new HttpEntity<>(heartbeat, headers);

//                 restTemplate.postForEntity(serviceRegistryUrl + "/heartbeat", request, String.class);
//                 System.out.println("Heartbeat sent for service " + serviceName);
//             } catch (Exception e) {
//                 System.err.println("Failed to send heartbeat for service " + serviceName + ": " + e.getMessage());
//             }
//         }, 30, 30, TimeUnit.SECONDS);
//     }

//     @PreDestroy
//     public void shutdownScheduler() {
//         if (scheduler != null) {
//             scheduler.shutdown();
//         }
//     }
// }
