module com.projectgain.projectgain {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.projectgain.controller to javafx.fxml;
    exports com.projectgain.controller;
    exports com.projectgain;
    opens com.projectgain to javafx.fxml;
}