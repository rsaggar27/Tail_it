package com.example.tail_it.dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.example.tail_it.HelloApplication;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblDelivered;

    @FXML
    private Label lblPlaced;

    @FXML
    private Label lblReceived;

    @FXML
    private LineChart<?, ?> lineChartView;

    @FXML
    private PieChart pieChartView;

    @FXML
    void MeasurementXplorer(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("measurement_explorerr/MeasurementExplorerView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void addCustomers(ActionEvent event) {

    }

    @FXML
    void addWorkers(ActionEvent event) {

    }

    @FXML
    void deliverOrders(ActionEvent event) {

    }

    @FXML
    void receiveOrders(ActionEvent event) {

    }

    @FXML
    void takeOrders(ActionEvent event) {

    }

    @FXML
    void workerDetails(ActionEvent event) {

    }

    PreparedStatement stmt;
    PreparedStatement stmt2;
    ArrayList<String> dresses = new ArrayList<>();
    ArrayList<Integer> count =new ArrayList<>();

    void doFetchPie(){
        try{
            stmt = con.prepareStatement("select distinct dress from measurements");
            ResultSet res = stmt.executeQuery();

            while(res.next()){
                dresses.add(res.getString("dress"));
//                System.out.println(res.getString("dress"));

            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
//        int idx=0;
        for(String dress :dresses) {
            try {
                stmt2 = con.prepareStatement("select count(dress) from measurements where dress=?");

//                System.out.println(dress);

                stmt2.setString(1, dress);
                ResultSet res2 = stmt2.executeQuery();

                if (res2.next()) {
                    count.add(res2.getInt(1));
                }
//                idx+=1;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }

            //create data
            int i=0;
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for(String dress:dresses){
                pieChartData.add(new PieChart.Data(dress,count.get(i)));
                i+=1;
            }

            //create pie chart
            pieChartView.setData(pieChartData);
            pieChartView.setStartAngle(180);
            pieChartView.setClockwise(true);
            pieChartView.setTitle("Dresses");
    }

    void doFetchLine(){

    }



    Connection con;

    @FXML
    void initialize() {
        con= MySQLConnectionKlass.doConnect();
        if(con==null)
            System.out.println("Connection failed");
        else
            System.out.println("Connection done !!");

        doFetchPie();
        doFetchLine();
    }

}
