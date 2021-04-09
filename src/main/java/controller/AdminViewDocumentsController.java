package controller;

import dto.DocumentDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import service.DocumentService;
import utils.ApplicationUtils;

import java.io.IOException;
import java.util.List;

public class AdminViewDocumentsController {

    private DocumentService documentService;
    private final Alert alert = new Alert((Alert.AlertType.NONE));

    @FXML private TableView<DocumentDto> tableView;
    @FXML private TableColumn<DocumentDto, String> nameColumn;

    @FXML private TextField nameTextField;

    public void initialize(){
        documentService = new DocumentService();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.setItems(getAllDocuments());
        tableView.setEditable(true);

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public ObservableList<DocumentDto> getAllDocuments(){
        ObservableList<DocumentDto> documents = FXCollections.observableArrayList();
        List<DocumentDto> documentDtos = documentService.getAllDocuments();
        documents.addAll(documentDtos);
        return documents;
    }

    public void addNewDocument(){
        DocumentDto documentDto = new DocumentDto();

        if(nameTextField.getText().equals("")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Document name can't be empty.");
            return;
        }

        documentDto.setName(nameTextField.getText());

        documentService.insertDocument(documentDto);
        tableView.setItems(getAllDocuments());

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Document added successfully.");

        nameTextField.clear();
    }

    public void deleteDocument(){
        ObservableList<DocumentDto> document = tableView.getItems();
        DocumentDto documentDto = tableView.getSelectionModel().getSelectedItem();

        if(documentDto == null){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the request first.");
            return;
        }

        document.remove(documentDto);
        documentService.deleteDocument(documentDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Document deleted successfully.");
    }

    public void updateNameCell(TableColumn.CellEditEvent cellEditEvent){
        DocumentDto documentDto = tableView.getSelectionModel().getSelectedItem();
        documentDto.setName(cellEditEvent.getNewValue().toString());
        documentService.updateDocument(documentDto);
    }

    public void viewHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-home-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "Admin Home");
    }

    public void viewUsers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-view-users-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "View Users");
        AdminViewUsersController adminViewUsersController = loader.getController();
        adminViewUsersController.initialize();
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
