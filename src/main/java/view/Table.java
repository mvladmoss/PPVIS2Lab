package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.Pagination;
import model.TrainShedule;

public class Table {
    private final static int TABLE_WIDTH = 815;
    private final static int TABLE_HEIGHT = 500;
    private final static double TABLE_TOP_ANCHOR = 31d;
    private final static String TRAIN_NUMBER_COLUMN = "Train number";
    private final static String DEPARTURE_STATION_COLUMN = "Departure station";
    private final static String ARRIVAL_STATION_COLUMN = "Arrival station";
    private final static String DEPATURE_DATE_TIME_COLUMN = "Depature date and time";
    private final static String ARRIVAL_DATE_TIME_COLUMN = "Arrival date and time";
    private final static String TRAVEL_DURATION = "Travel duration";
    private final static String NEXT_PAGE = "Next";
    private final static String PREVIOUS_PAGE = "Previos";
    private final static String FIRST_PAGE = "First page";
    private final static String LAST_PAGE = "Last page";
    private Text currentPageText;
    private Text allPagesText;
    private Pagination pagination;
    private ObservableList observableList;
    private double leftAnchor;
    private TextField totalRecords;

    Table(){
        observableList = FXCollections.observableArrayList();
        pagination = new Pagination();
    }

    void customTable(Pane pane, double leftAnchor) {
        TableView<TrainShedule> tableView = new TableView<>(observableList);
        tableView.setMinWidth(TABLE_WIDTH);
        tableView.setMinHeight(TABLE_HEIGHT);
        pane.getChildren().addAll(tableView);
        AnchorPane.setTopAnchor(tableView,TABLE_TOP_ANCHOR);
        this.leftAnchor = leftAnchor;
        AnchorPane.setLeftAnchor(tableView,leftAnchor);
        addColumns(tableView);
        pagination.setAvaibleTrainShedules(observableList);
        createPaginationButton(pane,FIRST_PAGE,100d,leftAnchor+100);
        createPaginationButton(pane,NEXT_PAGE,60d,leftAnchor+220);
        createPaginationButton(pane,PREVIOUS_PAGE,70d,leftAnchor+300);
        createPaginationButton(pane,LAST_PAGE,100d,leftAnchor+400);
        createPagesNumeration(pane);
        createButtonCurrentPagesPagination(pane,5,leftAnchor + 620);
        createButtonCurrentPagesPagination(pane,10,leftAnchor + 680);
        createButtonCurrentPagesPagination(pane,15,leftAnchor +740);
        createButtonCurrentPagesPagination(pane,20,leftAnchor + 800);
        createAllRecordsText(pane);
    }

