package controller.specifications;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pagination;
import model.SpecificationsEvents;
import model.TrainShedule;
import specifications.TrainSheduleSpecification;
import view.MainPane;
import view.Table;
import view.TimeSpinner;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static model.SpecificationsEvents.ARRIVAL_STATION;
import static model.SpecificationsEvents.DEPARTURE_STATION;
import static model.SpecificationsEvents.TRAIN_NUMBER;

public class ActionSpecification implements EventHandler {

    private final static int START_WINDOW_WIDTH = 300;
    private final static int START_WINDOW_HEIGHT = 100;
    private static final String BY_DEPARTURE_DATE = "By departure date";
    private static final String BY_DEPARTURE_TIME = "By departure time";
    private static final String BY_ARRIVAL_TIME = "By departure time";
    private static final String BY_ARRIVAL_DATE = "By arrival date";
    private static final String BY_TRAIN_NUMBER = "By departure date";
    private static final String BY_DEPARTURE_STATION = "By departure station";
    private static final String BY_ARRIVAL_STATION = "By arrival station";
    private static final String BY_TRAVEL_TIME = "By travel time";
    private static final String FIND = "Find";
    private static final String DELETE = "Delete";
    private static final String NO_SUCH_RECORDS = "No records were found to delete";
    private static final String DELETE_INFORMATION = " records were deleted";
    private Table table;
    private SpecificationsEvents specification;
    private boolean deleteSpecification;

    public ActionSpecification(Table table, SpecificationsEvents specification, boolean deleteSpecification){
        this.table = table;
        this.specification = specification;
        this.deleteSpecification = deleteSpecification;
    }

