package controller;

import dto.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import service.DocumentService;
import service.RequestService;
import service.ResidenceService;
import utils.ApplicationUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class UserRequestsController {

    private UserDto userDto;
    private RequestService requestService;
    private DocumentService documentService;
    private ResidenceService residenceService;
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TableView<RequestDto> tableView;
    @FXML private TableColumn<RequestDto, String> dateColumn;
    @FXML private TableColumn<RequestDto, String> documentColumn;
    @FXML private TableColumn<RequestDto, String> residenceColumn;
    @FXML private TableColumn<RequestDto, String> statusColumn;

    @FXML private MenuButton documentMenuButton;
    @FXML private MenuButton residenceMenuButton;

    private final String PENDING_STATUS = "Pending";
    private final String TOWN_HALL_CITY = "Cluj-Napoca";

    public void initialize(UserDto userDto) {
        this.userDto = userDto;
        requestService = new RequestService();
        documentService = new DocumentService();
        residenceService = new ResidenceService();

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        documentColumn.setCellValueFactory(new PropertyValueFactory<>("document_name"));
        residenceColumn.setCellValueFactory(new PropertyValueFactory<>("residence_name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(getAllUserRequests());
        tableView.setEditable(false);

        menuButtonEvent();
    }

    public ObservableList<RequestDto> getAllUserRequests(){
        ObservableList<RequestDto> requests = FXCollections.observableArrayList();
        List<RequestDto> requestDtos = requestService.getAllUserRequests(userDto);

        for(RequestDto request : requestDtos) {
            request.setDocument_name(request.getDocument().getName());
            request.setResidence_name(request.getResidence().getName());
            requests.add(request);
        }

        return requests;
    }

    public void menuButtonEvent(){
        //document button
        EventHandler<ActionEvent> pickDocument = e -> { documentMenuButton.setText(((MenuItem)e.getSource()).getText()); };

        List<DocumentDto> documentDtos = documentService.getAllDocuments();
        for(DocumentDto document : documentDtos){
            MenuItem menuItem = new MenuItem(document.getName());
            menuItem.setOnAction(pickDocument);
            documentMenuButton.getItems().add(menuItem);
        }

        //residence button
        EventHandler<ActionEvent> pickResidence = e -> { residenceMenuButton.setText(((MenuItem)e.getSource()).getText());};

        List<ResidenceDto> residenceDtos = residenceService.getAllResidences(userDto);
        for(ResidenceDto residence : residenceDtos){
            MenuItem menuItem = new MenuItem(residence.getName());
            menuItem.setOnAction(pickResidence);
            residenceMenuButton.getItems().add(menuItem);
        }
    }

    public boolean menuButtonNotSelected(){
        if(documentMenuButton.getText().equals("Document")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the document.");
            return true;
        }

        if(residenceMenuButton.getText().equals("Residence")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the residence.");
            return true;
        }

        return false;
    }


    public void addNewRequest(){
        RequestDto requestDto = new RequestDto();

        if(menuButtonNotSelected()){
            return;
        }

        requestDto.setDate(ApplicationUtils.formattedDate(LocalDateTime.now()));
        requestDto.setDocument(documentService.getDocumentByName(documentMenuButton.getText()));
        requestDto.setResidence(residenceService.getResidenceByNameAndUser(residenceMenuButton.getText(), userDto));
        requestDto.setStatus(PENDING_STATUS);

        if (checkResidence(requestDto)) return;

        requestService.insertRequest(requestDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Request sent successfully.");

        documentMenuButton.setText("Document");
        residenceMenuButton.setText("Residence");

        tableView.setItems(getAllUserRequests());
    }

    public void selectRequest(){
        RequestDto requestDto = tableView.getSelectionModel().getSelectedItem();
        documentMenuButton.setText(requestDto.getDocument_name());
        residenceMenuButton.setText(requestDto.getResidence_name());
    }

    public void updateRequest(){
        RequestDto requestDto = tableView.getSelectionModel().getSelectedItem();

        if(requestDto == null){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the request first.");
            return;
        }

        if(!requestDto.getStatus().equals(PENDING_STATUS)){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You can't update this request.");
            return;
        }

        requestDto.setDocument(documentService.getDocumentByName(documentMenuButton.getText()));
        requestDto.setResidence(residenceService.getResidenceByNameAndUser(residenceMenuButton.getText(), userDto));

        if (checkResidence(requestDto)) return;

        requestService.updateRequest(requestDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Request updated successfully.");

        documentMenuButton.setText("Document");
        residenceMenuButton.setText("Residence");

        tableView.setItems(getAllUserRequests());
    }

    private boolean checkResidence(RequestDto requestDto) {
        if(!requestDto.getResidence().getCity().equals(TOWN_HALL_CITY)){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "The residence should be located in " + TOWN_HALL_CITY + ".");
            return true;
        }

        if(requestService.getResidenceRequestsThisYear(requestDto.getResidence()) == 3) {
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You can make only 3 requests per residence this year.");
            return true;
        }
        return false;
    }

    public void deleteRequest(){
        ObservableList<RequestDto> requests = tableView.getItems();
        RequestDto requestDto = tableView.getSelectionModel().getSelectedItem();

        if(requestDto == null){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the request first.");
            return;
        }

        if(!requestDto.getStatus().equals(PENDING_STATUS)){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You can't delete this request.");
            return;
        }

        requests.remove(requestDto);
        requestService.deleteRequest(requestDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "Request deleted successfully.");
    }

    public void viewHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/user-home-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "User Home");
        UserHomeController userHomeController = loader.getController();
        userHomeController.initialize(userDto);
    }

    public void yourResidences(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/user-residences-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "Your Residences");
        UserResidencesController userResidencesController = loader.getController();
        userResidencesController.initialize(userDto);
    }

    public void doSignout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/login-frame.fxml"));
        ApplicationUtils.newFrame(loader, event, "Login");
    }
}
