module ramon.del.moral.buscadormtg {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.data.jpa;
    requires java.sql;
    requires spring.beans;
    requires spring.core;
    requires org.json;
    requires jakarta.persistence;
    requires static lombok;

    opens ramon.del.moral.buscadormtg to spring.core, spring.beans, spring.context, javafx.fxml, org.json;
    opens ramon.del.moral.buscadormtg.entities to org.hibernate.orm.core;
    exports ramon.del.moral.buscadormtg;
    exports ramon.del.moral.buscadormtg.entities;
}