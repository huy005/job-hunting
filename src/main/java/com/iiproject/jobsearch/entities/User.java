package com.iiproject.jobsearch.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @Column(name="user_full_name")
    private String username;

    @Column(name="user_email")
    private String email;

    @Column(name="user_address")
    private String userAddress;

    @Column(name="user_password")
    private String password;

    @Column(name="user_image")
    private  String userImage;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="user_description")
    private String userDescription;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="role_id")
    private Role role;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="cv_id")
    private Cv cv;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;
}
