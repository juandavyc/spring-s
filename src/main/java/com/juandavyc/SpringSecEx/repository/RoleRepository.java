package com.juandavyc.SpringSecEx.repository;

import com.juandavyc.SpringSecEx.entity.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository

extends JpaRepository<RoleEntity, Integer> {
}
