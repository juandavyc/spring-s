package com.juandavyc.SpringSecEx.repository;

import com.juandavyc.SpringSecEx.entity.PermissionEnum;
import com.juandavyc.SpringSecEx.entity.RoleEntity;
import com.juandavyc.SpringSecEx.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

//    Optional<RoleEntity> findByName(RoleEnum name);
//    List<RoleEntity> findByPermissionsNameIn(Set<PermissionEnum> permissionEnums);


}
