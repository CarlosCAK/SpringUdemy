package com.carlosjpa.libraryapi.Controller;

import com.carlosjpa.libraryapi.security.CustomAuthentication;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String paginaDeLogin() {
        return "login";
    }
    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if(authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }
       return "Olá"+  authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@Param("code") String code) {
        return "Seu authorization code é:" + code;
    }
}