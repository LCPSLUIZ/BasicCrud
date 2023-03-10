package com.AB3.API.AB3.API.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "UserInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 40)
    private String lastName;

    @Column(nullable = false, length = 40)
    private String passWord;

    @Column(nullable = false, unique = true, length = 30)
    private String eMail;

    @Column(unique = true, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDateTime dateTimeOfRequest;







}
