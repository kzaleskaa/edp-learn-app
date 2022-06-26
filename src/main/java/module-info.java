module com.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires gson;
    requires spring.security.crypto;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires java.net.http;
    requires json.simple;
    requires com.google.guice;
    requires javafx.media;
    requires com.google.common;
    requires commons.email;

    opens com.finalproject to javafx.fxml, javafx.graphics;
    exports com.finalproject;
    exports com.finalproject.Controllers;
    opens com.finalproject.Controllers to javafx.fxml;
    opens com.finalproject.Models to gson, org.hibernate.orm.core;
    exports com.finalproject.helpers to com.google.guice;
    exports com.finalproject.Repository to com.google.guice;
    exports com.finalproject.Models;
    exports com.finalproject.CustomComponents to javafx.graphics;
    exports com.finalproject.Models.API;
    opens com.finalproject.Models.API to gson, org.hibernate.orm.core;
    exports com.finalproject.Models.POJO;
    opens com.finalproject.Models.POJO to gson, org.hibernate.orm.core;
    exports com.finalproject.CustomEvents to com.google.common;
}
