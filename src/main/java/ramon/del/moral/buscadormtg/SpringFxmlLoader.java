package ramon.del.moral.buscadormtg;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import ramon.del.moral.buscadormtg.controllers.CollectionController;
import ramon.del.moral.buscadormtg.dtos.UserDto;

import java.io.IOException;

@Component
public class SpringFxmlLoader {

    private final ConfigurableApplicationContext context;

    public SpringFxmlLoader(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public FXMLLoader load(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProjectJavaFxApp.class.getResource(fxmlPath));
        fxmlLoader.setControllerFactory(context::getBean);
        return fxmlLoader;
    }

    public static void goToCollections(FXMLLoader loader, Stage stage, UserDto userDto) throws IOException {
        Scene scene = new Scene(loader.load());

        CollectionController collectionController = loader.getController();
        collectionController.receiveUser(userDto);

        stage.setTitle("Manage Collections");
        stage.setScene(scene);
        stage.show();
    }

    public static void logOut(FXMLLoader loader, Stage stage) throws IOException {
        Scene scene = new Scene(loader.load());

        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
}
