package com.example.employeepayrollapp.Interface;


import com.example.employeepayrollapp.dto.AuthUserDTO;
import com.example.employeepayrollapp.model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;

}