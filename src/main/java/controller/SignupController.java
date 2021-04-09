package controller;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import service.UserService;
import utils.ApplicationUtils;

import java.io.IOException;
import java.util.List;

public class SignupController {

    private final UserService userService = new UserService();
    private final UserDto userDto = new UserDto();
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TextField emailTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;

    private Border redBorder;
    private Border defaultBorder;

    public void initialize(){
        redBorder = new Border(new BorderStroke(Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        final TextField defaultTextField = new TextField();
        defaultTextField.setStyle("-fx-background-color: #3b3b3b; -fx-text-fill: #b1b1b1; -fx-prompt-text-fill: #626262; -fx-background-insets: 0 0 -1 0,0,1; -fx-background-radius: 5,5,4");
        defaultBorder = defaultTextField.getBorder();
    }

    public void doSignup(){
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        emailTextField.borderProperty().setValue(defaultBorder);
        usernameTextField.borderProperty().setValue(defaultBorder);
        passwordTextField.borderProperty().setValue(defaultBorder);
        confirmPasswordTextField.borderProperty().setValue(defaultBorder);

        if(!emailChecker(email)){
            emailTextField.borderProperty().setValue(redBorder);
            return;
        }

        if(!usernameChecker(username)){
            usernameTextField.borderProperty().setValue(redBorder);
            return;
        }

        if(!passwordChecker(password)){
            passwordTextField.borderProperty().setValue(redBorder);
            return;
        }

        if(!confirmPasswordChecker(password, confirmPassword)){
            confirmPasswordTextField.borderProperty().setValue(redBorder);
            return;
        }

        //valid data

        userDto.setEmail(email);
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setType("user");

        userService.insertUser(userDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Your account has been created.");

        emailTextField.clear();
        usernameTextField.clear();
        passwordTextField.clear();
        confirmPasswordTextField.clear();
    }

    public boolean emailChecker(String email){
        if (email.equals("")){
            return false;
        }

        if(!email.contains("@") || !email.contains(".")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Invalid email.");
            return false;
        }

        List list = userService.getAllEmails();
        if(list.contains(email)){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "This email is associated with another account.");
            return false;
        }

        return true;
    }

    public boolean usernameChecker(String username){
        if(username.equals("")){
            return false;
        }

        if(username.length() < 4){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Username must be of minimum 4 characters length.");
            return false;
        }

        if(userService.userExists(username)){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Username already taken.");
           return false;
        }

        return true;
    }

    public boolean passwordChecker(String password){
        if(password.equals("")){
            return false;
        }

        if(password.length() < 4){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Password must be of minimum 4 characters length.");
            return false;
        }

        return true;
    }

    public boolean confirmPasswordChecker(String password, String confirmPassword){
        if(confirmPassword.equals("")){
            return false;
        }

        if(!password.equals(confirmPassword)){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Passwords don't match.");
            return false;
        }

        return true;
    }

    public void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/login-frame.fxml"));
        ApplicationUtils.switchFrame(loader,event,"Login");
    }
}
