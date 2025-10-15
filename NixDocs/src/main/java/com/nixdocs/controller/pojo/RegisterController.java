package com.nixdocs.controller.pojo;

import com.nixdocs.util.templateEngine.ThymeleafUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterController implements Controller {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Create Account");
            ThymeleafUtil.renderTemplate(request, response, "createAccount", variables);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> variables = new HashMap<>();
        if (!passwordSimilarity(request.getParameter("password"),request.getParameter("repeat-password"))) {
            variables.put("passwordNotEqual", "The passwords are not Equal");
        }
        if (emailAlreadyExist()){
            variables.put("emailAlreadyExist","Email Already in use");
        }
        if (usernameAlreadyExist()){
            variables.put("usernameAlreadyExist","Username Already in use");
        }
        ThymeleafUtil.renderTemplate(request,response,"createAccount",variables);
    }

    private boolean passwordSimilarity(String password1,String password2){
        return password1.equals(password2);
    }

    private boolean emailAlreadyExist(){
        // !TODO check if email exist in the database
        return true;
    }
    private boolean usernameAlreadyExist(){
        // !TODO check if username exist in the database
        return true;
    }
}