package com.thiagocarneiro.estudo.estudospring.controller;

import com.thiagocarneiro.estudo.estudospring.securiy.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class LoginViewController {

    @GetMapping("/")
    @ResponseBody
    public String pageHome(Authentication authentication) {
        if (authentication instanceof CustomAuthentication cusstomAuth) {
            System.out.println(cusstomAuth);
        }
        return "Olá Google: " + authentication.getName();
    }
}