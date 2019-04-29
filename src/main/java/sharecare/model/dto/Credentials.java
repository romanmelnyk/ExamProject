package sharecare.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Credentials {
    @NotNull
    String email;
    @NotNull
    String password;
}
