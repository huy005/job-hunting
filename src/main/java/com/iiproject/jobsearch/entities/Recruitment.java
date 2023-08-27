package com.iiproject.jobsearch.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="recruitment_db")
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recruitment_id")
    private int recruitmentId;

    @Column(name="recruitment_address")
    private String recruitmentAddress;

    @Column(name="quantity")
    private String quantity;

    @Column(name="experience")
    private String experience;

    @Column(name="recruitment_rank")
    private  String recruitmentRank;

    @Column(name="salary")
    private String salary;

    @Column(name="title")
    private String title;

    @Column(name="recruitment_type")
    private String recruitmentType;

    @Column(name="recruitment_description")
    private String recruitmentDescription;

    @Column(name="deadline")
    private String deadline;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="view")
    private int view;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="company_id")
    private Company company;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

}
