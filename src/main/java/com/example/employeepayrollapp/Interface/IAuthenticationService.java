package com.example.employeepayrollapp.Interface;


import com.example.employeepayrollapp.dto.AuthUserDTO;
import com.example.employeepayrollapp.dto.ForgetPasswordDTO;
import com.example.employeepayrollapp.dto.LoginDTO;
import com.example.employeepayrollapp.model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO);
    String logout(Long userId, String token);
    public String forgetPassword(String email, ForgetPasswordDTO forgetPasswordDTO);
    public String resetPassword(String email, String currentPassword, String newPassword);

}