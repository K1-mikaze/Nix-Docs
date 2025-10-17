package com.nixdocs.controller.pojo;

import com.nixdocs.model.User;
import com.nixdocs.repository.PostgresUserRepository;
import com.nixdocs.util.templateEngine.ThymeleafUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
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
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Map<String, Object> variables = new HashMap<>();
        PostgresUserRepository postgresUserRepository = new PostgresUserRepository();
        if (!passwordSimilarity(request.getParameter("password"),request.getParameter("repeat-password"))) {
            variables.put("passwordNotEqual", "The passwords are not Equal");
        }
        if (postgresUserRepository.findByEmail(request.getParameter("email")).isPresent()){
            variables.put("emailAlreadyExist","Email Already in use");
            System.out.println(postgresUserRepository.findByEmail(request.getParameter("email")).isEmpty());
        }
        if (postgresUserRepository.findByUsername(request.getParameter("username")).isPresent()){
            variables.put("usernameAlreadyExist","Username Already in use");
            System.out.println(postgresUserRepository.findByUsername(request.getParameter("username")).isEmpty());
        }
        if (!variables.isEmpty()){
            ThymeleafUtil.renderTemplate(request,response,"createAccount",variables);
        }else {
            User user = new User(request.getParameter("username"),request.getParameter("email"),request.getParameter("password"));
            postgresUserRepository.save(user);
            ThymeleafUtil.renderTemplate(request,response,"index",variables);
        }
    }

    private boolean passwordSimilarity(String password1,String password2){
        return password1.equals(password2);
    }
}