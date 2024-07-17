package com.example.tail_it.ready_products;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ReadyProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> lstdod;

    @FXML
    private ListView<String> lstdress;

    @FXML
    private ListView<String> lstorderid;

    @FXML
    private ComboBox<String> worker;

    @FXML
    void doRecieve(MouseEvent event) {
        if(event.getClickCount()==2) {
            try{
                String order=lstorderid.getSelectionModel().getSelectedItem();
                stmt=con.prepareStatement("update measurements set pstatus=? where orderid=? ");
                stmt.setInt(1,2);
                stmt.setString(2,order);
                stmt.executeUpdate();

                fetchOrders2();
            }
            catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }
    }

    @FXML
    void doRecieveAll(ActionEvent event) {
        try {
            stmt = con.prepareStatement("update measurements set pstatus=? where worker=?");
            stmt.setInt(1, 2);
            String wname=worker.getSelectionModel().getSelectedItem();
            System.out.println(wname);
            stmt.setString(2,wname);
            stmt.executeUpdate();
            fetchOrders2();
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    void fetchOrders2() {
        lstorderid.getItems().clear();
        lstdress.getItems().clear();
        lstdod.getItems().clear();
        try{
            stmt=con.prepareStatement("select * from measurements where worker=? and pstatus=1");
//            stmt.setInt(1,1);
            stmt.setString(1,worker.getSelectionModel().getSelectedItem());

            ResultSet res= stmt.executeQuery();

            while(res.next()){
                lstorderid.getItems().add(res.getString("orderid"));
                lstdress.getItems().add(res.getString("dress"));
                lstdod.getItems().add(String.valueOf(res.getDate("dod")));
            }
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void fetchOrders(ActionEvent event) {
       fetchOrders2();
    }
    PreparedStatement stmt;
    void fetchWorkers(){
        try{
            stmt=con.prepareStatement("select distinct worker from measurements where pstatus=1");
//            stmt.setInt(1,1);

            ResultSet res= stmt.executeQuery();

            while(res.next()){
                worker.getItems().add(res.getString("worker"));
            }
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    Connection con;
    @FXML
    void initialize() {
        con= MySQLConnectionKlass.doConnect();
        if(con==null)
            System.out.println("Connection failed");
        else
            System.out.println("Connection done !!");

        fetchWorkers();

    }

}
