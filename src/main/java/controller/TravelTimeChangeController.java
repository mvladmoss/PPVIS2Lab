package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import view.InsertRecordPane;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class TravelTimeChangeController implements ChangeListener {

    private static final String PATTERN = "%02d:%02d";
    private InsertRecordPane insertPane;

    public TravelTimeChangeController(InsertRecordPane insertPane) {
        this.insertPane = insertPane;
    }

    private String calculateDuration(){
        System.out.println(insertPane.getDepartureDate().getValue().toString());
        LocalDateTime departureDateTime = LocalDateTime.of(insertPane.getDepartureDate().getValue(), insertPane.getDepartureTime().getValue());
        LocalDateTime arrivalDateTime = LocalDateTime.of(insertPane.getArrivalDate().getValue(), insertPane.getArrivalTime().getValue());
        Duration dur = Duration.between(departureDateTime, arrivalDateTime);
        long millis = dur.toMillis();
        return String.format(PATTERN,
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        String travelTime = calculateDuration();
        System.out.println(observable.getValue());
        insertPane.getTravelDuration().setText(travelTime);

    }
}
