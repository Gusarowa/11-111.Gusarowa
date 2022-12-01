package utils;

import models.User;
import services.UserService;

public class Utils {
    public static boolean isPostBelongsToUser(Long userID) {
        return UserService.isAuth() && UserService.getAuthUser().getId().equals(userID);
    }

    public static String getUsername(Long userID) {
        UserService service = new UserService();
        for (User user : service.getAllUsers()) {
            if (user.getId().equals(userID)) {
                return user.getLogin();
            }
        }
        return "";
    }
}
