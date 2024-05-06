package iiproject.jobhunting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="apply_post_db")
public class ApplyPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="apply_post_id")
    private int companyId;

    @Column(name="cv_name")
    private String cvName;

    @Column(name="apply_post_text")
    private String applyPostText;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="user_id")
    private int userId;

    @Column(name="recruitment_id")
    private int recruitmentId;

    @Column(name="status")
    private int status;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="recruitment_id")
    private List<JobDescription> jobDescription;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private List<User> user;
}
