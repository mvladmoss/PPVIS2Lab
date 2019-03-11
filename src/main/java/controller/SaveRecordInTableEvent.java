package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.Pagination;
import model.Station;
import model.Train;
import model.TrainShedule;
import javafx.scene.control.Alert;
import view.InsertRecordPane;
import view.Table;
import java.time.Duration;
import java.time.LocalDateTime;

public class SaveRecordInTableEvent implements EventHandler {

    private final static String EMPTY_FIELD = "Please fill all fields";
    private final static String INCORRECT_INPUT_DATE = "Please input correct data";

    private InsertRecordPane insertRecordPane;
    private Table table;

    public SaveRecordInTableEvent(InsertRecordPane insertRecordPane,Table table){
        this.insertRecordPane = insertRecordPane;
        this.table = table;
    }

    @Override
    public void handle(Event event) {
        try {
            Train train = new Train(Long.parseLong(insertRecordPane.getTrainNumberField().getText()));
            Station departureStation = new Station(insertRecordPane.getDepartureStationField().getText());
            Station arrivalStation = new Station(insertRecordPane.getArrivalStationField().getText());
            LocalDateTime departureDateTime = LocalDateTime.of(insertRecordPane.getDepartureDate().getValue(), insertRecordPane.getDepartureTime().getValue());
            LocalDateTime arrivalDateTime = LocalDateTime.of(insertRecordPane.getArrivalDate().getValue(), insertRecordPane.getArrivalTime().getValue());
            Duration travelDuration = Duration.between(departureDateTime, arrivalDateTime);
            TrainShedule trainShedule = new TrainShedule(train, departureStation, arrivalStation, departureDateTime, arrivalDateTime, travelDuration);
            Pagination pagination = table.getPagination();
            pagination.addNewRecord(trainShedule);
            table.setTotalRecordsNumber(pagination.getElementsCount());
        }catch (NullPointerException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(EMPTY_FIELD);
            alert.showAndWait();
        }catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(INCORRECT_INPUT_DATE);
            alert.showAndWait();
        }

    }
}
