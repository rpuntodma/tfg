package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.SpringFxmlLoader;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.dtos.CollectionDto;
import ramon.del.moral.buscadormtg.facades.CardFacade;
import ramon.del.moral.buscadormtg.facades.CollectionFacade;

import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class CardController {

    @Resource
    private SpringFxmlLoader springFxmlLoader;
    @Resource
    private CollectionFacade collectionFacade;
    @Resource
    private CardFacade cardFacade;

    @FXML
    private Label wellcomeLabel;

    @FXML
    private ComboBox<CollectionDto> collectionsComboBox;
    @FXML
    private ListView<CardDto> collectionCards;

    @FXML
    private TextField searchBar;
    @FXML
    private ListView<CardDto> searchResult;

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

    @FXML
    private Label errorMessage;

    private CollectionDto collectionDto;

    public void receiveCollection(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;
        wellcomeLabel.setText("Wellcome " + collectionDto.getUser()
                                                         .getName());

        collectionsComboBox.getItems()
                           .addAll(collectionFacade.findAll()
                                                   .stream()
                                                   .filter(coll -> coll.getUser()
                                                                       .getId()
                                                                       .equals(collectionDto.getUser()
                                                                                            .getId()))
                                                   .toList());

        collectionsComboBox.setValue(collectionDto);

        collectionCards.getItems()
                       .addAll(collectionDto.getCards());
    }

    @FXML
    private void initialize() {
        collectionsComboBox.setCellFactory(collectionDtoListView -> new ListCell<>() {
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

        collectionsComboBox.setButtonCell(new ListCell<>() {
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

        collectionCards.getSelectionModel()
                       .selectedItemProperty()
                       .addListener((observable, oldValue, newValue) -> {
                           if (newValue != null) {
                               selectCard(newValue);
                               searchResult.getSelectionModel()
                                           .clearSelection();
                           }
                       });

        searchResult.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            selectCard(newValue);
                            collectionCards.getSelectionModel()
                                           .clearSelection();
                        }
                    });
    }

    @FXML
    private void goToCollections(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                              .getWindow();
        SpringFxmlLoader.goToCollections(springFxmlLoader.load("fxml/collections-view.fxml"), stage, collectionDto.getUser());
    }

    @FXML
    private void logOut(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = springFxmlLoader.load("fxml/user-login-view.fxml");

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene()
                                                              .getWindow();
        SpringFxmlLoader.logOut(fxmlLoader, stage);
    }

    @FXML
    private void selectCollection() {
        collectionDto = collectionsComboBox.getSelectionModel()
                                           .getSelectedItem();
        collectionCards.getItems()
                       .clear();
        collectionCards.getItems()
                       .addAll(cardFacade.findAllByCollection(collectionDto.getId()));
    }

    @FXML
    private void deleteCardFromCollection() {
        CardDto selectedCard = collectionCards.getSelectionModel()
                                              .getSelectedItem();
        System.out.println(selectedCard.getId());
        try {
            collectionDto.getCards()
                         .remove(selectedCard);
            collectionDto = collectionFacade.save(collectionDto);
            if (collectionDto.getCards()
                             .size() != collectionCards.getItems()
                                                       .size()) {
                collectionCards.getItems()
                               .clear();
                collectionCards.getItems()
                               .addAll(collectionDto.getCards());
            }
        } catch (NoSuchElementException e) {
            errorMessage.setText("Nothing selected to delete");
        }
    }

    @FXML
    private void searchCards() {
        searchResult.getItems()
                    .clear();

        try {
            searchResult.getItems()
                        .addAll(cardFacade.searchCardsByName(searchBar.getText())
                                          .stream()
                                          .filter(cardDto -> !cardDto.getImageUrl()
                                                                     .isEmpty())
                                          .toList());
        } catch (IOException | InterruptedException e) {
            errorMessage.setText("Nothing found");
        }
    }

    @FXML
    private void addCard() {
        CardDto selectedCard = searchResult.getSelectionModel()
                                           .getSelectedItem();
        try {
            collectionDto.getCards()
                         .add(selectedCard);
            collectionDto = collectionFacade.save(collectionDto);
            if (collectionDto.getCards()
                             .size() != collectionCards.getItems()
                                                       .size()) {
                collectionCards.getItems()
                               .add(collectionDto.getCards()
                                                 .stream()
                                                 .filter(card -> card.equals(selectedCard))
                                                 .findAny()
                                                 .orElseThrow());
            } else {
                errorMessage.setText("Nothing added to collection");
            }
        } catch (NoSuchElementException e) {
            errorMessage.setText("Nothing selected to add");
        }
    }

    private void selectCard(CardDto selectedCard) {
        imageView.setImage(new Image(selectedCard.getImageUrl()));
        nameLabel.setText(selectedCard.getName());
        typesLabel.setText(selectedCard.getTypes());
        manaCostLabel.setText(selectedCard.getManaCost());
        oracleLabel.setText(selectedCard.getOracle());
    }
}