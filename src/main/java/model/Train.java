package model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
@EqualsAndHashCode
public class Train {

    private LongProperty idProperty;
    private long id;

    public Train(long id){
        this.idProperty = new SimpleLongProperty(id);
    }

    public LongProperty getIdProperty() {
        return idProperty;
    }

    public long getId(){
        return idProperty.get();
    }
}
