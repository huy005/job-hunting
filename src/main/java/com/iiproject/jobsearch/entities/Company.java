package com.iiproject.jobsearch.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="company_db")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private int companyId;

    @Column(name="company_address")
    private String companyAddress;

    @Column(name="company_email")
    private String companyEmail;

    @Column(name="company_logo")
    private String companyLogo;

    @Column(name="company_name")
    private  String companyName;

    @Column(name="company_phone_number")
    private String companyPhoneNumber;

    @Column(name="company_description")
    private String companyDescription;

    @Column(name="status")
    private int status;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="follow_company_id",
    joinColumns = @JoinColumn(name = "company_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
