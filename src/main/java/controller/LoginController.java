package controller;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.UserService;
import utils.ApplicationUtils;

import java.io.IOException;

public class LoginController {

    private final UserService userService = new UserService();
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;

    public void doLogin(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(username.equals("")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You need to complete the username filed.");
            return;
        }

        if(password.equals("")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You need to complete the password field.");
            return;
        }

        if(userService.userExists(username)) {

            UserDto userDto = userService.getUserByName(username);

            if (userDto.getPassword().equals(password)) {
                if (userDto.getType().equals("admin")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-home-frame.fxml"));
                    ApplicationUtils.newFrame(loader, event, "Admin Home");
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/user-home-frame.fxml"));
                    ApplicationUtils.newFrame(loader, event, "User Home");
                    UserHomeController userHomeController = loader.getController();
                    userHomeController.initialize(userDto);
                }
            } else {
                //wrong password
                ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Wrong Password. Try again.");
            }
        }
        else{
            //wrong user
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "User doesn't exist. Try again.");
        }
    }

    public void createAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/signup-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "Create Account");
    }
}
