package com.nixdocs.controller.pojo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Controller {
    void processGet(HttpServletRequest request, HttpServletResponse response) throws  IOException;
    void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}