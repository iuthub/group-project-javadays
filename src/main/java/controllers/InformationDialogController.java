package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import model.Book;
import model.User;

import java.io.IOException;

public class InformationDialogController {
    public Label lblMessage;

    public void setLabel(String text) {
        lblMessage.setText(text);
    }

    public static InformationDialogController getDialog(Class root, Dialog<ButtonType> dialog, Button btnSubmit, String text, String file) {

        dialog.initOwner(btnSubmit.getScene().getWindow());
        dialog.setTitle("Information");
        dialog.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(root.getResource(file));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Button okBtn = (Button) dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(0));
        okBtn.getStyleClass().add("submit-button");

        InformationDialogController controller = fxmlLoader.getController();

        controller.setLabel(text);

        return controller;
    }


}
