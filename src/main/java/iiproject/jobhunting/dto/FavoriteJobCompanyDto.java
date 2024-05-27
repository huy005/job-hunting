package iiproject.jobhunting.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteJobCompanyDto {
    private int jobDescriptionId;

    private int favoriteJobStatus;

    private int companyId;

    private int favoriteCompanyStatus;
}
