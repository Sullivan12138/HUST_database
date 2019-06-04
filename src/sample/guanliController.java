package sample;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JOptionPane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;

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
public class guanliController{
    @FXML private Button exitButton;
    @FXML private Button commitButton;
    @FXML private Button commitButton2;
    private ObservableList<jinhuo2> jinhuo2Data = FXCollections.observableArrayList();
    @FXML private TableView<jinhuo2> jinhuo2Table;
    @FXML private TableColumn<jinhuo2, String> jinhuo2wareNameColumn;
    @FXML private TableColumn<jinhuo2, String> jinhuo2wareNumberColumn;
    @FXML private TableColumn<jinhuo2, String> jinhuo2amountColumn;
    @FXML private TableColumn<jinhuo2, String> jinhuo2timeColumn;
    @FXML private TableColumn<jinhuo2, String> jinhuo2factoryColumn;
    @FXML private TableColumn<jinhuo2, String> jinhuo2storageColumn;
    @FXML private TableColumn<jinhuo2, CheckBox> jinhuo2approvedColumn;


    private ObservableList<chuhuo2> chuhuo2Data = FXCollections.observableArrayList();
    @FXML private TableView<chuhuo2> chuhuo2Table;
    @FXML private TableColumn<chuhuo2, String> chuhuo2wareNameColumn;
    @FXML private TableColumn<chuhuo2, String> chuhuo2wareNumberColumn;
    @FXML private TableColumn<chuhuo2, String> chuhuo2amountColumn;
    @FXML private TableColumn<chuhuo2, String> chuhuo2timeColumn;
    @FXML private TableColumn<chuhuo2, CheckBox> chuhuo2approvedColumn;
    @FXML private TableColumn<chuhuo2, String> chuhuo2storageColumn;


    private ObservableList<quehuo> quehuoData = FXCollections.observableArrayList();
    @FXML private TableView<quehuo> quehuoTable;
    @FXML private TableColumn<quehuo, String> quehuowareNameColumn;
    @FXML private TableColumn<quehuo, String> quehuowareNumberColumn;
    @FXML private TableColumn<quehuo, String> quehuoamountColumn;
    @FXML private TableColumn<quehuo, String> quehuotimeColumn;
    @FXML private TableColumn<quehuo, String> quehuostorageColumn;


