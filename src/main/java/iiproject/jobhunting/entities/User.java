package iiproject.jobhunting.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @Column(name="delete_status")
    private int deleteStatus;

    @Column(name="deletedAt")
    private LocalDateTime deletedAt;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="role_id")
    private Role role;

    @OneToOne(mappedBy = "user")
    private Cv cv;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "user_job_db",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_description_id"))
    private List<JobDescription> jobDescriptions;
}
