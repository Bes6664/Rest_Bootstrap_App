package com.bootstrap.Bootstrap_App.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int id;

    @Override
    public String toString() {
        return role + ' ';
    }

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    @JsonBackReference
    List<User> users;

    public Role(String role, List<User> users) {
        this.role = role;
        this.users = users;
    }

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
