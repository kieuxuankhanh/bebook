package com.example.demo.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    @Email(message = "Vui lòng nhập theo định dạng email")
    @NotBlank(message = "Vui lòng nhập email")
    private String email;
    @NotBlank(message ="Vui lòng nhập tên người dùng!")
    private String username;
    @NotBlank(message = "Vui lòng nhập mật khẩu!")
    private String password;
}
