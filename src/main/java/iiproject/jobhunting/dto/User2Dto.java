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
//    private int userId;

    private String username;

    @NotBlank(message = "Enter the email!!!")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Email has to match with the format: abc@xyz.oiu!!!")
    private String email;

    private String userAddress;

    private String userPhoneNumber;

    private String userDescription;
}
