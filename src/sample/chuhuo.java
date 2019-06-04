package sample;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class chuhuo {
    private final StringProperty wareName;
    private final StringProperty wareNumber;
    private final StringProperty amount;
    private final StringProperty time;
    private final StringProperty storage;
    public chuhuo(String wareName,String wareNumber,String amount, String time, String storage)
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


