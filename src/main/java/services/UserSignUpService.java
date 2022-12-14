package services;

import lombok.RequiredArgsConstructor;
import models.User;
import repositories.UserRepository;

@RequiredArgsConstructor
public class UserSignUpService {
    private final UserRepository userRepository;

    public void signUp(User user) {
        userRepository.save(user);
    }
}
