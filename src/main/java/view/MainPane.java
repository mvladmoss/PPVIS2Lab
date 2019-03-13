package view;

import controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.TrainShedule;
import model.TrainSheduleRepository;

import java.util.List;

public class MainPane extends Application {

    private final static String PROGRAM_NAME = "Simple GUI Examples";
    private final static int START_WINDOW_WIDTH = 1000;
    private final static int START_WINDOW_HEIGHT = 700;
    private final static int MENU_BAR_WIDTH = 2000;
    private final static int MENU_BAR_HEIGHT = 30;
    private static final String IMAGE_URL_CHOOSE_FILE = "open.png";
    private static final String IMAGE_URL_INPUT_RECORD = "input.png";
    private static final String IMAGE_URL_SAVE_BUTTON = "save.png";
    private static final String IMAGE_URL_FIND_BUTTON = "find.png";
    private static final String IMAGE_URL_DELETE_BUTTON = "delete.png";
    private static final String FILE_MENU = "File";
    private static final String OPEN_FILE = "Open file";
    private static final String SAVE_FILE = "Save file";
    private static final String EXIT = "Exit";
    private static final String RECORDS = "Records";
    private static final String ADD_RECORD = "Add record";
    private static final String FIND_RECORD = "Find record";
    private static final String DELETE_RECORD = "Delete record";
    private static final double CHOOSE_FILE_BUTTON_TOP_ANCHOR = 170d;
    private static final double INPUT_RECORD_TOP_ANCHOR = 220d;
    private static final double SAVE_RECORD_TOP_ANCHOR = 270d;
    private static final double FIND_RECORD_TOP_ANCHOR = 320d;
    private static final double DELETE_RECORD_TOP_ANCHOR = 370d;
    private Table table;
    private Stage stage;
    private TrainSheduleRepository repository;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        AnchorPane root = new AnchorPane();
        primaryStage.setTitle(PROGRAM_NAME);
        primaryStage.setScene(new Scene(root, START_WINDOW_WIDTH, START_WINDOW_HEIGHT));
        table = new Table();
        table.customTable(root,115d);
        customMenu(root,primaryStage);
        repository = new TrainSheduleRepository();
        new ButtonCreator().customButton(root,IMAGE_URL_CHOOSE_FILE,CHOOSE_FILE_BUTTON_TOP_ANCHOR,event -> handleLoadXmlFile());
        new ButtonCreator().customButton(root, IMAGE_URL_INPUT_RECORD,INPUT_RECORD_TOP_ANCHOR,event -> new InsertRecordPane(table,repository));
        new ButtonCreator().customButton(root,IMAGE_URL_SAVE_BUTTON,SAVE_RECORD_TOP_ANCHOR,event -> new SaveRecordsController(primaryStage,repository).save());
        new ButtonCreator().customButton(root,IMAGE_URL_FIND_BUTTON,FIND_RECORD_TOP_ANCHOR,event -> new ActionRecordPane(repository));
        new ButtonCreator().customButton(root,IMAGE_URL_DELETE_BUTTON,DELETE_RECORD_TOP_ANCHOR,event -> new ActionRecordPane(table,repository));
        primaryStage.show();
    }

    private void customMenu(Pane pane,Stage stage){
        MenuBar menuBar = new MenuBar();
        menuBar.setMinSize(MENU_BAR_WIDTH,MENU_BAR_HEIGHT);

        Menu fileMenu = new Menu(FILE_MENU);
        MenuItem openFileItem = new MenuItem(OPEN_FILE);
        openFileItem.setOnAction(event -> handleLoadXmlFile());
        MenuItem saveFileItem = new MenuItem(SAVE_FILE);
        saveFileItem.setOnAction(event -> new SaveRecordsController(stage,repository).save());
        MenuItem exitProgramItem = new MenuItem(EXIT);
        exitProgramItem.setOnAction(event -> stage.close());
        fileMenu.getItems().addAll(openFileItem,saveFileItem,exitProgramItem);

        Menu recordMenu = new Menu(RECORDS);
        MenuItem addRecordItem = new MenuItem(ADD_RECORD);
        addRecordItem.setOnAction(event -> new InsertRecordPane(table,repository));
        MenuItem findRecordItem = new MenuItem(FIND_RECORD);
        findRecordItem.setOnAction(event -> new ActionRecordPane(repository));
        MenuItem deleteRecordItem = new MenuItem(DELETE_RECORD);
        deleteRecordItem.setOnAction(event -> new ActionRecordPane(table,repository));
        recordMenu.getItems().addAll(addRecordItem,findRecordItem,deleteRecordItem);

        menuBar.getMenus().addAll(fileMenu,recordMenu);
        pane.getChildren().addAll(menuBar);
    }

    private void handleLoadXmlFile(){
        LoadXmlFileContoller loadXmlFileContoller = new LoadXmlFileContoller(stage,repository);
        List<TrainShedule> trainShedules = loadXmlFileContoller.getData();
        table.setObservableList(trainShedules);
        Pagination pagination = table.getPagination();
        pagination.getAvaibleTrainShedules().clear();
        pagination.getAllTrainShedules().clear();
        pagination.setAllTrainShedules(trainShedules);
        pagination.setFirstPage();
        pagination.setPagesNumber(table.getAllPagesText());
        pagination.setStartPageNumber(table.getCurrentPageText());
        table.setTotalRecordsNumber(pagination.getElementsCount());
    }

}
