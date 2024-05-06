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
@Table(name="save_job_db")
public class SaveJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="save_job_id")
    private int saveJobId;

    @Column(name="user_id")
    private int userId;

    @Column(name="recruitment_id")
    private int recruitmentId;
}
