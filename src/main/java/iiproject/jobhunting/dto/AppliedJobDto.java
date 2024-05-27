package iiproject.jobhunting.dto;

import iiproject.jobhunting.entities.JobDescription;
import iiproject.jobhunting.entities.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppliedJobDto {
    private String cvName;

    private String additionalInfo;

    private int jobDescriptionId;
}
