package com.example.demo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(max = 20)
    private String city;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 10)
    private String phoneNumber;

    @NotNull
    private String speciality;

    // Getters and Setters
}
