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
public class jinhuoController {
    @FXML private Button exitButton;
    private ObservableList<jinhuo> jinhuoData = FXCollections.observableArrayList();
    @FXML private TableView<jinhuo> jinhuoTable;
    @FXML private TableColumn<jinhuo, String> wareNameColumn;
    @FXML private TableColumn<jinhuo, String> wareNumberColumn;
    @FXML private TableColumn<jinhuo, String> amountColumn;
    @FXML private TableColumn<jinhuo, String> timeColumn;
    @FXML private TableColumn<jinhuo, String> factoryColumn;
    @FXML private TableColumn<jinhuo, String> storageColumn;
    @FXML
    public void initialize() {
        System.out.println("about to initialize.");
        wareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        wareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeProperty()));
        factoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().factoryProperty()));
        storageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().storageProperty()));
        showjinhuo();
    }

    void showjinhuo()
    {

        System.out.print("ready to show jinhuo information");
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
            pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM import WHERE approved = 0");
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
            String factory = null;
            String storage = null;
            while (rs.next()) {
                wareName = rs.getString("name").trim();
                wareNumber = rs.getString("number").trim();
                amount = rs.getString("amount").trim();
                time = rs.getString("date").trim();
                time = time.substring(0,19);
                factory = rs.getString("factory").trim();
                storage = rs.getString("place").trim();
                System.out.println(wareName + wareNumber + amount + time + factory + storage);
                jinhuoData.add(new jinhuo(wareName, wareNumber, amount, time, factory, storage));
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
        jinhuoTable.setItems(jinhuoData);
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
    @FXML public void on_delete_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要删除吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            jinhuo jh = jinhuoTable.getSelectionModel().getSelectedItem();
            String wareNumber = jh.wareNumberProperty();
            String time = jh.timeProperty();
            linkMySql con = new linkMySql();
            Connection myCon = con.caigouconnect();
            if(myCon == null)
            {
                JOptionPane.showMessageDialog(null, "无法连接到数据库！");
                return;
            }
            /*fetch data*/
            Statement stmt = null;
            try{
                stmt = myCon.createStatement();
            } catch(SQLException SqlEX) {
                SqlEX.printStackTrace();
            }
            System.out.println("Preparing table data\n");
            System.out.println(time);
            String str = String.format("DELETE FROM import WHERE approved = 0 AND date = \"%s\" AND number = \"%s\"", time, wareNumber);
            System.out.println(str);
            try {
                Stage tempStage = (Stage) exitButton.getScene().getWindow();
                tempStage.close();
                stmt.execute(str);
                JOptionPane.showMessageDialog(null, "删除成功！");
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("jinhuo.fxml")
                );
                AnchorPane root = new AnchorPane();
                Scene myScene = new Scene(root);
                try {
                    myScene.setRoot((Parent)loader.load());
                    Stage newStage = new Stage();
                    newStage.setTitle("进货单");
                    newStage.setScene(myScene);
                    newStage.show();
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    @FXML public void on_update_clicked()
    {

    }
}

