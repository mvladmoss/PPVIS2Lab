package controller;

import model.TrainSheduleRepository;
import model.Station;
import model.Train;
import model.TrainShedule;
import view.InsertRecordPane;
import java.time.Duration;
import java.time.LocalDateTime;

public class InsertXmlDataContoller {

    private InsertRecordPane insertRecordPane;
    private TrainSheduleRepository repository;

    public InsertXmlDataContoller(InsertRecordPane insertRecordPane,TrainSheduleRepository repository){
        this.insertRecordPane = insertRecordPane;
        this.repository = repository;
    }

    public TrainShedule addTrainShedule() {
            Train train = new Train(Long.parseLong(insertRecordPane.getTrainNumberField().getText()));
            Station departureStation = new Station(insertRecordPane.getDepartureStationField().getText());
            Station arrivalStation = new Station(insertRecordPane.getArrivalStationField().getText());
            LocalDateTime departureDateTime = LocalDateTime.of(insertRecordPane.getDepartureDate().getValue(), insertRecordPane.getDepartureTime().getValue());
            LocalDateTime arrivalDateTime = LocalDateTime.of(insertRecordPane.getArrivalDate().getValue(), insertRecordPane.getArrivalTime().getValue());
            Duration travelDuration = Duration.between(departureDateTime, arrivalDateTime);
            TrainShedule trainShedule = new TrainShedule(train, departureStation, arrivalStation, departureDateTime, arrivalDateTime, travelDuration);
            repository.add(trainShedule);
            return trainShedule;
    }
}
