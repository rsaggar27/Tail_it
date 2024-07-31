package com.example.tail_it.dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javafx.scene.chart.*;

import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.example.tail_it.HelloApplication;
import javafx.stage.StageStyle;

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
    private BarChart<String, Integer> barChartView;


    @FXML
    private PieChart pieChartView;

    @FXML
    void MeasurementXplorer(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("measurement_explorerr/MeasurementExplorerView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Measurement Xplorer");
            stage.setScene(scene);
            stage.setResizable(false);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();


        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void addCustomers(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customer_enrollmentt/customerEnrollmentView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Customer Enrollment");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void addWorkers(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("worker_enrollmentt/WorkerEnrollmentView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Worker Enrollment");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void deliverOrders(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("order_deliveryy/OrderDeliveryView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Deliver Orders");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void receiveOrders(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ready_productss/ReadyProductsView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Receive Orders");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void takeOrders(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("measurementt/MeasurementView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Take Orders!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void workerDetails(ActionEvent event) {
        try{
            Stage stage=new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("show_workerr/ShowWorkerView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Worker Details");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
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

    Map<String, Integer>  workerCountMap = new HashMap<>();
    PreparedStatement stmt3;
    void doFetchBar(){
        try{
            stmt3=con.prepareStatement("select worker, count(worker) as count_worker from measurements group by worker");
            ResultSet res=stmt3.executeQuery();

            while(res.next()){
                String worker = res.getString("worker");
                int count = res.getInt("count_worker");
                System.out.println(worker +" "+ count);
                workerCountMap.put(worker, count);
            }

        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }

        //creating the data
        XYChart.Series<String,Integer> data= new XYChart.Series<>();
        data.setName("Worker Data");

        for (Map.Entry<String, Integer> entry : workerCountMap.entrySet()){
            data.getData().add(new XYChart.Data<String,Integer>(entry.getKey(),entry.getValue()));
        }

        barChartView.getData().add(data);

    }

    void doFetchOrder1(){
        try{
            stmt = con.prepareStatement("select count(pstatus) as count_plc from measurements where pstatus=1");
            ResultSet res=stmt.executeQuery();


            if(res.next()){
//                System.out.println(res.getString("count_plc"));
                lblPlaced.setText(res.getString("count_plc"));
            }
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    void doFetchOrder2(){
        try{
            stmt = con.prepareStatement("select count(pstatus) as count_plc from measurements where pstatus=2");
            ResultSet res=stmt.executeQuery();


            if(res.next()){
//                System.out.println(res.getString("count_plc"));
                lblReceived.setText(res.getString("count_plc"));
            }
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    void doFetchOrder3(){
        try{
            stmt = con.prepareStatement("select count(pstatus) as count_plc from measurements where pstatus=3");
            ResultSet res=stmt.executeQuery();


            if(res.next()){
//                System.out.println(res.getString("count_plc"));
                lblDelivered.setText(res.getString("count_plc"));
            }
        }catch(Exception exp){
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

        doFetchPie();
        doFetchBar();
        doFetchOrder3();
        doFetchOrder1();
        doFetchOrder2();
    }

}
