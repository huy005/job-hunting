package iiproject.jobhunting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;

    private String username;

    @NotBlank(message = "Enter the email!!!")
    @Pattern(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$", message = "Email has to match with the format: abc@xyz.oiu!!!")
    private String email;

    private String userAddress;

    @NotBlank(message = "Enter password!!!")
    @Size(min = 6, max = 12, message = "Password contains 6-12 characters!!!")
    private String password;

    private String userImage;

    private String userPhoneNumber;

    private String userDescription;

    private String role;

//    private Cv cv;
//
//    private List<Company> companies;
}
