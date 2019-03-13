package view;

import controller.ActionController;
import controller.FindAllRecordsController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpecificationsEvents;
import model.TrainShedule;
import model.TrainSheduleRepository;

import java.util.ArrayList;
import java.util.List;

 class ActionRecordPane {

     private static final int START_WINDOW_WIDTH = 1170;
     private static final int START_WINDOW_HEIGHT = 680;
     private static final double INITIAL_TOP_ANCHOR = 0d;
     private static final String CHOOSE_BUTTON = "Choose";
     private static final String DELETE_PANE = "Delete pane";
     private ToggleGroup toggleGroup;
     private List<RadioButton> buttonList;
     private ActionController controller;
     private Table table;
     private Pane pane;
     private Stage stage;
     private TrainSheduleRepository repository;

     ActionRecordPane(Table table, TrainSheduleRepository repository) {
         this.table = table;
         this.repository = repository;
         controller = new ActionController(true,repository);
         start();
         stage.showAndWait();
     }

     ActionRecordPane(TrainSheduleRepository repository){
         this.table = new Table();
         this.repository = repository;
         controller =  new ActionController(false,repository);
         start();
         table.customTable(pane,300d);
         customPagination(pane);
         createResetButton(pane);
         stage.showAndWait();
     }

     private void start() {
         AnchorPane root = new AnchorPane();
         this.pane = root;
         Scene scene = new Scene(root, START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
         stage = new Stage();
         stage.setTitle(DELETE_PANE);
         stage.setScene(scene);
         createSpecifications(root);
         createChooseButton(root);
     }

     private void createSpecifications(Pane pane) {
         toggleGroup = new ToggleGroup();
         buttonList = new ArrayList<>();
         double initialTopAnchor = INITIAL_TOP_ANCHOR;
         for (SpecificationsEvents specification : SpecificationsEvents.values()) {
             RadioButton buttonSpecification = createButtonSpecification(specification, initialTopAnchor);
             pane.getChildren().add(buttonSpecification);
             buttonList.add(buttonSpecification);
             initialTopAnchor += 30;
         }
     }

     private void customPagination(Pane pane) {
         Pagination pagination = table.getPagination();
         List<TrainShedule> allShedules = new ArrayList<>(new FindAllRecordsController(repository).getRecords());
         pagination.setAllTrainShedules(allShedules);
         pagination.setFirstPage();
         pagination.setPagesNumber(table.getAllPagesText());
         pagination.setStartPageNumber(table.getCurrentPageText());
         table.setTotalRecordsNumber(pagination.getElementsCount());
         table.customTable(pane,300d);
     }

     private RadioButton createButtonSpecification(SpecificationsEvents specificationsEvents, double topAnchor) {
         RadioButton specification = new RadioButton(specificationsEvents.getSpecificationName());
         specification.setToggleGroup(toggleGroup);
         AnchorPane.setTopAnchor(specification, topAnchor);
         AnchorPane.setLeftAnchor(specification, 50d);
         return specification;
     }

     private void createChooseButton(Pane pane) {
         Button findButton = new Button();
         findButton.setText(CHOOSE_BUTTON);
         pane.getChildren().add(findButton);
         AnchorPane.setTopAnchor(findButton, 240d);
         AnchorPane.setLeftAnchor(findButton, 50d);
         findButton.setOnAction(event -> {
             for (RadioButton button : buttonList) {
                 if (button.isSelected()) {
                     SpecificationsEvents specification = SpecificationsEvents.getSpecification(button.getText());
                     List<TrainShedule> remainShedules = controller.getData(specification);
                     Pagination pagination = table.getPagination();
                     pagination.refresh(remainShedules);
                     table.refreshTableView();
                 }
             }
         });
     }

     private void createResetButton(Pane pane) {
         Button resetButton = new Button();
         resetButton.setText("Reset");
         pane.getChildren().add(resetButton);
         AnchorPane.setTopAnchor(resetButton, 240d);
         AnchorPane.setLeftAnchor(resetButton, 160d);
         resetButton.setOnAction(event -> {
             Pagination pagination = table.getPagination();
             FindAllRecordsController controller = new FindAllRecordsController(repository);
             List<TrainShedule> shedules = controller.getRecords();
             pagination.refresh(shedules);
             table.refreshTableView();
         });
     }
 }
