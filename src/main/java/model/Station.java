package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@EqualsAndHashCode
public class Station {

    private long id;
    private StringProperty stationName;

    public Station(String stationName){
        this.stationName = new SimpleStringProperty(stationName);
    }

    public StringProperty getStationNameProperty(){
        return stationName;
    }

    public String getStationName() {
        return stationName.get();
    }

    public long getId() {
        return id;
    }
}
