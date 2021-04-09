package service;

import dto.UserDto;
import entity.User;
import exception.CustomExceptionMessages;
import exception.EntityNotExistsException;
import mappers.UserMapper;
import repository.UserRepo;
import utils.ApplicationUtils;
import validator.UserValidator;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserRepo userRepo;

    public UserService(){
        this.userRepo = new UserRepo();
    }

    public void insertUser(UserDto userDto){
        User user = UserMapper.dtoToEntity(userDto);
        UserValidator.validateInsertUserFlow(user);
        user.setId_user(ApplicationUtils.generateUUID());
        userRepo.insertNewUser(user);
    }

    public void deleteUser(UserDto userDto){
        User user = userRepo.findUserById(userDto.getId_user());
        userRepo.deleteUser(user);
    }

    public void updateUser(UserDto userDto){
        User user = UserMapper.dtoToEntity(userDto);
        userRepo.updateUser(user);
    }

    public UserDto getUserById(String id_user){
        if(id_user == null || id_user.equals("")){
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_ID_MESSAGE);
        }

        User user = userRepo.findUserById(id_user);

        if(user == null){
            throw new EntityNotExistsException("No user having id " + id_user + " exists.");
        }

        return UserMapper.entityToDto(user);
    }

    public UserDto getUserByName(String username){
        if(username == null || username.equals("")){
            throw new IllegalArgumentException(CustomExceptionMessages.INVALID_USERNAME_MESSAGE);
        }

        User user = userRepo.findUserByName(username);

        if(user == null){
            throw new EntityNotExistsException("No user having username " + username + " exists.");
        }

        return UserMapper.entityToDto(user);
    }

    public boolean userExists(String username){
        User user = userRepo.findUserByName(username);
        return user != null;
    }

    public List getAllEmails(){
       return userRepo.getAllEmails();
    }

    public List<UserDto> getAllUsers(){
        List<User> users = userRepo.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(UserMapper.entityToDto(user));
        }

        return userDtos;
    }
}

