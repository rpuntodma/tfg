module ramon.del.moral.buscadormtg {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.orm;
    requires spring.data.jpa;
    requires java.sql;
    requires spring.beans;
    requires spring.core;
    requires org.json;
    requires jakarta.persistence;
    requires static lombok;
    requires jakarta.annotation;
    requires org.hibernate.orm.core;

    opens ramon.del.moral.buscadormtg to spring.core, spring.beans, spring.context, javafx.fxml, org.json;
    opens ramon.del.moral.buscadormtg.entities to spring.core, org.hibernate.orm.core;
    opens ramon.del.moral.buscadormtg.services.impl to spring.core, org.hibernate.orm.core;
    exports ramon.del.moral.buscadormtg;
    exports ramon.del.moral.buscadormtg.daos;
    exports ramon.del.moral.buscadormtg.dtos;
    exports ramon.del.moral.buscadormtg.services;
    exports ramon.del.moral.buscadormtg.services.impl;
    exports ramon.del.moral.buscadormtg.entities;
}