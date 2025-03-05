package com.juandavyc.SpringSecEx.repository;

import com.juandavyc.SpringSecEx.entity.PermissionEntity;
import com.juandavyc.SpringSecEx.entity.PermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository
        extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByName(PermissionEnum permission);


}
