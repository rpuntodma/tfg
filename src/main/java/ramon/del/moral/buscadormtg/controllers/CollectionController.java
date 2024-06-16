package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

import java.io.IOException;
import java.util.HashSet;

@Component
public class CollectionController {

    @Resource
    private SpringFxmlLoader springFxmlLoader;

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
    private void createCollection() {

        if (nameTextField.getText()
                         .isEmpty()) {
            System.out.println("Cannot create a collection without a name");

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

            collectionDtoNew = collectionFacade.save(collectionDtoNew);
            collectionsList.getItems().add(collectionDtoNew);
        }

    }

    @FXML
    private void deleteSelected() {
        CollectionDto selectedCollection = collectionsList.getSelectionModel().getSelectedItem();
        collectionsList.getItems().remove(selectedCollection);
        collectionFacade.deleteById(selectedCollection.getId());
    }

    @FXML
    private void editSelected(ActionEvent actionEvent) throws IOException {
        CollectionDto selectedCollection = collectionsList.getSelectionModel().getSelectedItem();

        goToCards(actionEvent, selectedCollection);
    }

    public void receiveUser(UserDto newUser) {
        user = newUser;
        userLabel.setText("Bienvenido " + user.getName());

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
