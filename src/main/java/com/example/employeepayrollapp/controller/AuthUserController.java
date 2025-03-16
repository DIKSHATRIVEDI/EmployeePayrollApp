package com.example.employeepayrollapp.controller;

import com.example.employeepayrollapp.dto.AuthUserDTO;
import com.example.employeepayrollapp.dto.ForgetPasswordDTO;
import com.example.employeepayrollapp.dto.LoginDTO;
import com.example.employeepayrollapp.dto.ResponseDTO;
import com.example.employeepayrollapp.model.AuthUser;
import com.example.employeepayrollapp.service.AuthenticationService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        AuthUser user=authenticationService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        String result=authenticationService.login(loginDTO);
        ResponseDTO responseUserDTO=new ResponseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }

    @PutMapping("/forgetPassword/{email}")
    public ResponseEntity<ResponseDTO> forgetPassword(@PathVariable String email,
                                                      @Valid @RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        String response = authenticationService.forgetPassword(email, forgetPasswordDTO);
        return new ResponseEntity<>(new ResponseDTO(response, null), HttpStatus.OK);
    }

    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<ResponseDTO> resetPassword(@PathVariable String email,
                                                     @RequestParam String currentPassword,
                                                     @RequestParam String newPassword) {
        String response = authenticationService.resetPassword(email, currentPassword, newPassword);
        return new ResponseEntity<>(new ResponseDTO(response, null), HttpStatus.OK);
    }

}