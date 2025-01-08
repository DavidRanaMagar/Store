package com.example.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(name = "createdAt", nullable = true, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "createdBy", nullable = true)
    private Integer createdBy;

    @Column(name = "updatedAt")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "updatedBy", nullable = true)
    private Integer updatedBy;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "roleId", foreignKey = @ForeignKey(name = "users_ibfk_1"))
    private Role role;

    public User(String username, String password, Integer createdBy, Role role) {
        this.username = username;
        this.password = password;
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
        this.role = role;
    }
}
