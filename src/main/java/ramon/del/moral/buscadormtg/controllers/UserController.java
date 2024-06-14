package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.SpringFxmlLoader;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.facades.UserFacade;

import java.io.IOException;

@Component
public class UserController {

    @Autowired
    SpringFxmlLoader springFxmlLoader;
    @Resource
    private UserFacade userFacade;

    @FXML
    private TextField userNameSignUp;

    @FXML
    private TextField passwordSignUp;

    @FXML
    private void pressSignUp(ActionEvent actionEvent) throws IOException {
        boolean userExist = userFacade.findAll()
                                      .stream()
                                      .anyMatch(user -> userNameSignUp.getText()
                                                                      .compareToIgnoreCase(user.getName()) == 0);

        if (userExist) {
            System.out.println("Usuario ya existe");
        } else {
            UserDto newUser = userFacade.save(UserDto.builder()
                                                     .name(userNameSignUp.getText())
                                                     .password(passwordSignUp.getText())
                                                     .build());

            FXMLLoader fxmlLoader = springFxmlLoader.load("fxml/collections-view.fxml");
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                                  .getWindow();
            CollectionController collectionController = fxmlLoader.getController();

            collectionController.receiveUser(newUser);
            stage.setTitle("Collecciones!");
            stage.setScene(scene);
            stage.show();
        }
    }
}
