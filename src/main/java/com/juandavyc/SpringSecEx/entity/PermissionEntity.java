package com.juandavyc.SpringSecEx.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")

@Setter
@Getter
@ToString

@NoArgsConstructor
@AllArgsConstructor

@Builder
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, insertable = false, updatable = false)
    public PermissionEnum name;

//    @Column(unique = true, nullable = false, updatable = false)
//    private String name;

}
