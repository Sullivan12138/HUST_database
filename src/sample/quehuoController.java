package sample;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class quehuoController {
    @FXML private Button exitButton;
    private ObservableList<quehuo> quehuoData = FXCollections.observableArrayList();
    @FXML private TableView<quehuo> quehuoTable;
    @FXML private TableColumn<quehuo, String> wareNameColumn;
    @FXML private TableColumn<quehuo, String> wareNumberColumn;
    @FXML private TableColumn<quehuo, String> amountColumn;
    @FXML private TableColumn<quehuo, String> timeColumn;
    @FXML private TableColumn<quehuo, String> storageColumn;
    @FXML
    public void initialize() {
        System.out.println("about to initialize.");
        wareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        wareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeProperty()));
        storageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().storageProperty()));
        showquehuo();
    }

    void showquehuo()
    {

        System.out.print("ready to show quehuo information");
        /*set up connection */
        linkMySql con = new linkMySql();
        Connection myCon = con.caigouconnect();
        if(myCon == null)
        {
            JOptionPane.showMessageDialog(null, "无法连接到数据库！");
            return;
        }
        /*fetch data*/
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        System.out.println("Preparing table data\n");
        try {
            pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM lack");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            System.out.println("selected successfully");
            String wareName = null;
            String wareNumber = null;
            String amount = null;
            String time = null;
            String storage = null;
            while (rs.next()) {
                wareName = rs.getString("name").trim();
                wareNumber = rs.getString("number").trim();
                amount = rs.getString("amount").trim();
                time = rs.getString("time").trim();
                storage = rs.getString("place").trim();
                System.out.println(wareName + wareNumber + amount + time + storage);
                quehuoData.add(new quehuo(wareName, wareNumber, amount, time, storage));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        /*close the connection*/
        try
        {
            myCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        quehuoTable.setItems(quehuoData);
    }
    @FXML
    public void on_exit_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要退出吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            Stage tempStage = (Stage) exitButton.getScene().getWindow();
            tempStage.close();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("caigou.fxml")
            );
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            try {
                myScene.setRoot((Parent) loader.load());
                Stage newStage = new Stage();
                newStage.setTitle("采购人员操作界面");
                newStage.setScene(myScene);
                newStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else return;
    }
}
