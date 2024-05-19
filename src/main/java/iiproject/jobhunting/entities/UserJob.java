package iiproject.jobhunting.entities;

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
@Table(name="user_job_db")
public class UserJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_job_id")
    private int userJobId;

    @Column(name="user_id")
    private int userId;

    @Column(name="job_description_id")
    private int jobDescriptionId;
}
