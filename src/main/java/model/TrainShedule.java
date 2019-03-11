package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class TrainShedule {

    private static final String DATETIME_PATTERN = "dd-MM-yyyy HH:mm";
    private static final String DURATION_REGEX = "(\\d[HM])(?!$)";

    private Train train;
    private Station depatureStation;
    private Station arriveStation;
    private LocalDateTime depatureDate;
    private LocalDateTime arrivalDate;
    private Duration travelDuration;

    public TrainShedule(Train train, Station depatureStation, Station arriveStation, LocalDateTime depatureDate, LocalDateTime arrivalDate, Duration travelDuration) {
        this.train = train;
        this.depatureStation = depatureStation;
        this.arriveStation = arriveStation;
        this.depatureDate = depatureDate;
        this.arrivalDate = arrivalDate;
        this.travelDuration = travelDuration;
    }

    public StringProperty getArrivalDateStringProperty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        return new SimpleStringProperty(arrivalDate.format(formatter));
    }

    public StringProperty getDepatureDateStringProperty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        return new SimpleStringProperty(depatureDate.format(formatter));
    }

    public StringProperty getDurationStringProperty(){
        String stringDuration = travelDuration.toString();
        stringDuration = travelDuration.toString()
                .substring(2,stringDuration.indexOf('M') + 1)
                .replaceAll(DURATION_REGEX, "$1 ")
                .toLowerCase();
        return new SimpleStringProperty(stringDuration);
    }
}
