package ramon.del.moral.buscadormtg;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ramon.del.moral.buscadormtg")
public class ProjectSpringBootApp {

    public static void main(String[] args) {
        Application.launch(ProjectJavaFxApp.class, args);
    }
}
