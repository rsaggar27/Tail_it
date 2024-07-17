package com.example.tail_it.customer_enrollment;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.example.tail_it.sql_connection.MySQLConnectionKlass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
public class customerEnrollmentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblMsg;

    @FXML
    private TextField address;

    @FXML
    private ComboBox<String> city;

    @FXML
    private TextField cname;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField mobile;

    @FXML
    private ComboBox<String> state;

    @FXML
    void doClear(ActionEvent event) {
        mobile.setText("");
        cname.setText("");
        address.setText("");
        email.setText("");
        gender.getSelectionModel().select(0);
        city.getSelectionModel().select(0);
        state.getSelectionModel().select(0);
        dob.setValue(null);
        lblMsg.setText("");

    }

    PreparedStatement stmt;
    @FXML
    void doEnroll(ActionEvent event) {
        try {
            stmt = con.prepareStatement("insert into customers values(?,?,?,?,?,?,?,?,?)");
            stmt.setString(1,mobile.getText());
            stmt.setString(2,cname.getText());
            stmt.setString(3,gender.getSelectionModel().getSelectedItem());
            stmt.setString(4,address.getText());
            stmt.setString(5,city.getSelectionModel().getSelectedItem());
            stmt.setString(6,state.getSelectionModel().getSelectedItem());

            LocalDate local=dob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(7, date);

            LocalDate noww=LocalDate.now();
            java.sql.Date now=java.sql.Date.valueOf(noww);
            stmt.setDate(8,now);

            stmt.setString(9,email.getText());

            stmt.executeUpdate();
            System.out.println("Record Saved!!");
            lblMsg.setText("Customer Enrolled :)");
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void doFetch(ActionEvent event) {
        try{
            stmt=con.prepareStatement("select * from customers where mobile=?");
            stmt.setString(1,mobile.getText());

            ResultSet res= stmt.executeQuery();

            while(res.next()){
                String cnamee=res.getString("cname");
                String emaill=res.getString("email");
                String genderr=res.getString("gender");
                String addresss=res.getString("address");
                String cityy=res.getString("city");
                String statee=res.getString("state");
                Date dobb=res.getDate("dob");

                cname.setText(cnamee);
                email.setText(emaill);
                address.setText(addresss);
                gender.getEditor().setText(genderr);
                city.getEditor().setText(cityy);
                state.getEditor().setText(statee);
                dob.setValue(dobb.toLocalDate());
            }
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        try {
            stmt=con.prepareStatement("update customers set cname=?, email=?, gender=?, dob=? ,address=?,city=?,state=? where  mobile=?");

            stmt.setString(8,mobile.getText());
            stmt.setString(1,cname.getText());
            stmt.setString(3,gender.getSelectionModel().getSelectedItem());
            stmt.setString(5,address.getText());
            stmt.setString(6,city.getSelectionModel().getSelectedItem());
            stmt.setString(7,state.getSelectionModel().getSelectedItem());

            LocalDate local=dob.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(4, date);

            stmt.setString(2,email.getText());

            stmt.executeUpdate();
            System.out.println("Record Updated!!");
            lblMsg.setText("Record Updated!!");

        }
        catch(Exception exp)
        {
            System.out.println(exp.getMessage());
        }


    }

    Connection con;
    @FXML
    void initialize() {
        String gen[]={"Select","Male","Female","Other"};
        gender.getItems().addAll(gen);
        gender.getSelectionModel().select(0);

        String[] states = {"Select",
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
                "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
                "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal"
        };
        state.getItems().addAll(states);
        state.getSelectionModel().select(0);

        String[] cities = {"Select",
                "Agartala", "Agra", "Ahmedabad", "Ahmednagar", "Aizawl",
                "Ajmer", "Akola", "Aligarh", "Allahabad", "Alwar",
                "Ambarnath", "Ambattur", "Amravati", "Amritsar", "Anantapur",
                "Arrah", "Asansol", "Aurangabad", "Avadi", "Bangalore",
                "Bardhaman", "Bareilly", "Bathinda", "Begusarai", "Belgaum",
                "Bellary", "Bharatpur", "Bhatpara", "Bhavnagar", "Bhilai",
                "Bhilwara", "Bhiwandi", "Bhopal", "Bhubaneswar", "Bhagalpur",
                "Bhiwani", "Bhusawal", "Bidar", "Bijapur", "Bikaner",
                "Bilaspur", "Bokaro", "Brahmapur", "Buxar", "Chandigarh",
                "Chandrapur", "Chennai", "Chhapra", "Coimbatore", "Cuttack",
                "Darbhanga", "Davanagere", "Dehradun", "Dewas", "Dhanbad",
                "Dharwad", "Dhule", "Dindigul", "Durg", "Durgapur",
                "Eluru", "Erode", "Etawah", "Faridabad", "Farrukhabad",
                "Firozabad", "Gaya", "Ghaziabad", "Gorakhpur", "Gopalpur",
                "Gulbarga", "Guntur", "Gurgaon", "Guwahati", "Gwalior",
                "Hapur", "Haridwar", "Hisar", "Hosur", "Hubli–Dharwad",
                "Hyderabad", "Ichalkaranji", "Imphal", "Indore", "Jabalpur",
                "Jaipur", "Jalandhar", "Jalgaon", "Jalna", "Jammu",
                "Jamnagar", "Jamshedpur", "Jhansi", "Jodhpur", "Junagadh",
                "Kadapa", "Kadiri", "Kakinada", "Kalyan-Dombivli", "Kamarhati",
                "Kanchipuram", "Kanpur", "Karnal", "Karur", "Kharagpur",
                "Khammam", "Kochi", "Kolhapur", "Kolkata", "Kollam",
                "Korba", "Kota", "Kozhikode", "Kulti", "Kurnool",
                "Latur", "Loni", "Lucknow", "Ludhiana", "Madurai",
                "Malegaon", "Mangalore", "Mathura", "Meerut", "Moradabad",
                "Muzaffarnagar", "Muzaffarpur", "Mysore", "Nagpur", "Nanded",
                "Nashik", "Navi Mumbai", "Nellore", "New Delhi", "Nizamabad",
                "Noida", "North Dumdum", "Ongole", "Panipat", "Panihati",
                "Patiala", "Patna", "Pimpri-Chinchwad", "Pondicherry", "Puducherry",
                "Pune", "Purnia", "Raipur", "Rajahmundry", "Rajkot",
                "Rajpur", "Sonarpur", "Ranchi", "Ratlam", "Rewa", "Rohtak",
                "Rourkela", "Sagar", "Saharanpur", "Salem", "Sambalpur",
                "Sangli-Miraj & Kupwad", "Satara", "Satna", "Shimoga", "Siliguri",
                "Solapur", "Sonipat", "South Dumdum", "Srinagar", "Surat",
                "Thane", "Thiruvananthapuram", "Thrissur", "Tiruchirappalli", "Tirunelveli",
                "Tiruppur", "Tumkur", "Udaipur", "Ujjain", "Ulhasnagar",
                "Vadodara", "Varanasi", "Vasai-Virar", "Vijayawada", "Visakhapatnam",
                "Warangal", "Yamunanagar"
        };
        city.getItems().addAll(cities);
        city.getSelectionModel().select(0);


        con= MySQLConnectionKlass.doConnect();
        if(con==null)
            System.out.println("Connection failed");
        else
            System.out.println("Connection done !!");
    }

}
