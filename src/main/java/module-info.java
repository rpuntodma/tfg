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
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires spring.data.commons;

    opens ramon.del.moral.buscadormtg to spring.core, spring.beans, spring.context, javafx.fxml, org.json;
    opens ramon.del.moral.buscadormtg.controllers to javafx.fxml, org.json, spring.beans, spring.context, spring.core;
    opens ramon.del.moral.buscadormtg.entities to spring.core, org.hibernate.orm.core;
    opens ramon.del.moral.buscadormtg.services.impl to spring.core, org.hibernate.orm.core;
    opens ramon.del.moral.buscadormtg.converters to spring.core;
    opens ramon.del.moral.buscadormtg.facades.impl to spring.core;
    exports ramon.del.moral.buscadormtg;
    exports ramon.del.moral.buscadormtg.daos;
    exports ramon.del.moral.buscadormtg.dtos;
    exports ramon.del.moral.buscadormtg.services;
    exports ramon.del.moral.buscadormtg.services.impl;
    exports ramon.del.moral.buscadormtg.entities;
    exports ramon.del.moral.buscadormtg.converters;
    exports ramon.del.moral.buscadormtg.facades;
    exports ramon.del.moral.buscadormtg.facades.impl;
    exports ramon.del.moral.buscadormtg.controllers;
}