package controller;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import utils.ApplicationUtils;

import java.io.IOException;
public class UserHomeController {

    private UserDto userDto;

    @FXML private Label welcomeLabel;

    public void initialize(UserDto userDto) {

        this.userDto = userDto;
        welcomeLabel.setText("Welcome, " + userDto.getUsername() + "!");
    }

    public void yourResidences(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/user-residences-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "Your Residences");
        UserResidencesController userResidencesController = loader.getController();
        userResidencesController.initialize(userDto);
    }

    public void yourRequests(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/user-requests-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "Your Requests");
        UserRequestsController userRequestsController = loader.getController();
        userRequestsController.initialize(userDto);
    }

    public void doSignout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/login-frame.fxml"));
        ApplicationUtils.newFrame(loader, event, "Login");
    }
}
