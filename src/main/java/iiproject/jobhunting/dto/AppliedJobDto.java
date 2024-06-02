package iiproject.jobhunting.dto;

import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppliedJobDto {
    private String cvName;

    private String additionalInfo;

    private int jobDescriptionId;

    private int appliedJobId;

    private LocalDateTime createdAt;

    private int status;

    private JobDescription jobDescription;

    private User user;
}
