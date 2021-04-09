package controller;

import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import service.UserService;
import utils.ApplicationUtils;

import java.io.IOException;
import java.util.List;

public class AdminViewUsersController {

    private UserService userService;

    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TableView<UserDto> tableView;
    @FXML private TableColumn<UserDto, String> usernameColumn;
    @FXML private TableColumn<UserDto, String> emailColumn;
    @FXML private TableColumn<UserDto, String> typeColumn;

    public void initialize(){
        userService = new UserService();

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableView.setItems(getAllUsers());
        tableView.setEditable(true);

        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public ObservableList<UserDto> getAllUsers(){
        ObservableList<UserDto> users = FXCollections.observableArrayList();
        List<UserDto> userDtos = userService.getAllUsers();
        users.addAll(userDtos);
        return users;
    }

    public void updateTypeCell(TableColumn.CellEditEvent cellEditEvent){
        UserDto userDto = tableView.getSelectionModel().getSelectedItem();

        String newType = cellEditEvent.getNewValue().toString();

        if(newType.equals("admin") || newType.equals("user")){
            userDto.setType(newType);
            userService.updateUser(userDto);
        }
        else {
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Type can be only \"user\" or \"admin\".");
            tableView.setItems(getAllUsers());
        }
    }

    public void viewHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-home-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "Admin Home");
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
