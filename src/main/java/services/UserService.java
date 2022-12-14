package services;

import lombok.RequiredArgsConstructor;
import models.User;
import repositories.UserRepository;

import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public boolean isAdmin(User user) {
        return user != null && user.getRole().equalsIgnoreCase("admin");
    }
}
