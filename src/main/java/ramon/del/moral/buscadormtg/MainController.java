package ramon.del.moral.buscadormtg;

import jakarta.annotation.Resource;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.daos.CardDao;
import ramon.del.moral.buscadormtg.entities.CardModel;
import ramon.del.moral.buscadormtg.services.CardService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    private List<Carta> cartas = new ArrayList<>();
    @FXML
    private void search(ActionEvent actionEvent) {
        String datos = searchBar.getText();
        listResult.getItems().clear();
        if (cartas != null) {
            cartas.clear();
        }
        JSONObject jsonInfo;
        int page = 0;
        try {
            do {
                page++;
                String info = ApiServices.search(datos + "&page=" + page);
                jsonInfo = new JSONObject(info);
                JSONArray datosCarta = jsonInfo.getJSONArray("data");
                cartas.addAll(datosCarta.toList().stream()
                        .map(v -> (Map<?, ?>) v)
                        .filter(v -> v.get("image_uris") != null)
                        .map(v -> new Carta(
                                (String) v.get("name"),
                                (String) v.get("type_line"),
                                (String) v.get("mana_cost"),
                                (String) v.get("oracle_text"),//Acordarme de que sara me dijo que nuullpointer puede darse aqui.
                                (String) ((Map<?, ?>) v.get("image_uris")).get("normal")
                        )).toList()
                );
                listResult.getItems().addAll(cartas.stream().map(Carta::getName).toList());
            } while (jsonInfo.getBoolean("has_more"));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void selectCard(MouseEvent mouseEvent) {
        String nombreCarta = listResult.getSelectionModel().getSelectedItem();
        cartas.stream()
                .filter(v -> v.getName().equals(nombreCarta))
                .findAny().ifPresent(v -> {
                    imagen.setImage(new Image(v.getImagen()));
                    nameLabel.setText(v.getName());
                    tiposLabel.setText(v.getTipos());
                    manaCostLabel.setText(v.getManaCost());
                    oracleLabel.setText(v.getOracle());
                });
    }
    @Resource
    private CardService cardService;

    @FXML
    private void addCard(ActionEvent actionEvent) {
        cardService.save(CardModel.builder()
                              .name("Carta de Prueba")
                              .build());
    }
}
