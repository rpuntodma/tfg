package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.SpringFxmlLoader;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.facades.UserFacade;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;

@Component
public class UserController {

    @Resource
    SpringFxmlLoader springFxmlLoader;
    @Resource
    private UserFacade userFacade;

    @FXML
    private TextField userNameSignUp;
    @FXML
    private PasswordField passwordSignUp;
    @FXML
    private Label errorSignUpLabel;
    @FXML
    private ImageView eyeSignUpImage;

    @FXML
    private TextField userNameSignIn;
    @FXML
    private PasswordField passwordSignIn;
    @FXML
    private Label errorSignInLabel;
    @FXML
    private ImageView eyeSignInImage;

    @FXML
    public void initialize() {
        TextField visiblePasswordSignIn = new TextField();
        initPasswordFields(passwordSignIn, visiblePasswordSignIn, eyeSignInImage);

        TextField visiblePasswordSignUp = new TextField();
        initPasswordFields(passwordSignUp, visiblePasswordSignUp, eyeSignUpImage);
    }

    @FXML
    private void pressSignUp(ActionEvent actionEvent) throws IOException {
        if (userExists(userNameSignUp.getText())) {
            errorSignUpLabel.setText("User name already exists");

        } else if (userNameSignUp.getText()
                                 .isBlank()) {
            errorSignUpLabel.setText("Username cannot be blank");


        } else if (passwordSignUp.getText()
                                 .isBlank()) {
            errorSignUpLabel.setText("Password cannot be blank");

        } else {
            UserDto newUser = userFacade.save(UserDto.builder()
                                                     .name(userNameSignUp.getText())
                                                     .password(BCrypt.hashpw(passwordSignUp.getText(), BCrypt.gensalt()))
                                                     .build());

            goToCollections(actionEvent, newUser);
        }
    }

    @FXML
    private void pressSignIn(ActionEvent actionEvent) throws IOException {

        try {
            UserDto userDto = userFacade.findAll()
                                        .stream()
                                        .filter(user -> userNameSignIn.getText()
                                                                      .compareToIgnoreCase(user.getName()) == 0)
                                        .findAny()
                                        .orElseThrow();
            if (!BCrypt.checkpw(passwordSignIn.getText(), userDto.getPassword())) {
                throw new InvalidKeyException();
            }

            goToCollections(actionEvent, userDto);

        } catch (NoSuchElementException e) {
            errorSignInLabel.setText("User not found");
        } catch (InvalidKeyException e) {
            errorSignInLabel.setText("Invalid password");
        }
    }

    private void goToCollections(ActionEvent actionEvent, UserDto userDto) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                              .getWindow();
        SpringFxmlLoader.goToCollections(springFxmlLoader.load("fxml/collections-view.fxml"), stage, userDto);
    }

    private boolean userExists(String userName) {
        return userFacade.findAll()
                         .stream()
                         .anyMatch(user -> userName.compareToIgnoreCase(user.getName()) == 0);
    }

    private void initPasswordFields(PasswordField passwordField, TextField visiblePasswordField, ImageView eyeIcon) {

        visiblePasswordField.setManaged(false);
        visiblePasswordField.setVisible(false);
        HBox.setHgrow(visiblePasswordField, Priority.ALWAYS);

        eyeIcon.setOnMouseEntered(event -> {
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setManaged(true);
            visiblePasswordField.setVisible(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        });

        eyeIcon.setOnMouseExited(event -> {
            passwordField.setText(visiblePasswordField.getText());
            visiblePasswordField.setManaged(false);
            visiblePasswordField.setVisible(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
        });

        HBox hBox = (HBox) passwordField.getParent();
        hBox.getChildren()
            .add(visiblePasswordField);
    }
}