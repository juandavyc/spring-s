package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.PermissionEntity;
import com.juandavyc.SpringSecEx.entity.PermissionEnum;

import java.util.List;

public interface PermissionService {

    PermissionEntity findById(Long id);
    PermissionEntity findByName(PermissionEnum permission);

    List<PermissionEntity> findAll();


}
