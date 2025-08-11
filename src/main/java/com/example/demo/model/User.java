package com.example.demo.model;

import com.example.demo.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//@Table(name = "users")
//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String avatarUrl;
//    @Enumerated(EnumType.STRING)
    private Role roles;
    private LocalDate birthdate;

}
