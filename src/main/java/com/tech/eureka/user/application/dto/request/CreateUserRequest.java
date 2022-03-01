package com.tech.eureka.user.application.dto.request;

import com.tech.eureka.user.domain.Phone;
import com.tech.eureka.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotEmpty
    private String name;

    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Debe proporcionar un correo electronico valido")
    private String email;

//    Min 1 uppercase letter.
//    Min 1 lowercase letter.
//    Min 1 special character.
//    Min 1 number.
//    Min 4 characters.
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{4,}$", message = "Debe proporcionar una contraseña valida")
    private String password;

    private List<Role> roles;
    private List<Phone> phones;
}
