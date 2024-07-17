package com.example.tail_it.show_worker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ShowWorkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> splz;

    @FXML
    private TableView<WorkerBean> tableView;

    @FXML
    void doExport(ActionEvent event) {

    }

    PreparedStatement stmt;

    ObservableList<WorkerBean> getRecords(){
        ObservableList<WorkerBean>arr= FXCollections.observableArrayList();
        try{
            if(splz.getSelectionModel().getSelectedItem()=="Any"){
                stmt = con.prepareStatement("select * from workers");
            }
            else {
                stmt = con.prepareStatement("select * from workers where splz like ?");
                stmt.setString(1, "%" + splz.getSelectionModel().getSelectedItem() + "%");
            }
            ResultSet res=stmt.executeQuery();

            while(res.next()){
                String wname=res.getString("cname");
                String mobile=res.getString("mobile");
                String gender=res.getString("gender");
                String splz=res.getString("splz");
                Date doe=res.getDate("doe");

                arr.add(new WorkerBean(wname,mobile,gender,doe,splz));
                System.out.println(wname+" "+mobile+" "+gender+" "+splz+" "+doe.toString());
            }
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }

        return arr;
    }


    @FXML
    void doFetch(ActionEvent event) {
//        getRecords();

        TableColumn<WorkerBean, String> nameC=new TableColumn<WorkerBean, String>("Name");//kuch bhi
        nameC.setCellValueFactory(new PropertyValueFactory<>("wname"));
        nameC.setMinWidth(100);

        TableColumn<WorkerBean, String> genC=new TableColumn<WorkerBean, String>("Gender");//kuch bhi
        genC.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genC.setMinWidth(100);

        TableColumn<WorkerBean, String> mobC=new TableColumn<WorkerBean, String>("Mobile");//kuch bhi
        mobC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobC.setMinWidth(100);

        TableColumn<WorkerBean, String> doeC=new TableColumn<WorkerBean, String>("DOE");//kuch bhi
        doeC.setCellValueFactory(new PropertyValueFactory<>("doe"));
        doeC.setMinWidth(100);

        TableColumn<WorkerBean, String> splzC=new TableColumn<WorkerBean, String>("Specializations");//kuch bhi
        splzC.setCellValueFactory(new PropertyValueFactory<>("splz"));
        splzC.setMinWidth(200);

        tableView.getColumns().addAll(nameC,genC,mobC,doeC,splzC);
        tableView.setItems(null);
        tableView.setItems(getRecords());

    }

    @FXML
    void fillWorkers(MouseEvent event) {

    }
Connection con;
    @FXML
    void initialize() {

        String[] dresses = { "Any",
                "A-Line", "Ball Gown", "Blazer", "Bodycon", "Bohemian",
                "Chinos", "Cocktail", "Dress Shirt", "Empire Waist", "Evening Gown",
                "Flapper", "Halter", "High-Low", "Jacket", "Jeans",
                "Jumpsuit", "Kaftan", "Kimono", "Kurta", "Maxi",
                "Mermaid", "Midi", "Mini", "Necktie", "Peplum",
                "Pinafore", "Polo", "Puffy Sleeve", "Sari", "Sheath",
                "Shift", "Shirt", "Shorts", "Skater", "Slip",
                "Smock", "Suit", "Sundress", "Sweater", "T-Shirt",
                "Tent", "Trousers", "Tunic", "Vest", "Wrap"
        };

        splz.getItems().addAll(dresses);
        splz.getSelectionModel().select(0);

      con= MySQLConnectionKlass.doConnect();
        if(con==null)
            System.out.println("Connection Failed!!");
        else
            System.out.println("Connection Done :)");

    }

}
