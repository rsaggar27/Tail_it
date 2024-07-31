package com.example.tail_it.measurement;
import com.example.tail_it.mailer.MailController;
import com.example.tail_it.sql_connection.MySQLConnectionKlass;
//import com.example.tail_it.customer_enrollmentt.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MeasurementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField advpaid;

    @FXML
    private TextField bill;

    @FXML
    private Label lblMsg;

    @FXML
    private Button despic;

    @FXML
    private DatePicker dod;

    @FXML
    private ComboBox<String> dress;

    @FXML
    private ImageView imgPrev;

    @FXML
    private TextArea measurements;

    @FXML
    private TextField mobile;

    @FXML
    private TextField pprice;

    @FXML
    private ComboBox<String> quantity;

    @FXML
    private ComboBox<String> worker;

    @FXML
    public Button closeButton;

    @FXML
    void fillWorkers(MouseEvent event) {
        try {
            worker.getItems().clear();
            stmt = con.prepareStatement("select cname from workers where splz like ?");
            stmt.setString(1, "%"+dress.getSelectionModel().getSelectedItem()+"%");
            ResultSet res= stmt.executeQuery();

            while(res.next()){
                worker.getItems().add(res.getString("cname"));
            }
        }
        catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void doClose(ActionEvent event) {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void doFetch(ActionEvent event) {
        int o_id=getOrderId();
        order_id=o_id;

        try {
            stmt = con.prepareStatement("select * from measurements where orderid=?");
            stmt.setInt(1,o_id);

            ResultSet res= stmt.executeQuery();
            while(res.next()) {
                mobile.setText(res.getString("mobile"));
                dress.getSelectionModel().select(res.getString("dress"));
                dod.setValue(res.getDate("dod").toLocalDate());
                quantity.getSelectionModel().select(res.getInt("quantity"));
                pprice.setText(res.getString("pprice"));
                bill.setText(res.getString("bill"));
                advpaid.setText(res.getString("advpaid"));
                worker.getSelectionModel().select(res.getString("worker"));
                measurements.setText(res.getString("measurements"));
                imgPrev.setImage(new Image(new FileInputStream(res.getString("despic"))));

            }

        }catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }

    @FXML
    void doNew(ActionEvent event) {
        mobile.setText("");
        dress.getSelectionModel().select(0);
        dod.setValue(null);
        quantity.getSelectionModel().select(0);
        pprice.setText("");
        bill.setText("");
        advpaid.setText("");
        worker.getSelectionModel().select(0);
        imgPrev.setImage(null);
        measurements.setText("");

    }

PreparedStatement stmt;
    PreparedStatement stmt2;
int order_id;
    //create table measurements(orderid int primary key auto_increment,mobile varchar(30),dress varchar(100),dod date, quantity int,bill int, advpaid int, worker varchar(100),despic varchar(200), measurements varchar(400));
    @FXML
    void doSave(ActionEvent event) {
        try {
            stmt = con.prepareStatement("insert into measurements(mobile, dress, dod, quantity, bill, advpaid, worker, despic, measurements,pprice,doo,pstatus) values(?,?,?,?,?,?,?,?,?,?,?,1)");
//            stmt.setInt(1,1);
            stmt.setString(1,mobile.getText());
            stmt.setString(2,dress.getSelectionModel().getSelectedItem());

            LocalDate local=dod.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(3, date);

            stmt.setInt(4,Integer.parseInt(quantity.getSelectionModel().getSelectedItem()));
            stmt.setInt(5,Integer.parseInt(bill.getText()));
            stmt.setInt(6,Integer.parseInt(advpaid.getText()));
            stmt.setString(7,worker.getSelectionModel().getSelectedItem());
            stmt.setString(8,filePath);
            stmt.setString(9, measurements.getText());
            stmt.setString(10,pprice.getText());

            LocalDate noww=LocalDate.now();
            java.sql.Date now=java.sql.Date.valueOf(noww);
            stmt.setDate(11,now);

            stmt.executeUpdate();

            stmt=con.prepareStatement("select LAST_INSERT_ID()");
            ResultSet res= stmt.executeQuery();

            stmt2=con.prepareStatement("select * from customers where mobile=?");
            stmt2.setString(1,mobile.getText());

            ResultSet res2=stmt2.executeQuery();

            String em="";
            String cname="";
            String msg="";
            String oid="0";

            if(res.next()) {
                oid=res.getString(1);
                System.out.println(res.getString(1));
                showMyMsg("Your order id is "+res.getString(1));
            }


            while(res2.next()){
                em = res2.getString("email");
                System.out.println(em);
                cname = res2.getString("cname");
            }

            msg="Your order id is "+oid+"\nYour Order will be ready by +"+date+"\nThank you for your order!";

            MailController.sendMail(em,cname,"Your order id is here.",msg);

            System.out.println("Done!!");
            lblMsg.setText("Record taken !!");

        }
        catch(Exception exp){
            exp.printStackTrace();
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        try{
            stmt = con.prepareStatement("update measurements set mobile=?, dress=?, dod=?, quantity=?, bill=?, advpaid=?, worker=?, despic=?, measurements=?,pprice=?,doo=? where orderid=?");
//            stmt.setInt(1,1);
            stmt.setString(1,mobile.getText());
            stmt.setString(2,dress.getSelectionModel().getSelectedItem());

            LocalDate local=dod.getValue();
            java.sql.Date date=java.sql.Date.valueOf(local);
            stmt.setDate(3, date);

            stmt.setInt(4,Integer.parseInt(quantity.getSelectionModel().getSelectedItem()));
            stmt.setInt(5,Integer.parseInt(bill.getText()));
            stmt.setInt(6,Integer.parseInt(advpaid.getText()));
            stmt.setString(7,worker.getSelectionModel().getSelectedItem());
            stmt.setString(8,filePath);
            stmt.setString(9, measurements.getText());
            stmt.setString(10,pprice.getText());

            LocalDate noww=LocalDate.now();
            java.sql.Date now=java.sql.Date.valueOf(noww);
            stmt.setDate(11,now);
            stmt.setInt(12,order_id);
            stmt.executeUpdate();

            System.out.println("Done!!");
            lblMsg.setText("Record updated !!");
        }catch(Exception exp){
            System.out.println(exp.getMessage());
        }
    }
    String filePath="nopic";
    @FXML
    void doUpload(ActionEvent event) {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Select Profile Pic:");

        //filters to choose a img in right format only
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("*.*", "*.*")
        );

        File file=chooser.showOpenDialog(null);
        filePath=file.getAbsolutePath();

        try{
            imgPrev.setImage( new Image(new FileInputStream(filePath)));
        }catch(FileNotFoundException exp){
            System.out.println(exp.getMessage());
        }
    }

    int getOrderId(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Update");
        dialog.setContentText("Please enter the order id:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent())
        {
            if(result.get().equals(""))
                showMyMsg("Fill Value");
            else
            {
                return Integer.parseInt(result.get());

            }
        }
        else
            showMyMsg("No Discount");

        return -1;
    }


    void showMyMsg(String msg)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);


        alert.setTitle("Order Id");

        alert.setHeaderText("Order Id");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    Connection con;

    @FXML
    void initialize() {
        String []qty={"Select","1","2","3","4","5","6","7","8","9","10"};
        quantity.getItems().addAll(qty);
        quantity.getSelectionModel().select(0);

        String[] dresses = { "Select",
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

        dress.getItems().addAll(dresses);
        dress.getSelectionModel().select(0);

       con= MySQLConnectionKlass.doConnect();
       if(con==null)
           System.out.println("Connection Failed!!");
       else
           System.out.println("Connection Done :)");

    }

}
