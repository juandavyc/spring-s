package com.juandavyc.SpringSecEx.controller;

import com.juandavyc.SpringSecEx.entity.PermissionEntity;
import com.juandavyc.SpringSecEx.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    //@PreAuthorize("hasAuthority('READ')") // Ensure only users with READ authority can access
    public ResponseEntity<List<PermissionEntity>> getPermissions() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping(path = "{id}")
    //@PreAuthorize("hasAuthority('READ')") // Ensure only users with READ authority can access
    public ResponseEntity<PermissionEntity> permissions(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(permissionService.findById(id));
    }
}