    @Override
    public void handle(Event mainEvent) {
        AtomicReference<Predicate<TrainShedule>> shedulePredicate = new AtomicReference<>();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,START_WINDOW_WIDTH,START_WINDOW_HEIGHT);
        Button findButton = new Button();
        if(deleteSpecification){
            findButton.setText(DELETE);
        }else{
            findButton.setText(FIND);
        }
        VBox vBox = new VBox();
        root.getChildren().addAll(vBox);
        stage.setScene(scene);
        switch(specification){
            case TRAIN_NUMBER:{
                stage.setTitle(BY_TRAIN_NUMBER);
                TextField trainNumberField = new TextField(TRAIN_NUMBER.getSpecificationName());
                vBox.getChildren().addAll(trainNumberField,findButton);
                findButton.setOnAction(event -> {
                    long trainNUmber = Long.parseLong(trainNumberField.getText());
                    shedulePredicate.set(TrainSheduleSpecification.findByTrainNumber(trainNUmber));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case DEPARTURE_DATE:{
                stage.setTitle(BY_DEPARTURE_DATE);
                DatePicker datePicker = new DatePicker();
                vBox.getChildren().addAll(datePicker,findButton);
                findButton.setOnAction(event -> {
                    LocalDate departureDate = datePicker.getValue();
                    shedulePredicate.set(TrainSheduleSpecification.findByDepartureDate(departureDate));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case DEPARTURE_TIME:{
                stage.setTitle(BY_DEPARTURE_TIME);
                TimeSpinner startTime = new TimeSpinner();
                TimeSpinner endTime = new TimeSpinner();
                vBox.getChildren().addAll(startTime,endTime,findButton);
                findButton.setOnAction(event -> {
                    LocalTime departureTimeValue = startTime.getValue();
                    LocalTime arrivalTimeValue = endTime.getValue();
                    shedulePredicate.set(TrainSheduleSpecification.findByDepartureTimePeriod(departureTimeValue,arrivalTimeValue));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case ARRIVAL_DATE:{
                stage.setTitle(BY_ARRIVAL_DATE);
                DatePicker datePicker = new DatePicker();
                vBox.getChildren().addAll(datePicker,findButton);
                findButton.setOnAction(event -> {
                    LocalDate arrivalDate = datePicker.getValue();
                    shedulePredicate.set(TrainSheduleSpecification.findByArrivalDate(arrivalDate));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case ARRIVAL_TIME:{
                stage.setTitle(BY_ARRIVAL_TIME);
                TimeSpinner startTime = new TimeSpinner();
                TimeSpinner endTime = new TimeSpinner();
                vBox.getChildren().addAll(startTime,endTime,findButton);
                findButton.setOnAction(event -> {
                    LocalTime startTimeValue = startTime.getValue();
                    LocalTime endTimeValue = endTime.getValue();
                    shedulePredicate.set(TrainSheduleSpecification.findByArrivalTimePeriod(startTimeValue,endTimeValue));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case DEPARTURE_STATION:{
                stage.setTitle(BY_DEPARTURE_STATION);
                TextField stationNameField = new TextField(DEPARTURE_STATION.getSpecificationName());
                vBox.getChildren().addAll(stationNameField,findButton);
                findButton.setOnAction(event -> {
                    String stationName = stationNameField.getText();
                    shedulePredicate.set(TrainSheduleSpecification.findByDepartureStation(stationName));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case ARRIVAL_STATION:{
                stage.setTitle(BY_ARRIVAL_STATION);
                TextField stationNameField = new TextField(ARRIVAL_STATION.getSpecificationName());
                vBox.getChildren().addAll(stationNameField,findButton);
                findButton.setOnAction(event -> {
                    String stationName = stationNameField.getText();
                    shedulePredicate.set(TrainSheduleSpecification.findByArrivalStation(stationName));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
            case TRAVEL_TIME:{
                stage.setTitle(BY_TRAVEL_TIME);
                TimeSpinner travelTime = new TimeSpinner();
                vBox.getChildren().addAll(travelTime,findButton);
                findButton.setOnAction(event -> {
                    LocalTime endTimeValue = travelTime.getValue();
                    int hours = endTimeValue.getHour();
                    int minutes = endTimeValue.getMinute();
                    Duration duration = Duration.ofHours(hours);
                    duration = duration.plusMinutes(minutes);
                    shedulePredicate.set(TrainSheduleSpecification.findByTravelTime(duration));
                    stage.close();
                });
                stage.showAndWait();
                break;
            }
        }
        if(shedulePredicate.get()==null){
            return;
        }

        if(deleteSpecification){
            Pagination pagination = MainPane.getTable().getPagination();
            List<TrainShedule> allShedules = pagination.getAllTrainShedules();
            List<TrainShedule> findShedules = allShedules.stream()
                    .filter(shedulePredicate.get())
                    .collect(Collectors.toList());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(findShedules.size()==0){
                alert.setContentText(NO_SUCH_RECORDS);
                alert.showAndWait();
                stage.close();
            }else{
                deleteRecords(findShedules);
                alert.setContentText(findShedules.size() + DELETE_INFORMATION);
                alert.showAndWait();
                stage.close();
            }
        }else{
            Pagination pagination = table.getPagination();
            List<TrainShedule> allShedules = pagination.getAllTrainShedules();
            List<TrainShedule> findShedules = allShedules.stream()
                    .filter(shedulePredicate.get())
                    .collect(Collectors.toList());
            findRecords(findShedules,pagination);
        }

    }

    private void deleteRecords(List<TrainShedule> findShedules){
        Table table = MainPane.getTable();
        Pagination pagination = table.getPagination();
        List<TrainShedule> allShedules =  pagination.getAllTrainShedules();
        for(TrainShedule trainShedule : findShedules){
            allShedules.remove(trainShedule);
        }
        pagination.setAllTrainShedules(allShedules);
        pagination.getAvaibleTrainShedules().clear();
        pagination.recalculateNumberOfPages();
        pagination.setFirstPage();
        table.setAllPages(pagination.getNumberOfPages());
        table.setCurrentPage(pagination.getSelectedPage());
        table.setTotalRecordsNumber(pagination.getElementsCount());
    }

    private void findRecords(List<TrainShedule> remainShedules, Pagination pagination){
        pagination.getAllTrainShedules().clear();
        pagination.getAvaibleTrainShedules().clear();
        pagination.setAllTrainShedules(remainShedules);
        pagination.recalculateNumberOfPages();
        pagination.setFirstPage();
        table.setAllPages(pagination.getNumberOfPages());
        table.setCurrentPage(pagination.getSelectedPage());
        table.setTotalRecordsNumber(pagination.getElementsCount());
    }



}
