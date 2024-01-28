module com.projectgain.projectgain {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires freetts;

    opens com.projectgain.controllers to javafx.fxml;
    opens com.projectgain.models;
    exports com.projectgain.controllers;
    exports com.projectgain;
    opens com.projectgain to javafx.fxml;
}