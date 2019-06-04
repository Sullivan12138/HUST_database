package sample;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.*;

public class chuhuo2 {
    private final StringProperty wareName;
    private final StringProperty wareNumber;
    private final StringProperty amount;
    private final StringProperty time;
    private final StringProperty storage;
    public checkbox chosen = new checkbox();

    public chuhuo2(String wareName,String wareNumber,String amount, String time, String storage)
    {
        this.wareName = new SimpleStringProperty(wareName);
        this.wareNumber = new SimpleStringProperty(wareNumber);
        this.amount = new SimpleStringProperty(amount);
        this.time = new SimpleStringProperty(time);
        this.storage = new SimpleStringProperty(storage);
    }

    public String wareNameProperty() {
        return wareName.get();
    }
    public String wareNumberProperty() {
        return wareNumber.get();
    }
    public String amountProperty()
    {
        return amount.get();
    }
    public String timeProperty()
    {
        return time.get();
    }
    public String storageProperty()
    {
        return storage.get();
    }
}



