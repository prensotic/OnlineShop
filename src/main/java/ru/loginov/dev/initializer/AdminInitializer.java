package ru.loginov.dev.initializer;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.loginov.dev.services.AdminService;

@Component
public class AdminInitializer {

    private final AdminService adminService;

    public AdminInitializer(AdminService adminService) {
        this.adminService = adminService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        adminService.createInitialSuperAdmin();
    }
}