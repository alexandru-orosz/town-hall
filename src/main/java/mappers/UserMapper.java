package mappers;

import dto.UserDto;
import entity.User;

public class UserMapper {

    public static UserDto entityToDto(User user){
        UserDto userDto =  new UserDto();
        userDto.setId_user(user.getId_user());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setType(user.getType());
        return userDto;
    }

    public static User dtoToEntity(UserDto userDto){
        User user = new User();
        user.setId_user(userDto.getId_user());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setType(userDto.getType());
        return user;
    }
}