    private ObservableList<kucun> kucunData = FXCollections.observableArrayList();
    @FXML private TableView<kucun> kucunTable;
    @FXML private TableColumn<kucun, String> kucunwareNameColumn;
    @FXML private TableColumn<kucun, String> kucunwareNumberColumn;
    @FXML private TableColumn<kucun, String> kucunamountColumn;
    @FXML private TableColumn<kucun, String> kucunstorageColumn;
    @FXML private TableColumn<kucun, String> kucunfactoryColumn;
    @FXML public void initialize()
    {
        jinhuo2wareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        jinhuo2wareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        jinhuo2amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        jinhuo2timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeProperty()));
        jinhuo2factoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().factoryProperty()));
        jinhuo2storageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().storageProperty()));
        jinhuo2approvedColumn.setCellValueFactory(cellData-> cellData.getValue().chosen.getCheckBox());
        showjinhuo2();

        chuhuo2wareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        chuhuo2wareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        chuhuo2amountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        chuhuo2timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeProperty()));
        chuhuo2storageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().storageProperty()));
        chuhuo2approvedColumn.setCellValueFactory(cellData-> cellData.getValue().chosen.getCheckBox());
        showchuhuo2();


        quehuowareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        quehuowareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        quehuoamountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        quehuotimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().timeProperty()));
        quehuostorageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().storageProperty()));
        showquehuo();


        kucunwareNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNameProperty()));
        kucunwareNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().wareNumberProperty()));
        kucunamountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().amountProperty()));
        kucunfactoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().factoryProperty()));
        kucunstorageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().storageProperty()));
        showkucun();
    }
    void showjinhuo2()
    {

        System.out.print("ready to show jinhuo2 information");
        /*set up connection */
        linkMySql con = new linkMySql();
        Connection myCon = con.guanliconnect();
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
                jinhuo2Data.add(new jinhuo2(wareName, wareNumber, amount, time, factory, storage));
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
        jinhuo2Table.setItems(jinhuo2Data);
    }
    void showchuhuo2() {

        System.out.print("ready to show chuhuo2 information");
        /*set up connection */
        linkMySql con = new linkMySql();
        Connection myCon = con.guanliconnect();
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
            pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM export WHERE approved = 0");
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
                time = rs.getString("date").trim();
                time = time.substring(0,19);
                storage = rs.getString("place").trim();
                System.out.println(wareName + wareNumber + amount + time + storage);
                chuhuo2Data.add(new chuhuo2(wareName, wareNumber, amount, time, storage));
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
        chuhuo2Table.setItems(chuhuo2Data);
    }
    void showquehuo()
    {

        System.out.print("ready to show quehuo information");
        /*set up connection */
        linkMySql con = new linkMySql();
        Connection myCon = con.guanliconnect();
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
    void showkucun()
    {

        System.out.print("ready to show quehuo information");
        /*set up connection */
        linkMySql con = new linkMySql();
        Connection myCon = con.guanliconnect();
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
            pStatement=(PreparedStatement) myCon.prepareStatement("SELECT * FROM storage");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            rs = pStatement.executeQuery();
            System.out.println("selected successfully");
            String wareName = null;
            String wareNumber = null;
            String amount = null;
            String storage = null;
            String factory = null;
            while (rs.next()) {
                wareName = rs.getString("name").trim();
                wareNumber = rs.getString("number").trim();
                amount = rs.getString("amount").trim();
                storage = rs.getString("place").trim();
                factory = rs.getString("factory").trim();
                System.out.println(wareName + wareNumber + amount + storage + factory);
                kucunData.add(new kucun(wareName, wareNumber, amount, factory, storage));
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
        kucunTable.setItems(kucunData);
    }
    @FXML public void on_commit_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要提交吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0)
        {
            ObservableList<jinhuo2> list = jinhuo2Table.getItems();
            ResultSet rs = null;
            linkMySql con = new linkMySql();
            Connection myCon = con.guanliconnect();
            for(jinhuo2 o : list)
            {
                if(o.chosen.isSelected()) {
                    String wareName = null;
                    String wareNumber = null;
                    String storage = null;
                    String amount = null;
                    String factory = null;
                    wareName = o.wareNameProperty();
                    wareNumber = o.wareNumberProperty();
                    storage = o.storageProperty();
                    amount = o.amountProperty();
                    factory = o.factoryProperty();
                    String time = o.timeProperty();
                    PreparedStatement pStatement = null;
                    int ct = 0;
                    try {
                        String insertImport = "SELECT amount as ct from lack where number = ? and place = ?";
                        System.out.println(insertImport);
                        pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                        pStatement.setString(1, wareNumber);
                        pStatement.setString(2, storage);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        rs = pStatement.executeQuery();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        while (rs.next())
                        {
                            ct = rs.getInt("ct");
                        }
                        if (ct == 0) {
                            try {
                                String insertImport = "INSERT INTO storage values(?,?,?,?,?)";
                                System.out.println(insertImport);
                                pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                                pStatement.setString(1, wareName);
                                pStatement.setString(2, wareNumber);
                                pStatement.setString(3, storage);
                                pStatement.setInt(4, Integer.parseInt(amount));
                                pStatement.setString(5, factory);

                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                pStatement.executeUpdate();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            try {
                                String insertImport = "SELECT amount  from lack where number = ? and place = ?";
                                System.out.println(insertImport);
                                pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                                pStatement.setString(1, wareNumber);
                                pStatement.setString(2, storage);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                rs = pStatement.executeQuery();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                while (rs.next()) {
                                    int amount2 = rs.getInt("amount");
                                    if (Integer.parseInt(amount) > amount2)
                                    {
                                        int quehuoAmount = Integer.parseInt(amount) - amount2;
                                        try {
                                            String insertImport = "INSERT INTO storage values(?,?,?,?,?)";
                                            System.out.println(insertImport);
                                            pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                                            pStatement.setString(1, wareName);
                                            pStatement.setString(2, wareNumber);
                                            pStatement.setString(3, storage);
                                            pStatement.setInt(4, quehuoAmount);
                                            pStatement.setString(5, factory);
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                        try {
                                            pStatement.executeUpdate();
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                        String str = String.format("DELETE FROM lack WHERE number = \"%s\" AND place = \"%s\"", wareNumber,storage);
                                        Statement stmt = null;
                                        try{
                                            stmt = myCon.createStatement();
                                        } catch(SQLException SqlEX) {
                                            SqlEX.printStackTrace();
                                        }
                                        try {
                                            stmt.execute(str);
                                        }catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    else if(Integer.parseInt(amount) < amount2)
                                    {
                                        int shengyuAmount = amount2 - Integer.parseInt(amount);
                                        String str = String.format("UPDATE lack SET amount = %d WHERE number = \"%s\" AND place = \"%s\"", shengyuAmount, wareNumber,storage);
                                        Statement stmt = null;
                                        try{
                                            stmt = myCon.createStatement();
                                        } catch(SQLException SqlEX) {
                                            SqlEX.printStackTrace();
                                        }
                                        try {
                                            stmt.execute(str);
                                        }catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    else if (Integer.parseInt(amount) == amount2)
                                    {
                                        String str = String.format("DELETE FROM lack WHERE number = \"%s\" AND place = \"%s\"", wareNumber,storage);
                                        Statement stmt = null;
                                        try{
                                            stmt = myCon.createStatement();
                                        } catch(SQLException SqlEX) {
                                            SqlEX.printStackTrace();
                                        }
                                        try {
                                            stmt.execute(str);
                                        }catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                        }
                    } catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                    /*close the connection*/
                    String str = String.format("UPDATE import SET approved = 1 WHERE approved = 0 AND date = \"%s\" AND number = \"%s\" AND place = \"%s\"", time, wareNumber,storage);
                    Statement stmt = null;
                    try{
                        stmt = myCon.createStatement();
                    } catch(SQLException SqlEX) {
                        SqlEX.printStackTrace();
                    }
                    try {
                        stmt.execute(str);
                    }catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            try
            {
                myCon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage tempStage = (Stage) exitButton.getScene().getWindow();
            tempStage.close();
            JOptionPane.showMessageDialog(null, "提交成功！");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("guanli.fxml")
            );
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            try {
                myScene.setRoot((Parent)loader.load());
                Stage newStage = new Stage();
                newStage.setTitle("管理人员操作界面");
                newStage.setScene(myScene);
                newStage.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else return;
    }
    @FXML public void on_commit2_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要提交吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0)
        {
            ResultSet rs = null;
            ObservableList<chuhuo2> list = chuhuo2Table.getItems();
            linkMySql con = new linkMySql();
            Connection myCon = con.guanliconnect();
            for(chuhuo2 o : list)
            {
                if(o.chosen.isSelected())
                {
                    String wareNumber = o.wareNumberProperty();
                    String wareName = o.wareNameProperty();
                    String amount = o.amountProperty();
                    String time = o.timeProperty();
                    String storage = o.storageProperty();
                    PreparedStatement pStatement = null;
                    int ct = 0;
                    try {
                        String insertImport = "SELECT amount as ct from storage where number = ? and place = ?";
                        System.out.println(insertImport);
                        pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                        pStatement.setString(1, wareNumber);
                        pStatement.setString(2, storage);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        rs = pStatement.executeQuery();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        while (rs.next())
                        {
                            ct = rs.getInt("ct");
                        }
                        if (ct == 0) {
                            JOptionPane.showMessageDialog(null,"当前货物暂无库存，已记载入缺货表！");
                            try {
                                String insertImport = "INSERT INTO lack values(?,?,?,?,?)";
                                System.out.println(insertImport);
                                pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                                pStatement.setString(1, wareName);
                                pStatement.setString(2, wareNumber);
                                pStatement.setInt(3, Integer.parseInt(amount));
                                pStatement.setString(4, time);
                                pStatement.setString(5, storage);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                pStatement.executeUpdate();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else {
                            try {
                                String insertImport = "SELECT amount  from storage where number = ? and place = ?";
                                System.out.println(insertImport);
                                pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                                pStatement.setString(1, wareNumber);
                                pStatement.setString(2, storage);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                rs = pStatement.executeQuery();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                while (rs.next()) {
                                    int amount2 = rs.getInt("amount");
                                    if (Integer.parseInt(amount) > amount2)
                                    {
                                        JOptionPane.showMessageDialog(null,"当前货物库存量不足，不足部分已记载入缺货表！");
                                        int quehuoAmount = Integer.parseInt(amount) - amount2;
                                        try {
                                            String quehuo = String.valueOf(quehuoAmount);
                                            String insertImport = "INSERT INTO lack values(?,?,?,?,?)";
                                            System.out.println(insertImport);
                                            pStatement=(PreparedStatement) myCon.prepareStatement(insertImport);
                                            pStatement.setString(1, wareName);
                                            pStatement.setString(2, wareNumber);
                                            pStatement.setString(3, quehuo);
                                            pStatement.setString(4, time);
                                            pStatement.setString(5, storage);
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                        try {
                                            pStatement.executeQuery();
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                        String str = String.format("DELETE FROM storage WHERE number = \"%s\" AND place = \"%s\"", wareNumber,storage);
                                        Statement stmt = null;
                                        try{
                                            stmt = myCon.createStatement();
                                        } catch(SQLException SqlEX) {
                                            SqlEX.printStackTrace();
                                        }
                                        try {
                                            stmt.execute(str);
                                        }catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    else if(Integer.parseInt(amount) < amount2)
                                    {
                                        int shengyuAmount = amount2 - Integer.parseInt(amount);
                                        String str = String.format("UPDATE storage SET amount = %d WHERE number = \"%s\" AND place = \"%s\"", shengyuAmount, wareNumber,storage);
                                        Statement stmt = null;
                                        try{
                                            stmt = myCon.createStatement();
                                        } catch(SQLException SqlEX) {
                                            SqlEX.printStackTrace();
                                        }
                                        try {
                                            stmt.execute(str);
                                        }catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    else if (Integer.parseInt(amount) == amount2)
                                    {
                                        String str = String.format("DELETE FROM storage WHERE number = \"%s\" AND place = \"%s\"", wareNumber,storage);
                                        Statement stmt = null;
                                        try{
                                            stmt = myCon.createStatement();
                                        } catch(SQLException SqlEX) {
                                            SqlEX.printStackTrace();
                                        }
                                        try {
                                            stmt.execute(str);
                                        }catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                        }
                    } catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                    /*close the connection*/
                    String str = String.format("UPDATE export SET approved = 1 WHERE approved = 0 AND date = \"%s\" AND number = \"%s\" AND place = \"%s\"", time, wareNumber,storage);
                    Statement stmt = null;
                    try{
                        stmt = myCon.createStatement();
                    } catch(SQLException SqlEX) {
                        SqlEX.printStackTrace();
                    }
                    try {
                        stmt.execute(str);
                    }catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }

            }
            try
            {
                myCon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage tempStage = (Stage) exitButton.getScene().getWindow();
            tempStage.close();
            JOptionPane.showMessageDialog(null, "提交成功！");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("guanli.fxml")
            );
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            try {
                myScene.setRoot((Parent)loader.load());
                Stage newStage = new Stage();
                newStage.setTitle("管理人员操作界面");
                newStage.setScene(myScene);
                newStage.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else return;
    }
    @FXML public void on_exit_clicked()
    {
        int n = JOptionPane.showConfirmDialog(null, "确定要退出吗?", "提示",JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            JOptionPane.showMessageDialog(null, "欢迎下次使用！");
            Stage tempStage = (Stage)exitButton.getScene().getWindow();
            tempStage.close();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml")
            );
            AnchorPane root = new AnchorPane();
            Scene myScene = new Scene(root);
            try {
                myScene.setRoot((Parent) loader.load());
                Stage newStage = new Stage();
                newStage.setTitle("登录界面");
                newStage.setScene(myScene);
                newStage.show();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else return;
    }

}
