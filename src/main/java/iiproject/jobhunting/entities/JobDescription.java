package iiproject.jobhunting.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="job_description_db")
public class JobDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="job_description_id")
    private int jobDescriptionId;

    @Column(name="job_description_address")
    private String jobDescriptionAddress;

    @Column(name="quantity")
    private int quantity;

    @Column(name="experience")
    private String experience;

    @Column(name="job_description_rank")
    private  String jobDescriptionRank;

    @Column(name="salary")
    private String salary;

    @Column(name="title")
    private String title;

    @Column(name="position")
    private String position;

    @Column(name="job_description_type")
    private String jobDescriptionType;

    @Column(name="description")
    private String description;

    @Column(name="deadline")
    private String deadline;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="status")
    private int status;

    @Column(name="view")
    private int view;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @Column(name="delete_status")
    private int deleteStatus;

    @Column(name="deletedAt")
    private LocalDateTime deletedAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany(mappedBy = "jobDescriptions")
    private Set<User> candidates;
}
