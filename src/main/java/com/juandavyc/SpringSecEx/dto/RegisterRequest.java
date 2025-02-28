package com.juandavyc.SpringSecEx.dto;

import com.juandavyc.SpringSecEx.entity.user.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String country;

    private List<Integer> roles;

}
