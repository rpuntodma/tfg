package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.ProjectJavaFxApp;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.facades.CardFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainController {

    @Resource
    private CardFacade cardFacade;
//    @Resource
//    private CollectionFacade

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> listResult;
    @FXML
    private Label nameLabel;
    @FXML///todo
    private Label tiposLabel;
    @FXML
    private Label manaCostLabel;
    @FXML
    private Label oracleLabel;
    @FXML
    private ImageView imagen;

    private List<CardDto> cards = new ArrayList<>();

    @FXML
    private void search(ActionEvent actionEvent) {
        String datos = searchBar.getText();
        listResult.getItems().clear();

        if (cards != null) {
            cards.clear();
        }

        try {
            cards = cardFacade.searchCardsByName(datos);
            listResult.getItems()
                      .addAll(cards.stream()
                                   .filter(cardDto -> !cardDto.getImageUrl().isEmpty())
                                   .map(CardDto::getName)
                                   .toList());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void selectCard(MouseEvent mouseEvent) {
        String nombreCarta = listResult.getSelectionModel().getSelectedItem();
        cards.stream()
                .filter(v -> v.getName().equals(nombreCarta))
                .findAny().ifPresent(v -> {
                    imagen.setImage(new Image(v.getImageUrl()));
                    nameLabel.setText(v.getName());
                    tiposLabel.setText(v.getTypes());
                    manaCostLabel.setText(v.getManaCost());
                    oracleLabel.setText(v.getOracle());
                });
    }

    @FXML
    private void addCard(ActionEvent actionEvent) {
        String nombreCarta = listResult.getSelectionModel()
                                       .getSelectedItem();
        try {
            cardFacade.save(cards.stream()
                                  .filter(v -> v.getName()
                                                .equals(nombreCarta))
                                  .findAny()
                                  .orElseThrow()
            );
        } catch (Exception e) {
            System.out.println("Nada que guardar");
        }
    }

    @FXML
    private void passSearch(ActionEvent actionEvent) throws IOException {
        String searchText = searchBar.getText();
        if (searchText != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource("fxml/collections-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            CollectionController collcon = fxmlLoader.getController();
            collcon.setWellcome(searchText);
            stage.setTitle("Collecciones!");
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    private void createCollection(ActionEvent actionEvent) {

    }
}