    private void addColumns(TableView tableView) {
        TableColumn<TrainShedule,Long> trainNumberColumn = new TableColumn<>(TRAIN_NUMBER_COLUMN);
        trainNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getTrain().getIdProperty().asObject());
        TableColumn<TrainShedule,String> depatureStationColumn = new TableColumn<>(DEPARTURE_STATION_COLUMN);
        depatureStationColumn.setCellValueFactory(cellData -> cellData.getValue().getDepatureStation().getStationNameProperty());
        TableColumn<TrainShedule,String> arrivalStationColumn = new TableColumn<>(ARRIVAL_STATION_COLUMN);
        arrivalStationColumn.setCellValueFactory(cellData -> cellData.getValue().getArriveStation().getStationNameProperty());
        TableColumn<TrainShedule,String> depatureDateTimeColumn = new TableColumn<>(DEPATURE_DATE_TIME_COLUMN);
        depatureDateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getDepatureDateStringProperty());
        TableColumn<TrainShedule,String> arrivalDateTimeColumn = new TableColumn<>(ARRIVAL_DATE_TIME_COLUMN);
        arrivalDateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getArrivalDateStringProperty());
        TableColumn<TrainShedule,String> durationColumn = new TableColumn<>(TRAVEL_DURATION);
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().getDurationStringProperty());
        tableView.getColumns().addAll(trainNumberColumn,depatureStationColumn,arrivalStationColumn,depatureDateTimeColumn,arrivalDateTimeColumn,durationColumn);
        trainNumberColumn.setPrefWidth(110d);
        depatureStationColumn.setPrefWidth(150d);
        arrivalStationColumn.setPrefWidth(110d);
        depatureDateTimeColumn.setPrefWidth(190d);
        arrivalDateTimeColumn.setPrefWidth(170d);
        durationColumn.setPrefWidth(130d);
    }

    private void createPaginationButton(Pane pane,String name,double width,double leftAnchor){
        Button button = new Button();
        button.setText(name);
        button.setPrefHeight(40d);
        button.setPrefWidth(width);
        AnchorPane.setLeftAnchor(button,leftAnchor);
        AnchorPane.setTopAnchor(button,550d);
        switch (name){
            case NEXT_PAGE:{
                button.setOnAction(
                        event -> {
                        pagination.setNextPage();
                        currentPageText.setText(String.valueOf(pagination.getSelectedPage()));
                });
                break;
            }
            case PREVIOUS_PAGE:{
                button.setOnAction(
                        event -> {
                            pagination.setPrevoisPage();
                            currentPageText.setText(String.valueOf(pagination.getSelectedPage()));
                        });
                break;
            }
            case FIRST_PAGE:{
                button.setOnAction(
                        event -> {
                            pagination.setFirstPage();
                            currentPageText.setText(String.valueOf(pagination.getSelectedPage()));
                        });
                break;
            }
            case LAST_PAGE:{
                button.setOnAction(
                        event -> {
                            pagination.setLastPage();
                            currentPageText.setText(String.valueOf(pagination.getSelectedPage()));
                        });
                break;
            }
        }
        pane.getChildren().add(button);
    }

    private void createPagesNumeration(Pane pane){
        currentPageText = new Text();
        currentPageText.setText("0");
        allPagesText = new Text();
        allPagesText.setText("0");
        Text ofText = new Text();
        ofText.setText("of");
        AnchorPane.setTopAnchor(currentPageText,540d);
        AnchorPane.setTopAnchor(allPagesText,540d);
        AnchorPane.setTopAnchor(ofText,540d);
        AnchorPane.setLeftAnchor(currentPageText,leftAnchor );
        AnchorPane.setLeftAnchor(ofText,leftAnchor + 10);
        AnchorPane.setLeftAnchor(allPagesText,leftAnchor + 30);
        pane.getChildren().addAll(currentPageText,ofText,allPagesText);
    }

    private void createButtonCurrentPagesPagination(Pane pane,int number,double leftAnchor){
        Button button = new Button();
        button.setText(String.valueOf(number));
        AnchorPane.setTopAnchor(button,550d);
        AnchorPane.setLeftAnchor(button,leftAnchor);
        button.setOnAction(event -> {
            pagination.setVisibleCount(number);
            pagination.setFirstPage();
            pagination.recalculateNumberOfPages();
            setCurrentPage(pagination.getSelectedPage());
            setAllPages(pagination.getNumberOfPages());
        });
        pane.getChildren().add(button);
    }

    private void createAllRecordsText(Pane pane){
        Text text = new Text("Total records");
        totalRecords = new TextField();
        totalRecords.setEditable(false);
        AnchorPane.setTopAnchor(text,600d);
        AnchorPane.setLeftAnchor(text,700d);
        AnchorPane.setTopAnchor(totalRecords,600d);
        AnchorPane.setLeftAnchor(totalRecords,800d);
        pane.getChildren().addAll(text,totalRecords);
        totalRecords.setText(String.valueOf(pagination.getElementsCount()));
    }

    public Text getAllPagesText() {
        return allPagesText;
    }

    public Text getCurrentPageText() {
        return currentPageText;
    }

    public void setAllPages(int number){
        this.allPagesText.setText(String.valueOf(number));
    }

    public void setCurrentPage(int number){
        this.currentPageText.setText(String.valueOf(number));
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setTotalRecordsNumber(int totalRecords) {
        this.totalRecords.setText(String.valueOf(totalRecords));
    }
}
