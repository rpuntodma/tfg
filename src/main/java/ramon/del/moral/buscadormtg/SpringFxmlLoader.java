package ramon.del.moral.buscadormtg;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

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
}
