package com.honeyfuc.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Login credentials")
public class JwtRequest {

    @Schema(description = "Username", example = "greatestPresidentEver@yahoo.com")
    @NotNull(message = "Username must not be null.")
    private String username;

    @Schema(description = "User password", example = "blondyboy1946")
    @NotNull(message = "Password must not be null.")
    private String password;

}
