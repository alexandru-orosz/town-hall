package controller;

import dto.RequestDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.RequestService;
import utils.ApplicationUtils;

import java.io.IOException;
import java.util.List;

public class AdminViewUserRequestsController {

    private RequestService requestService;
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TableView<RequestDto> tableView;
    @FXML private TableColumn<RequestDto, String> dateColumn;
    @FXML private TableColumn<RequestDto, String> usernameColumn;
    @FXML private TableColumn<RequestDto, String> documentColumn;
    @FXML private TableColumn<RequestDto, String> residenceColumn;
    @FXML private TableColumn<RequestDto, String> statusColumn;

    @FXML private TextField searchTextField;

    @FXML private ToggleButton togglePending;
    private final String SHOW_ALL_REQUESTS = "Show all requests";
    private final String SHOW_ONLY_PENDING_REQUESTS = "Show only pending requests";

    private String searchText = "";

    public void initialize(){
        requestService = new RequestService();
        togglePending.setText(SHOW_ONLY_PENDING_REQUESTS);

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        documentColumn.setCellValueFactory(new PropertyValueFactory<>("document_name"));
        residenceColumn.setCellValueFactory(new PropertyValueFactory<>("residence_name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(getAllRequests(false, searchText));
        tableView.setEditable(false);
    }

    public ObservableList<RequestDto> getAllRequests(boolean pendingOnly, String searchText){
        ObservableList<RequestDto> requests = FXCollections.observableArrayList();
        List<RequestDto> requestDtos;

        if(pendingOnly){
            requestDtos = requestService.getPendingRequests();
        }
        else{
            requestDtos = requestService.getAllRequests();
        }

        if(searchText.equals("")) {
            //no filter
            for (RequestDto request : requestDtos) {
                request.setDocument_name(request.getDocument().getName());
                request.setResidence_name(request.getResidence().getName());
                request.setUsername(request.getResidence().getUser().getUsername());

                requests.add(request);
            }
        }
        else
        {
            //filter by text
            for(RequestDto request : requestDtos){
                request.setDocument_name(request.getDocument().getName());
                request.setResidence_name(request.getResidence().getName());
                request.setUsername(request.getResidence().getUser().getUsername());

                if(requestService.foundBySearch(request, searchText)){
                    requests.add(request);
                }
            }
        }

        return requests;
    }

    public void searchRequest(){
        String text = searchTextField.getText();

        if(text.equals("")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You need to type something, then press the search button.");
            return;
        }

        searchText = text;

        ObservableList<RequestDto> requests;

        if(togglePending.isSelected()){
            requests = getAllRequests(true, searchText);
        }
        else{
            requests = getAllRequests(false, searchText);

        }
        if(requests.isEmpty()){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "No requests have been found.");
        }

        tableView.setItems(requests);
    }

    public void clearSearch(){
        searchText = "";

        if(togglePending.isSelected()){
            tableView.setItems(getAllRequests(true, searchText));
        }
        else{
            tableView.setItems(getAllRequests(false, searchText));
        }

        searchTextField.clear();
    }

    public void switchViewRequests(){
        if(togglePending.isSelected()){
            tableView.setItems(getAllRequests(true, searchText));
            togglePending.setText(SHOW_ALL_REQUESTS);
        }
        else{
            tableView.setItems(getAllRequests(false, searchText));
            togglePending.setText(SHOW_ONLY_PENDING_REQUESTS);
        }
    }

    public void acceptRequest(){
        RequestDto requestDto = tableView.getSelectionModel().getSelectedItem();

        if(requestDto == null){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the request first.");
            return;
        }

        if(requestDto.getStatus().equals("Residence removed")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You can't accept this request.");
            return;
        }

        requestDto.setStatus("Accepted");
        requestService.updateRequest(requestDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Request accepted.");

        if(togglePending.isSelected()){
            tableView.setItems(getAllRequests(true, searchText));
        }
        else{
            tableView.setItems(getAllRequests(false, searchText));
        }
    }

    public void rejectRequest(){
        RequestDto requestDto = tableView.getSelectionModel().getSelectedItem();

        if(requestDto == null){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must select the request first.");
            return;
        }

        if(requestDto.getStatus().equals("Residence removed")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You can't reject this request.");
            return;
        }

        requestDto.setStatus("Rejected");
        requestService.updateRequest(requestDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Request rejected.");

        if(togglePending.isSelected()){
            tableView.setItems(getAllRequests(true, searchText));
        }
        else{
            tableView.setItems(getAllRequests(false, searchText));
        }
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

    public void viewDocuments(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/admin-view-documents-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "View Documents");
        AdminViewDocumentsController adminViewDocumentsController = loader.getController();
        adminViewDocumentsController.initialize();
    }

    public void doSignout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/login-frame.fxml"));
        ApplicationUtils.newFrame(loader, event, "Login");
    }
}
