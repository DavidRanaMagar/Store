package com.example.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "user_information")
public class UserInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userInformationId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", foreignKey = @ForeignKey(name = "user_information_ibfk_1"))
    private User user;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 255, nullable = true)
    private String email;

    @Column(length = 10, nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private Integer addressId;

    @Column(nullable = true)
    private Integer createdBy;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createAt;

    @Column(nullable = true)
    private Integer updatedBy;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    // Getters and Setters
}
