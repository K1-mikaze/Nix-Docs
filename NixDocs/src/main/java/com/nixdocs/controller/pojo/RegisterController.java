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
        PostgresUserRepository postgresUserRepository = new PostgresUserRepository();
        Map<String, Object> variables = checkConditions(postgresUserRepository,request);
        if (!variables.isEmpty()){
            ThymeleafUtil.renderTemplate(request,response,"createAccount",variables);
        }else {
            User user = new User(request.getParameter("username"),request.getParameter("email"),request.getParameter("password"));
            postgresUserRepository.save(user);
            ThymeleafUtil.renderTemplate(request,response,"signIn",variables);
        }
    }

    private Map<String,Object> checkConditions(PostgresUserRepository repository,HttpServletRequest request) throws SQLException{
        Map<String, Object> variables = new HashMap<>();
        String email = request.getParameter("email").trim();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String repeatPassword = request.getParameter("repeat-password").trim();

        if (password.isBlank() || repeatPassword.isBlank()){
            variables.put("passwordWarning", "password is Empty");
        }else if (!passwordSimilarity(password,repeatPassword)) {
            variables.put("passwordWarning", "The passwords are not Equal");
        }
        if (email.isBlank()){
            variables.put("emailWarning","Email is Empty");
        }
        else if (repository.findByEmail(email).isPresent()){
            variables.put("emailWarning","Email Already in use");
        }

        if (username.isBlank()){
            variables.put("usernameWarning","Username is Empty");
        }else if (repository.findByUsername(username).isPresent()){
            variables.put("usernameAlreadyExist","Username Already in use");
        }

        return variables;
    }

    private boolean passwordSimilarity(String password1,String password2){
        return password1.equals(password2);
    }
}