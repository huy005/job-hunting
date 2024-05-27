package iiproject.jobhunting.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(name="verification_status")
    private int varificationStatus;

    @Column(name="token")
    private String token;

    @Column(name="token_expiry_date")
    private LocalDateTime tokenExpiryDate;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="update_at")
    private LocalDateTime updatedAt;

    @Column(name="delete_status")
    private int deleteStatus;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<AppliedJob> appliedJob;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;


    @ManyToMany
    @JoinTable(
            name = "candidate_company_db",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> candidateCompanies;

    @ManyToMany
    @JoinTable(
            name = "user_job_db",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_description_id"))
    private Set<JobDescription> jobDescriptions;
}
