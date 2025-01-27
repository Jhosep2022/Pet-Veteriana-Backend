package com.project.pet_veteriana.controller;

import com.project.pet_veteriana.bl.NotificationService;
import com.project.pet_veteriana.dto.SubscriptionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/subscribe-seller")
    public ResponseEntity<String> sendSellerSubscriptionNotification(@RequestBody SubscriptionRequest request) {
        try {
            notificationService.sendSubscriptionNotification(request.getEmail(), request.getSellerName());
            return ResponseEntity.ok("Notificación enviada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al enviar la notificación: " + e.getMessage());
        }
    }
}
