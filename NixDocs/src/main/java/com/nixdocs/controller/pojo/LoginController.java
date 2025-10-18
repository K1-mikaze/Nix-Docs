package com.nixdocs.controller.pojo;

import com.nixdocs.model.User;
import com.nixdocs.repository.PostgresUserRepository;
import com.nixdocs.repository.UserRepository;
import com.nixdocs.util.templateEngine.ThymeleafUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginController implements Controller {

    private final UserRepository userRepository = new PostgresUserRepository();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            showLoginForm(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException,SQLException {
        handleLogin(request, response);
    }

    public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Sign In");
        ThymeleafUtil.renderTemplate(request, response, "signIn", variables);
    }

    public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Map<String, Object> variables = new HashMap<>();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        if (!password.isBlank() && !email.isBlank()) {
             checkIfUserExist(email,password,request,response,variables);
        }else {
            if (email.isBlank()) {
                variables.put("emailWarning", "Email is Empty");
            }
            if (password.isBlank()) {
                variables.put("passwordWarning", "Password is Empty");
            }
            ThymeleafUtil.renderTemplate(request,response,"signIn",variables);
        }
    }


    private void checkIfUserExist(String email,String password,HttpServletRequest request,HttpServletResponse response,Map<String,Object> variables) throws  SQLException,IOException {
        PostgresUserRepository postgresUserRepository = new PostgresUserRepository();
        Optional<User> user = postgresUserRepository.findByEmail(email);
        if (user.isEmpty() ){
            variables.put("emailError","User Not Found");
            ThymeleafUtil.renderTemplate(request,response,"signIn",variables);
            return;
        }else{
            User userObtained = user.get();
            if (postgresUserRepository.verifyPassword(password,userObtained.getPassword())){
                variables.put("user",userObtained);
                ThymeleafUtil.renderTemplate(request,response,"index",variables);
            }else {
                variables.put("passwordError", "Incorrect Password");
                ThymeleafUtil.renderTemplate(request,response,"signIn",variables);
            }
        }
    }

}