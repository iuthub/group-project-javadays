package controllers;

import javafx.scene.control.Button;

public class LoginContext {
    private LoginStrategy loginStrategy;

    public void executeStrategy(Button btnSubmit) {
        loginStrategy.authenticate(btnSubmit);
    }

    public void setLoginStrategy(LoginStrategy loginStrategy) {
        this.loginStrategy = loginStrategy;
    }
}
