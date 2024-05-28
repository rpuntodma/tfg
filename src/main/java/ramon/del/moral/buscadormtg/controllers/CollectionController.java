package ramon.del.moral.buscadormtg.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class CollectionController {

    @FXML
    private Label search;

    public void setWellcome(String searchText) {
        search.setText(searchText);
    }
}
