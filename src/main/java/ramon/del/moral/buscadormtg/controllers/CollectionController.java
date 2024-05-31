package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.ProjectJavaFxApp;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.facades.CollectionFacade;

import java.io.IOException;

@Component
public class CollectionController {

    @Resource
    private CollectionFacade collectionFacade;

    @FXML
    private ListView<CollectionDto> collectionsList;
    @FXML
    private TextField nameTextField;

    @FXML
    private void initialize() {
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
        collectionsList.getItems().addAll(collectionFacade.findAll());
    }

    @FXML
    private void createCollection(ActionEvent actionEvent) throws IOException {
        if (nameTextField.getText().isEmpty()) {
            System.out.println("vacio");
        } else {
            CollectionDto collectionDtoNew = CollectionDto.builder().name(nameTextField.getText()).build();
            collectionDtoNew = collectionFacade.save(collectionDtoNew);

            FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource("fxml/collections-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                                  .getWindow();
            CardController cardController = fxmlLoader.getController();

            cardController.
            stage.setTitle("Collecciones!");
            stage.setScene(scene);
            stage.show();
        }

    }

//    @FXML
//    private void onEdit(ActionEvent actionEvent) {
//        CollectionDto collectionDto = (Button)actionEvent.getSource().
//        if (searchText != null) {
//            FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource("fxml/collections-view.fxml"));
//            Scene scene = new Scene(fxmlLoader.load());
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
//                                                                  .getWindow();
//            CollectionController collcon = fxmlLoader.getController();
//            collcon.setWellcome(searchText);
//            stage.setTitle("Collecciones!");
//            stage.setScene(scene);
//            stage.show();
//        }
//    }
}
