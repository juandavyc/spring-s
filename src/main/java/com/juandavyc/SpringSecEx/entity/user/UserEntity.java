package com.juandavyc.SpringSecEx.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Builder
@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String firstname;
    private String lastname;

    private String country;
    private String password;

//    @Enumerated(EnumType.STRING)
//    private Role role;


    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})

    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<RoleEntity> roles = new HashSet<>();


    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> roles = new java.util.ArrayList<>(getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).toList());

        getRoles().stream()
                .flatMap(role->role.getPermissions().stream())
                .forEach(permission->{
                    roles.add(new SimpleGrantedAuthority(permission.getName().name()));
                });

        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
