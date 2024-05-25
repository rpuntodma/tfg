package ramon.del.moral.buscadormtg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Main extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(ProjectSpringBootApp.class);
//        context = builder.run(getParameters().getRaw().toArray(new String[0]));
        context = SpringApplication.run(ProjectSpringBootApp.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Buscador!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
    }
}