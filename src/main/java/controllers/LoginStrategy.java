package controllers;

import javafx.scene.control.Button;

public interface LoginStrategy {
    void authenticate(Button btnSubmit);
}
