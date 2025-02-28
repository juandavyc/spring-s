package com.juandavyc.SpringSecEx.service;

import com.juandavyc.SpringSecEx.entity.user.RoleEntity;
import com.juandavyc.SpringSecEx.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }
}
