package iiproject.jobhunting.dto;

import iiproject.jobhunting.entities.Category;
import iiproject.jobhunting.entities.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDescriptionDto {
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

    private Company company;

    private Category category;
}
