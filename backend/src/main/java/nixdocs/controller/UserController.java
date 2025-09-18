package nixdocs.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nixdocs.dto.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@WebServlet(value = "/users/*")
public class UserController extends HttpServlet {
    
    private final Supplier<List<UserDto>> getUser; 
    private final Function<List<UserDto>, String> serializer; 

    public UserController(Supplier<List<UserDto>> getUser, Function<List<UserDto>, String> serializer){
        this.getUser = getUser;
        this.serializer = serializer;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json"); 

        try {
            response.setStatus(HttpServletResponse.SC_OK); 
            response.getWriter().print(
                serializer.apply(getUser.get())
            );
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("""
                {
                    "status": 500,
                    "error": "Internal Server Error"
                }
            """);
        }
    }
}
