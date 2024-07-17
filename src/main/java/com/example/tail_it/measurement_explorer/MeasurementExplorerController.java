package com.example.tail_it.measurement_explorer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Date;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.tail_it.show_worker.WorkerBean;
import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MeasurementExplorerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField mobile;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TableView<MeasureBean> tableView;

    @FXML
    private ComboBox<String> worker;

    ObservableList<MeasureBean> getRecordsMob(){
        ObservableList<MeasureBean>arr= FXCollections.observableArrayList();

        ResultSet res=null;
        try{
           stmt=con.prepareStatement("select * from measurements where mobile=?");
           stmt.setString(1,mobile.getText());


            while(res.next())
            {
                String mobile=res.getString("mobile");
                String dress=res.getString("dress");
                Date doo= res.getDate("doo");
                Date dod= res.getDate("dod");
                String meas=res.getString("measurements");
                String workerr=res.getString("worker");
                int orderid=res.getInt("orderid");

                arr.add(new MeasureBean(orderid,mobile,dress,doo,dod,workerr,meas));
            }
        }
        catch (Exception exp){
            System.out.println(exp.getMessage());
        }
        return arr;
    }

    @FXML
    void doExport(ActionEvent event) {

    }

    ObservableList<MeasureBean> getRecords(){
        ObservableList<MeasureBean>arr= FXCollections.observableArrayList();
        ResultSet res=null;
        int statuss=getStatus(status.getSelectionModel().getSelectedItem());
        if (statuss == 0 && worker.getSelectionModel().getSelectedItem().equals("Any")) {
            try {
                stmt = con.prepareStatement("select * from measurements");
                res = stmt.executeQuery();
            }
            catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }else if(statuss!=0 &&  worker.getSelectionModel().getSelectedItem().equals("Any")){
            try {
                stmt = con.prepareStatement("select * from measurements where pstatus=?");
                stmt.setInt(1,statuss);

                res = stmt.executeQuery();
            }
            catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }
        else if(statuss==0 &&  !worker.getSelectionModel().getSelectedItem().equals("Any")){
            try {
                stmt = con.prepareStatement("select * from measurements where worker=?");
                stmt.setString(1,worker.getSelectionModel().getSelectedItem());
                res = stmt.executeQuery();
            }
            catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }
        else{
            try {
                stmt = con.prepareStatement("select * from measurements where worker=? and pstatus=?");
                stmt.setString(1,worker.getSelectionModel().getSelectedItem());
                stmt.setInt(2,statuss);
                res = stmt.executeQuery();
            }
            catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }

        try{
            while(res.next())
            {
                String mobile=res.getString("mobile");
                String dress=res.getString("dress");
                Date doo= res.getDate("doo");
                Date dod= res.getDate("dod");
                String meas=res.getString("measurements");
                String workerr=res.getString("worker");
                int orderid=res.getInt("orderid");

                arr.add(new MeasureBean(orderid,mobile,dress,doo,dod,workerr,meas));
            }
        }
        catch (Exception exp){
            System.out.println(exp.getMessage());
        }
        return arr;
    }




    @FXML
    void doFetchMob(ActionEvent event) {

    }

    @FXML
    void doFetchSW(ActionEvent event) {
        TableColumn<MeasureBean, String> order_idC=new TableColumn<MeasureBean, String>("Order Id");//kuch bhi
        order_idC.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        order_idC.setMinWidth(70);

        TableColumn<MeasureBean, String> mobileC=new TableColumn<MeasureBean, String>("Mobile");//kuch bhi
        mobileC.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mobileC.setMinWidth(100);

        TableColumn<MeasureBean, String> dressC=new TableColumn<MeasureBean, String>("Dress");//kuch bhi
        dressC.setCellValueFactory(new PropertyValueFactory<>("dress"));
        dressC.setMinWidth(100);

        TableColumn<MeasureBean, Date> dooC=new TableColumn<MeasureBean, Date>("Date of Order");//kuch bhi
        dooC.setCellValueFactory(new PropertyValueFactory<>("doo"));
        dooC.setMinWidth(100);


        TableColumn<MeasureBean, String> workC=new TableColumn<MeasureBean, String>("Worker");//kuch bhi
        workC.setCellValueFactory(new PropertyValueFactory<>("worker"));
        workC.setMinWidth(100);

        TableColumn<MeasureBean, String> dodC=new TableColumn<MeasureBean, String>("Date of Delivery");//kuch bhi
        dodC.setCellValueFactory(new PropertyValueFactory<>("dod"));
        dodC.setMinWidth(100);

        TableColumn<MeasureBean, String> measC=new TableColumn<MeasureBean, String>("Measurements");//kuch bhi
        measC.setCellValueFactory(new PropertyValueFactory<>("measurements"));
        measC.setMinWidth(100);

        tableView.getColumns().addAll(order_idC,mobileC,dressC,dooC,workC,dodC,measC);
        tableView.setItems(null);
        tableView.setItems(getRecords());

    }



    PreparedStatement stmt;

    int getStatus(String s){
        return switch (s) {
            case "Any" -> 0;
            case "In Progress" -> 1;
            case "Received" -> 2;
            case "Delivered"->3;
            default -> 4;
        };

    }

    @FXML
    void fillWorkers(ActionEvent event) {

        int statuss=getStatus(status.getSelectionModel().getSelectedItem());
        ResultSet res = null;
        if(statuss==0){
            try{
                stmt = con.prepareStatement("select distinct worker from measurements");
                res=stmt.executeQuery();
            }catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }
        else{
            try{
                stmt=con.prepareStatement("select distinct worker from measurements where pstatus=?");
                stmt.setInt(1,statuss);
                res= stmt.executeQuery();
            }catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }

        worker.getSelectionModel().clearSelection();
        worker.getItems().clear();
        worker.getItems().add("Any");
        worker.getSelectionModel().selectFirst();
        try{
            while(res.next()){
                worker.getItems().add(res.getString("worker"));
            }
        }catch (Exception exp){
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

        String []stat={"Any","In Progress","Received","Delivered"};
        status.getItems().addAll(stat);
        status.getSelectionModel().selectFirst();
        worker.getItems().add("Any");
        worker.getSelectionModel().selectFirst();

    }

}
