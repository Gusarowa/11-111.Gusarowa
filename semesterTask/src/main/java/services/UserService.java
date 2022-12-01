package services;

import listeners.InitListener;
import models.User;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static final UserRepository repository = new UserRepository();

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public void save(User user) {
        repository.save(user);
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    public static boolean isAdmin() {
        return InitListener.getAuthUser().isPresent() && InitListener.getAuthUser().get().getRole().equals("admin");
    }

    public static boolean isAuth() {
        return InitListener.getAuthUser().isPresent();
    }

    public static User getAuthUser() {
        Optional<User> user = InitListener.getAuthUser();
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("Auth error");
        }
    }

}
