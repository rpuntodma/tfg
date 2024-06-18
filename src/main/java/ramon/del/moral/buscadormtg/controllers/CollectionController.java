package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.SpringFxmlLoader;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.dtos.UserDto;
import ramon.del.moral.buscadormtg.facades.CollectionFacade;
import ramon.del.moral.buscadormtg.facades.UserFacade;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@Component
public class CollectionController {

    @Resource
    private SpringFxmlLoader springFxmlLoader;
    @Resource
    private UserFacade userFacade;
    @Resource
    private CollectionFacade collectionFacade;

    private UserDto user;

    @FXML
    private ListView<CollectionDto> collectionsList;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label userLabel;
    @FXML
    private Label errorMessage;

    public void receiveUser(UserDto newUser) {
        user = newUser;
        userLabel.setText("Wellcome " + user.getName());

        collectionsList.setCellFactory(collectionDtoListView -> new ListCell<>() {
            @Override
            protected void updateItem(CollectionDto collection, boolean empty) {
                super.updateItem(collection, empty);
                if (empty || collection == null) {
                    setText(null);
                } else {
                    setText(collection.getName());
                }
            }
        });
        collectionsList.getItems()
                       .addAll(collectionFacade.findAll()
                                               .stream()
                                               .filter(coll -> coll.getUser()
                                                                   .getId()
                                                                   .equals(user.getId()))
                                               .toList());
    }

    @FXML
    private void logOut(ActionEvent actionEvent) throws IOException {
        errorMessage.setText("");

        FXMLLoader fxmlLoader = springFxmlLoader.load("fxml/user-login-view.fxml");

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                              .getWindow();
        SpringFxmlLoader.logOut(fxmlLoader, stage);
    }

    @FXML
    private void deleteAccount(ActionEvent actionEvent) throws IOException {
        errorMessage.setText("");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete user confirmation");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("By clicking yes, you will remove your account and all your collections.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes()
             .setAll(buttonTypeYes, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            userFacade.deleteById(user.getId());
            logOut(actionEvent);
        } else {
            errorMessage.setText("Delete account cancelled");
        }
    }

    @FXML
    private void createCollection() {
        errorMessage.setText("");

        if (nameTextField.getText()
                         .isEmpty()) {
            errorMessage.setText("Collection name cannot be empty");

        } else {
            CollectionDto collectionDtoNew;
            collectionDtoNew = collectionFacade.findAll()
                                               .stream()
                                               .filter(collectionDto -> collectionDto.getUser()
                                                                                     .getId()
                                                                                     .equals(user.getId()))
                                               .filter(collectionDto -> collectionDto.getName()
                                                                                     .compareToIgnoreCase(nameTextField.getText()) == 0)
                                               .findAny()
                                               .orElse(CollectionDto.builder()
                                                                    .name(nameTextField.getText())
                                                                    .cards(new HashSet<>())
                                                                    .user(user)
                                                                    .build());
            if (collectionDtoNew.getId() == null) {
                collectionDtoNew = collectionFacade.save(collectionDtoNew);
                collectionsList.getItems()
                               .add(collectionDtoNew);
            } else {
                errorMessage.setText("Collection name already exists");
            }
        }
    }

    @FXML
    private void deleteSelected() {
        errorMessage.setText("");

        try {
            CollectionDto selectedCollection = collectionsList.getSelectionModel()
                                                              .getSelectedItem();
            collectionsList.getItems()
                           .remove(selectedCollection);
            collectionFacade.deleteById(selectedCollection.getId());
        } catch (NullPointerException e) {
            errorMessage.setText("Nothing selected to delete");
        }
    }

    @FXML
    private void editSelected(ActionEvent actionEvent) throws IOException {
        errorMessage.setText("");

        try {
            CollectionDto selectedCollection = collectionsList.getSelectionModel()
                                                              .getSelectedItem();

            goToCards(actionEvent, selectedCollection);
        } catch (NullPointerException e) {
            errorMessage.setText("Nothing selected to edit");
        }
    }

    private void goToCards(ActionEvent actionEvent, CollectionDto collectionDto) throws IOException {
        FXMLLoader fxmlLoader = springFxmlLoader.load("fxml/cards-view.fxml");
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                              .getWindow();
        CardController cardController = fxmlLoader.getController();

        cardController.receiveCollection(collectionDto);
        stage.setTitle("Edit Collection");
        stage.setScene(scene);
        stage.show();
    }
}