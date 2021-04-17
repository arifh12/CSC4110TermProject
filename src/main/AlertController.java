package sample;

import javafx.scene.control.Alert;

public class AlertController extends Alert {
    public AlertController(AlertType alertType,String title,String content) {
        super(alertType);
        setTitle(title);
        setHeaderText(null);
        setContentText(content);
        showAndWait();
    }

}
