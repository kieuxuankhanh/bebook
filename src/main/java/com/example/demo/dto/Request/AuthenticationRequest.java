package com.example.demo.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "Vui lòng nhập tên người dùng!")
    @Size(min = 6, max = 50, message = "Tên người dùng phải lớn hơn 6 và nhỏ hơn 50")
    private String username;
    @NotBlank(message = "Vui lòng điền mật khẩu")
    @Size(min = 6,message = "Vui lòng điền mật khẩu tối thiểu 6 kí tự")
    private String password;
}
