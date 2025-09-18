package nixdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import nixdocs.controller.UserController;
import nixdocs.repository.UserRepository;
import nixdocs.service.UserService;
import nixdocs.mapper.UserMapper;

public class Main { 

    public static final UserRepository USER_REPOSITORY = new UserRepository();
    public static final UserService USER_SERVICE =
        new UserService(() -> UserMapper.toModelList(USER_REPOSITORY.users()));

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final UserController USER_CONTROLLER = new UserController(
        () -> UserMapper.toDtoList(UserMapper.toModelList(USER_REPOSITORY.users())),
        users -> {
            try {
                return OBJECT_MAPPER.writeValueAsString(users);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    );

    public static void main(String[] args) throws Exception {
        var server = new Server(Integer.parseInt(System.getProperty("API_PORT", "8080")));
        var context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder(USER_CONTROLLER), "/users");
        server.setHandler(context);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }
}
