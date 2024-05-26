package iiproject.jobhunting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User2Dto {
    private int userId;

    private String username;

    private String userAddress;

    private String userPhoneNumber;

    private String userDescription;

    private String userImageFileName;

    private String cvFileName;
}
