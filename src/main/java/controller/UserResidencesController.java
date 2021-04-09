package controller;

import dto.RequestDto;
import dto.ResidenceDto;
import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import service.RequestService;
import service.ResidenceService;
import utils.ApplicationUtils;

import java.io.IOException;
import java.util.List;

public class UserResidencesController {

    private UserDto userDto;
    private ResidenceService residenceService;
    private RequestService requestService;
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TableView<ResidenceDto> tableView;
    @FXML private TableColumn<ResidenceDto, String> nameColumn;
    @FXML private TableColumn<ResidenceDto, String> countryColumn;
    @FXML private TableColumn<ResidenceDto, String> cityColumn;
    @FXML private TableColumn<ResidenceDto, String> zipCodeColumn;
    @FXML private TableColumn<ResidenceDto, String> streetColumn;
    @FXML private TableColumn<ResidenceDto, String> numberColumn;
    @FXML private TableColumn<ResidenceDto, String> apartmentColumn;

    @FXML private TextField nameTextField;
    @FXML private TextField countryTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField zipCodeTextField;
    @FXML private TextField streetTextField;
    @FXML private TextField numberTextField;
    @FXML private TextField apartmentTextField;

    public void initialize(UserDto userDto) {

        this.userDto = userDto;
        residenceService = new ResidenceService();
        requestService = new RequestService();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory((new PropertyValueFactory<>("country")));
        cityColumn.setCellValueFactory((new PropertyValueFactory<>("city")));
        zipCodeColumn.setCellValueFactory((new PropertyValueFactory<>("zip_code")));
        streetColumn.setCellValueFactory((new PropertyValueFactory<>("street")));
        numberColumn.setCellValueFactory((new PropertyValueFactory<>("number")));
        apartmentColumn.setCellValueFactory((new PropertyValueFactory<>("apartment")));

        tableView.setItems(getAllResidences());
        tableView.setEditable(true);

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        countryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        streetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        apartmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public ObservableList<ResidenceDto> getAllResidences(){
        ObservableList<ResidenceDto> residence = FXCollections.observableArrayList();
        List<ResidenceDto> residenceDtos = residenceService.getAllResidences(userDto);
        residence.addAll(residenceDtos);
        return residence;
    }

    public void addNewResidence(){
        ResidenceDto residenceDto = new ResidenceDto();

        if(numberTextField.getText().equals("") || countryTextField.getText().equals("") || cityTextField.getText().equals("") || zipCodeTextField.getText().equals("") || streetTextField.getText().equals("") || numberTextField.getText().equals("")){
            ApplicationUtils.setAlert(alert, Alert.AlertType.ERROR, "You must complete all the fields.");
            return;
        }

        residenceDto.setUser(userDto);
        residenceDto.setName(nameTextField.getText());
        residenceDto.setCountry(countryTextField.getText());
        residenceDto.setCity(cityTextField.getText());
        residenceDto.setZip_code(zipCodeTextField.getText());
        residenceDto.setStreet(streetTextField.getText());
        residenceDto.setNumber(numberTextField.getText());
        residenceDto.setApartment(apartmentTextField.getText());

        if(residenceDto.getApartment().equals("")){
            residenceDto.setApartment("-");
        }

        residenceService.insertResidence(residenceDto);
        tableView.setItems(getAllResidences());

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Residence added successfully.");

        nameTextField.clear();
        countryTextField.clear();
        cityTextField.clear();
        zipCodeTextField.clear();
        streetTextField.clear();
        numberTextField.clear();
        apartmentTextField.clear();
    }

    public void deleteResidence(){
        ObservableList<ResidenceDto> residence = tableView.getItems();
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residence.remove(residenceDto);

        List<RequestDto> requests = requestService.getAllUserRequests(userDto);
        for(RequestDto request : requests){
            if(request.getResidence().equals(residenceDto)){
                request.setStatus("Residence removed");
                requestService.updateRequest(request);
                residenceService.hideResidence(residenceDto);
                ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Residence deleted successfully.");
                return;
            }
        }

        residenceService.deleteResidence(residenceDto);

        ApplicationUtils.setAlert(alert, Alert.AlertType.INFORMATION, "Residence deleted successfully.");
    }

    public void updateNameCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setName(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void updateCountryCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setCountry(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void updateCityCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setCity(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void updateZipCodeCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setZip_code(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void updateStreetCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setStreet(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void updateNumberCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setNumber(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void updateApartmentCell(TableColumn.CellEditEvent cellEditEvent){
        ResidenceDto residenceDto = tableView.getSelectionModel().getSelectedItem();
        residenceDto.setApartment(cellEditEvent.getNewValue().toString());
        residenceService.updateResidence(residenceDto);
    }

    public void viewHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frames/user-home-frame.fxml"));
        ApplicationUtils.switchFrame(loader, event, "User Home");
        UserHomeController userHomeController = loader.getController();
        userHomeController.initialize(userDto);
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
