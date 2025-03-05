package com.juandavyc.SpringSecEx.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")

@Setter
@Getter
@ToString

@NoArgsConstructor
@AllArgsConstructor

@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String username;
    public String password;
    public String lastname;
    public String firstname;

    //@Column(name = "is_enabled")
    private boolean isEnabled;

    //@Column(name = "account_No_Expired")
    private boolean accountNoExpired;

   // @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    //@Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<RoleEntity> roles;


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UserEntity that = (UserEntity) object;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(lastname, that.lastname) && Objects.equals(firstname, that.firstname) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, lastname, firstname, roles);
    }
}
