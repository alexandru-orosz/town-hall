package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import utils.ApplicationUtils;

import java.io.IOException;

public class AdminHomeController {

    public void viewUsers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-view-users-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "View Users");
        AdminViewUsersController adminViewUsersController = loader.getController();
        adminViewUsersController.initialize();
    }

    public void viewDocuments(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-view-documents-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "View Documents");
        AdminViewDocumentsController adminViewDocumentsController = loader.getController();
        adminViewDocumentsController.initialize();
    }

    public void viewUserRequests(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-view-user-requests-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "View User Requests");
        AdminViewUserRequestsController adminViewUserRequestsController = loader.getController();
        adminViewUserRequestsController.initialize();
    }

    public void doSignout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/login-frame.fxml"));
        ApplicationUtils.newFrame(loader, event, "Login");
    }
}
