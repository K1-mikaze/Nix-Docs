package nixdocs.service;

import java.util.List;
import java.util.function.Supplier;

import nixdocs.model.User;

public class UserService {
    
    private final Supplier<List<User>> getUsers;

    public UserService(Supplier<List<User>> getUsers) {
        this.getUsers = getUsers;
    }

    public List<User> findAll() {
        return List.copyOf(getUsers.get()); 
    }
}
