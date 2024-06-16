package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private TextField passwordSignUp;
    @FXML
    private Label errorSignUpLabel;

    @FXML
    private TextField userNameSignIn;
    @FXML
    private TextField passwordSignIn;
    @FXML
    private Label errorSignInLabel;

    @FXML
    private void pressSignUp(ActionEvent actionEvent) throws IOException {
        if (userExists(userNameSignUp.getText())) {
            errorSignUpLabel.setText("User name already exists");

        } else if (passwordSignUp.getText().isBlank()) {
            errorSignUpLabel.setText("Password cannot be blank");

        } else {
            UserDto newUser = userFacade.save(UserDto.builder()
                                                     .name(userNameSignUp.getText())
                                                     .password(passwordSignUp.getText())
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
            if (userDto.getPassword()
                       .compareToIgnoreCase(passwordSignIn.getText()) != 0) {
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
        FXMLLoader fxmlLoader = springFxmlLoader.load("fxml/collections-view.fxml");
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                              .getWindow();
        CollectionController collectionController = fxmlLoader.getController();

        collectionController.receiveUser(userDto);
        stage.setTitle("Collecciones!");
        stage.setScene(scene);
        stage.show();
    }

    private boolean userExists(String userName) {
        return userFacade.findAll()
                         .stream()
                         .anyMatch(user -> userName.compareToIgnoreCase(user.getName()) == 0);
    }
}
