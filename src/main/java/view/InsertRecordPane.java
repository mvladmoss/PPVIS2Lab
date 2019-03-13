package view;

import controller.InsertXmlDataContoller;
import controller.TravelTimeChangeController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.TrainShedule;
import model.TrainSheduleRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class InsertRecordPane {

    private final static int START_WINDOW_WIDTH = 800;
    private final static int START_WINDOW_HEIGHT = 320;
    private static final String TRAIN_NUMBER = "Train number";
    private static final String DEPARTURE_STATION = "Departure station";
    private static final String ARRIVAL_STATION = "Arrival station";
    private static final String DEPARTURE_TIME = "Departure date and time";
    private static final String ARRIVAL_TIME = "Arrival date and time";
    private static final String TRAVEL_TIME = "Travel time";
    private static final String INITIAL_TRAVEL_DURATION = "00:00";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private static final int INITIAL_YEAR = 2019;
    private static final int INITIAL_MONTH = 3;
    private static final int INITIAL_DAY = 3;
    private static final double TOP_ANCHOR = 100d;
    private static final double PANE_WIDTH = 110d;
    private static final double PANE_HEIGHT = 30d;
    private static final String ADD_BUTTON_NAME = "add";
    private static final String CLOSE_BUTTON_NAME = "close";
    private static final double BUTTON_TOP_ANCHOR = 230d;

    private TextField trainNumberField;
    private TextField departureStationField;
    private TextField arrivalStationField;
    private DatePicker departureDate;
    private DatePicker arrivalDate;
    private TimeSpinner departureTime;
    private TimeSpinner arrivalTime;
    private TextField travelDuration;
    private Button addRecordButton;
    private Button closeButton;
    private Stage stage;
    private Table table;
    private TrainSheduleRepository repository;

    InsertRecordPane(Table table, TrainSheduleRepository repository) {
        this.trainNumberField = new TextField();
        this.repository = repository;
        this.departureStationField = new TextField();
        this.arrivalStationField = new TextField();
        this.departureDate = new DatePicker();
        this.arrivalDate = new DatePicker();
        this.departureTime = new TimeSpinner();
        this.arrivalTime = new TimeSpinner();
        this.travelDuration = new TextField();
        this.table = table;
        start();
    }

    private void start(){
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,START_WINDOW_WIDTH,START_WINDOW_HEIGHT);
        stage = new Stage();
        stage.setScene(scene);
        createInputField(root,TRAIN_NUMBER,20d,trainNumberField);
        createInputField(root,DEPARTURE_STATION,240d,departureStationField);
        createInputField(root,ARRIVAL_STATION,460d,arrivalStationField);
        createCalendar(root,DEPARTURE_TIME,departureDate,departureTime,20d);
        createCalendar(root,ARRIVAL_TIME,arrivalDate,arrivalTime,280d);
        createTextField(root,travelDuration);
        createAddButton(root);
        createCloseButton(root);
        stage.show();
    }

    private void createAddButton(Pane pane){
        addRecordButton = new Button();
        addRecordButton.setText(ADD_BUTTON_NAME);
        addRecordButton.setOnAction(event -> insertTrainShedule());
        pane.getChildren().add(addRecordButton);
        AnchorPane.setTopAnchor(addRecordButton,BUTTON_TOP_ANCHOR);
        AnchorPane.setLeftAnchor(addRecordButton,30d);
    }

    private void createCloseButton(Pane pane){
        closeButton = new Button();
        closeButton.setText(CLOSE_BUTTON_NAME);
        closeButton.setOnAction(event -> stage.close());
        pane.getChildren().add(closeButton);
        AnchorPane.setTopAnchor(closeButton,BUTTON_TOP_ANCHOR);
        AnchorPane.setLeftAnchor(closeButton,120d);

    }

    private void createCalendar(Pane root, String name, DatePicker datePicker, TimeSpinner timeSpinner, double leftAnchor){
        TitledPane tilePane = new TitledPane();
        tilePane.setText(name);
        tilePane.setMinHeight(PANE_HEIGHT);
        tilePane.setMinWidth(PANE_WIDTH);
        tilePane.setCollapsible(false);
        VBox vBox = new VBox();
        datePicker.setValue(LocalDate.of(INITIAL_YEAR, INITIAL_MONTH,INITIAL_DAY));
        datePicker.setShowWeekNumbers(true);
        datePicker.valueProperty().addListener(new TravelTimeChangeController(this));
        timeSpinner.valueProperty().addListener(new TravelTimeChangeController(this));
        vBox.getChildren().addAll(datePicker,timeSpinner);
        tilePane.setContent(vBox);
        AnchorPane.setTopAnchor(tilePane,TOP_ANCHOR);
        AnchorPane.setLeftAnchor(tilePane,leftAnchor);
        root.getChildren().addAll(tilePane);
    }

    private void createInputField(Pane root, String textParameter,Double topAnchor,TextField textField){
        TitledPane tilePane = new TitledPane();
        tilePane.setText(textParameter);
        tilePane.setMinHeight(PANE_HEIGHT);
        tilePane.setMinWidth(PANE_WIDTH);
        tilePane.setCollapsible(false);
        VBox vBox = new VBox();
        vBox.getChildren().add(textField);
        tilePane.setContent(vBox);
        root.getChildren().addAll(tilePane);
        AnchorPane.setLeftAnchor(tilePane,topAnchor);
    }

    private void createTextField(Pane pane,TextField travelDuration){
        TitledPane titledPane = new TitledPane();
        titledPane.setText(TRAVEL_TIME);
        AnchorPane.setLeftAnchor(titledPane,530d);
        AnchorPane.setTopAnchor(titledPane,100d);
        VBox vBox = new VBox();
        titledPane.setMinHeight(PANE_HEIGHT);
        titledPane.setMinWidth(PANE_WIDTH);
        titledPane.setCollapsible(false);
        travelDuration.setEditable(false);
        travelDuration.setText(INITIAL_TRAVEL_DURATION);
        vBox.getChildren().addAll(travelDuration);
        titledPane.setContent(vBox);
        pane.getChildren().add(titledPane);
    }

    private void insertTrainShedule(){
        InsertXmlDataContoller contoller = new InsertXmlDataContoller(this,repository);
        TrainShedule trainShedule = contoller.addTrainShedule();
        Pagination pagination = table.getPagination();
        pagination.addNewRecord(trainShedule);
        table.setTotalRecordsNumber(pagination.getElementsCount());
    }
}
