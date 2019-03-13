package controller;

import model.MainTrainSheduleRepository;
import view.Pagination;
import model.Station;
import model.Train;
import model.TrainShedule;
import javafx.scene.control.Alert;
import view.InsertRecordPane;
import view.Table;
import java.time.Duration;
import java.time.LocalDateTime;

public class InsertXmlDataContoller {

    private InsertRecordPane insertRecordPane;

    public InsertXmlDataContoller(InsertRecordPane insertRecordPane){
        this.insertRecordPane = insertRecordPane;
    }

    public TrainShedule addTrainShedule() {
            Train train = new Train(Long.parseLong(insertRecordPane.getTrainNumberField().getText()));
            Station departureStation = new Station(insertRecordPane.getDepartureStationField().getText());
            Station arrivalStation = new Station(insertRecordPane.getArrivalStationField().getText());
            LocalDateTime departureDateTime = LocalDateTime.of(insertRecordPane.getDepartureDate().getValue(), insertRecordPane.getDepartureTime().getValue());
            LocalDateTime arrivalDateTime = LocalDateTime.of(insertRecordPane.getArrivalDate().getValue(), insertRecordPane.getArrivalTime().getValue());
            Duration travelDuration = Duration.between(departureDateTime, arrivalDateTime);
            TrainShedule trainShedule = new TrainShedule(train, departureStation, arrivalStation, departureDateTime, arrivalDateTime, travelDuration);
            MainTrainSheduleRepository repository = MainTrainSheduleRepository.getInstance();
            repository.add(trainShedule);
            return trainShedule;
    }
}
