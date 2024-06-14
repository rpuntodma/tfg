package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
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
    private ComboBox<CollectionDto> collections;
    @FXML
    private ListView<CardDto> collectionCards;

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
    private CollectionDto collectionDto;

    @FXML
    private void initialize() {

        collections.setCellFactory(collectionDtoListView -> new ListCell<>() {
            @Override
            protected void updateItem(CollectionDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                }
            }
        });

        collections.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CollectionDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                }
            }
        });

        collections.getItems()
                   .addAll(collectionFacade.findAll());
        if (!collections.getItems()
                        .isEmpty()) {
            collections.getSelectionModel()
                       .select(0);
        }

        collectionDto = collections.getItems()
                                   .isEmpty() ?
                CollectionDto.builder()
                             .name("New Collection")
                             .cards(new HashSet<>())
                             .build()
                : collections.getSelectionModel()
                             .getSelectedItem();

        collectionCards.setCellFactory(cardDtoListView -> new ListCell<>() {
            @Override
            protected void updateItem(CardDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

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
             .filter(cardDto -> cardDto.getName()
                                       .equals(nombreCarta))
             .findAny()
             .ifPresent(cardDto -> {
                 imageView.setImage(new Image(cardDto.getImageUrl()));
                 nameLabel.setText(cardDto.getName());
                 typesLabel.setText(cardDto.getTypes());
                 manaCostLabel.setText(cardDto.getManaCost());
                 oracleLabel.setText(cardDto.getOracle());
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
            collectionDto = collectionFacade.save(collectionDto);
            if (collectionDto.getCards()
                             .size() != collectionCards.getItems()
                                                       .size()) {
                collectionCards.getItems()
                               .add(cards.stream()
                                         .filter(v -> v.getName()
                                                       .equals(nombreCarta))
                                         .findAny()
                                         .orElseThrow());
            }
        } catch (Exception e) {
            System.out.println("Nada que guardar");
        }
    }

    public void setSelectedCollection(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;
        this.collections.setValue(collectionDto);
    }
}
