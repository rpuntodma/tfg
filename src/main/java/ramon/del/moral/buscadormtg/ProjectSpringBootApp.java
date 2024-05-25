package ramon.del.moral.buscadormtg;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "ramon.del.moral.buscadormtg")
@ImportResource("classpath:spring/spring-context.xml")
public class ProjectSpringBootApp {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
