package com.juandavyc.SpringSecEx.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
@ToString

@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,updatable = false)
    @Enumerated(EnumType.STRING)
    private Permission name;
}
