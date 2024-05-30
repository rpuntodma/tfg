package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.facades.CardFacade;
import ramon.del.moral.buscadormtg.facades.CollectionFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class CardController {

    @Resource
    private CollectionFacade collectionFacade;
    @Resource
    private CardFacade cardFacade;

    @FXML
    private ComboBox<String> collections;
    @FXML
    private ListView<String> collectionCards;

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> searchResult;

    @FXML
    private Label nameLabel;
    @FXML
    private Label typesLabel;
    @FXML
    private Label manaCostLabel;
    @FXML
    private Label oracleLabel;
    @FXML
    private ImageView imageView;

    private List<CardDto> cards = new ArrayList<>();
    private CollectionDto collectionDto = CollectionDto.builder()
                                                       .name("test")
                                                       .cards(new HashSet<>())
                                                       .build();

    @FXML
    private void searchCards(ActionEvent actionEvent) {
        searchResult.getItems()
                    .clear();
        if (cards != null) {
            cards.clear();
        }

        try {
            cards = cardFacade.searchCardsByName(searchBar.getText());
            searchResult.getItems()
                        .addAll(cards.stream()
                                     .filter(cardDto -> !cardDto.getImageUrl()
                                                                .isEmpty())
                                     .map(CardDto::getName)
                                     .toList());
        } catch (IOException | InterruptedException e) {
            System.out.println("Nada encontrado.");
        }
    }

    @FXML
    private void selectCard(MouseEvent mouseEvent) {
        String nombreCarta = searchResult.getSelectionModel()
                                         .getSelectedItem();
        cards.stream()
             .filter(v -> v.getName()
                           .equals(nombreCarta))
             .findAny()
             .ifPresent(v -> {
                 imageView.setImage(new Image(v.getImageUrl()));
                 nameLabel.setText(v.getName());
                 typesLabel.setText(v.getTypes());
                 manaCostLabel.setText(v.getManaCost());
                 oracleLabel.setText(v.getOracle());
             });
    }

    @FXML
    private void addCard(ActionEvent actionEvent) {
        String nombreCarta = searchResult.getSelectionModel()
                                         .getSelectedItem();
        try {
            collectionDto.getCards()
                         .add(cards.stream()
                                   .filter(v -> v.getName()
                                                 .equals(nombreCarta))
                                   .findAny()
                                   .orElseThrow());
            collectionFacade.save(collectionDto);
        } catch (Exception e) {
            System.out.println("Nada que guardar");
        }
    }
}
