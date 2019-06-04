package sample;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class kucun {
    private final StringProperty wareName;
    private final StringProperty wareNumber;
    private final StringProperty amount;
    private final StringProperty factory;
    private final StringProperty storage;

    public kucun(String wareName,String wareNumber,String amount,String factory, String storage)
    {
        this.wareName = new SimpleStringProperty(wareName);
        this.wareNumber = new SimpleStringProperty(wareNumber);
        this.amount = new SimpleStringProperty(amount);
        this.factory = new SimpleStringProperty(factory);
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
    public String factoryProperty()
    {
        return factory.get();
    }
    public String storageProperty()
    {
        return storage.get();
    }

}



