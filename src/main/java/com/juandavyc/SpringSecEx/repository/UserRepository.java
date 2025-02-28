package com.juandavyc.SpringSecEx.repository;

import com.juandavyc.SpringSecEx.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

}
