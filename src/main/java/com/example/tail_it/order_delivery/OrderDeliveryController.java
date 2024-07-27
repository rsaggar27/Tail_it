package com.example.tail_it.order_delivery;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class OrderDeliveryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblAdv;

    @FXML
    private Label lblBill;

    @FXML
    private Label lblTotBill;

    @FXML
    private ListView<Integer> lstBill;

    @FXML
    private ListView<String> lstDress;

    @FXML
    private ListView<String> lstOrderId;

    @FXML
    private ListView<String> lstStatus;

    @FXML
    private TextField mobile;
PreparedStatement stmt;
    @FXML
    void doDeliver(ActionEvent event) {
        try{
            stmt = con.prepareStatement("update measurements set pstatus=? where mobile=?");
            stmt.setString(2,mobile.getText());
            stmt.setInt(1,3);

            stmt.executeUpdate();

            doFetchh();
            doBill();
        }catch(Exception exp){
            exp.printStackTrace();
        }
        doBill();
    }

    void doFetchh(){
        try{
            lstBill.getItems().clear();
            lstOrderId.getItems().clear();
            lstStatus.getItems().clear();
            lstDress.getItems().clear();

            stmt = con.prepareStatement("select * from measurements where mobile=?");
            stmt.setString(1,mobile.getText());

            ResultSet res= stmt.executeQuery();

            while(res.next()){
                lstDress.getItems().add(res.getString("dress"));
                lstOrderId.getItems().add(res.getString("orderid"));
                lstStatus.getItems().add(res.getString("pstatus"));
                lstBill.getItems().add(res.getInt("bill"));
            }
        }catch(Exception exp){
            exp.printStackTrace();
        }
        doBill();
    }

    void doBill(){
        try{
            int totBill=0;
            int advPaid=0;
//            int bill=0;
            stmt = con.prepareStatement("select * from measurements where mobile=? and pstatus=?");
            stmt.setString(1,mobile.getText());
            stmt.setInt(2,2);

            ResultSet res= stmt.executeQuery();

            while(res.next()){
                 int tot=res.getInt("bill");
                 int adv=res.getInt("advpaid");

                 totBill=totBill+tot;
                 advPaid=advPaid+adv;
            }
            lblTotBill.setText(String.valueOf(totBill));
            lblAdv.setText(String.valueOf(advPaid));
            lblBill.setText(String.valueOf(totBill-advPaid));
        }catch(Exception exp){
            exp.printStackTrace();
        }
    }



    @FXML
    void doFetch(ActionEvent event) {
        doFetchh();
    }

Connection con;
    @FXML
    void initialize() {
        con= MySQLConnectionKlass.doConnect();
        if(con==null)
            System.out.println("Connection failed");
        else
            System.out.println("Connection done !!");
    }

}
