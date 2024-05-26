package ramon.del.moral.buscadormtg.controllers;

import jakarta.annotation.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.dtos.CardDto;
import ramon.del.moral.buscadormtg.facades.CardFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MainController {

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
    @Resource
    private CardFacade cardFacade;

    @FXML
    private void addCard(ActionEvent actionEvent) {
        String nombreCarta = listResult.getSelectionModel()
                                       .getSelectedItem();
        cardFacade.save(cards.stream()
                              .filter(v -> v.getName()
                                            .equals(nombreCarta))
                              .findAny()
                              .map(v -> CardDto.builder()
                                               .name(v.getName())
                                               .types(v.getTypes())
                                               .manaCost(v.getManaCost())
                                               .oracle(v.getOracle())
                                               .imageUrl(v.getImageUrl())
                                               .build())
                              .orElseThrow()
        );
    }
}
