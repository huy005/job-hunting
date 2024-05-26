package iiproject.jobhunting.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cv_db")
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cv_id")
    private int cvId;

    @Column(name="cv_file_name")
    private String cvFileName;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    @Column(name="delete_status")
    private int deleteStatus;

    @Column(name="deletedAt")
    private LocalDateTime deletedAt;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;
}
