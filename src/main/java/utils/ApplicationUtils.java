package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ApplicationUtils {

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static String formattedDate(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(dateTimeFormatter);
    }

    public static LocalDateTime dateTime(String formattedDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(formattedDate, dateTimeFormatter);
    }

    public static void setAlert(Alert alert, Alert.AlertType alertType, String message){
        alert.setAlertType(alertType);
        alert.setHeaderText(message);
        alert.show();
    }

    public static void closeOldWindow(ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

    public static void switchFrame(FXMLLoader loader, ActionEvent event, String title) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void newFrame(FXMLLoader loader, ActionEvent event, String title) throws IOException{
        Parent root = loader.load();
        Stage stage=new Stage();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

        closeOldWindow(event);
    }
}

