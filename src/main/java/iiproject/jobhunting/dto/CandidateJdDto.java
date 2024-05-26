package iiproject.jobhunting.dto;

import iiproject.jobhunting.entities.Cv;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateJdDto {
    private int userId;

    private String username;

    private String email;

    private String userAddress;

    private String phoneNumber;

    private String userDescription;

    private String userImage;

    private int jobDescriptionId;

    private String jobDescriptionAddress;

    private int quantity;

    private String experience;

    private String jobDescriptionRank;

    private String salary;

    private String title;

    private String position;

    private String jobDescriptionType;

    private String description;

    private String deadline;

    private String createdAt;

    private int view;

    private int companyId;

    private String categoryId;

    private Cv cv;
}
