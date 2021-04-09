package validator;

import entity.User;

public class UserValidator {

    public static void validateInsertUserFlow(User user){
        if(user == null || user.getUsername() == null){
            throw new IllegalArgumentException("User is null or name is empty");
        }
    }
}
