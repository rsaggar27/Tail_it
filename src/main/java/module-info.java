module com.example.tail_it {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires jdk.jdi;
    requires java.mail;
    requires activation;

    opens com.example.tail_it to javafx.fxml;
    exports com.example.tail_it;

    exports com.example.tail_it.customer_enrollment to javafx.fxml;
    opens com.example.tail_it.customer_enrollment;

    exports com.example.tail_it.measurement to javafx.fxml;
    opens com.example.tail_it.measurement;

    exports com.example.tail_it.measurement_explorer to javafx.fxml;
    opens com.example.tail_it.measurement_explorer;

    exports com.example.tail_it.worker_enrollment to javafx.fxml;
    opens com.example.tail_it.worker_enrollment;

    exports com.example.tail_it.ready_products to javafx.fxml;
    opens com.example.tail_it.ready_products;

    exports com.example.tail_it.sql_connection to javafx.fxml;
    opens com.example.tail_it.sql_connection;

    exports com.example.tail_it.show_worker to javafx.fxml;
    opens com.example.tail_it.show_worker;

    exports com.example.tail_it.order_delivery to javafx.fxml;
    opens com.example.tail_it.order_delivery;

    exports com.example.tail_it.dashboard to javafx.fxml;
    opens com.example.tail_it.dashboard;


}