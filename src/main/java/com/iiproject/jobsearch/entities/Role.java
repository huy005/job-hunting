package com.iiproject.jobsearch.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role_db")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleId;

    @Column(name="role_name")
    private String roleName;

//    @OneToOne(mappedBy = "role", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
//    private User user;
}
