package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LibrarianStatusDialogViewController
{
    //region <Declarations>
    @FXML
    public Label messageLabel;
    @FXML
    public Button okButton;

    private String message;
    //endregion

    //region <StartUp>
    public void initialize()
    {
        messageLabel.setText(message);
    }
    //endregion

    //region <Utilities>
    public void setMessage(String message)
    {
        //this.message = message;
        messageLabel.setText(message);
    }
    //endregion

    //region <Event handlers>
    @FXML
    private void handleOk()
    {
        ((Stage) okButton.getScene().getWindow()).close();
    }
    //endregion
}
