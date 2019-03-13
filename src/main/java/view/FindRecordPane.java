package view;

import controller.ActionController;
import controller.GetRecordsController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpecificationsEvents;
import model.TrainShedule;
import java.util.ArrayList;
import java.util.List;

class FindRecordPane {

    private final static int START_WINDOW_WIDTH = 1170;
    private final static int START_WINDOW_HEIGHT = 720;
    private Table table;
    private ToggleGroup toggleGroup;
    private List<RadioButton> buttonList;
    private ActionController controller = new ActionController(false);

    FindRecordPane() {
        start();
    }

    private void start() {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,START_WINDOW_WIDTH,START_WINDOW_HEIGHT);
        table = new Table();
        table.customTable(root,300d);
        customPagination();
        Stage stage = new Stage();
        stage.setScene(scene);
        createSpecifications(root);
        createChooseButton(root);
        createResetButton(root);
        stage.showAndWait();
    }

    private void customPagination() {
        Pagination pagination = table.getPagination();
        List<TrainShedule> allShedules = new ArrayList<>(new GetRecordsController().getRecords());
        pagination.setAllTrainShedules(allShedules);
        pagination.setFirstPage();
        pagination.setPagesNumber(table.getAllPagesText());
        pagination.setStartPageNumber(table.getCurrentPageText());
        table.setTotalRecordsNumber(pagination.getElementsCount());
    }

    private void createSpecifications(Pane pane) {
        toggleGroup = new ToggleGroup();
        buttonList = new ArrayList<>();
        double initialTopAnchor = 100d;
        for (SpecificationsEvents specification : SpecificationsEvents.values()) {
            RadioButton buttonSpecification = createButtonSpecification(specification, initialTopAnchor);
            pane.getChildren().add(buttonSpecification);
            buttonList.add(buttonSpecification);
            initialTopAnchor += 30;
        }
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
        findButton.setText("Choose");
        pane.getChildren().add(findButton);
        AnchorPane.setTopAnchor(findButton, 340d);
        AnchorPane.setLeftAnchor(findButton, 50d);
        findButton.setOnAction(event -> {
            for (RadioButton button : buttonList) {
                if (button.isSelected()) {
                    SpecificationsEvents specification = SpecificationsEvents.getSpecification(button.getText());
                    List<TrainShedule> shedules = controller.getData(specification);
                    Pagination pagination = table.getPagination();
                    pagination.refresh(shedules);
                    table.refreshTableView();
                }
            }
        });
    }

    private void createResetButton(Pane pane) {
        Button resetButton = new Button();
        resetButton.setText("Reset");
        pane.getChildren().add(resetButton);
        AnchorPane.setTopAnchor(resetButton, 340d);
        AnchorPane.setLeftAnchor(resetButton, 160d);
        resetButton.setOnAction(event -> {
            Pagination pagination = table.getPagination();
            GetRecordsController controller = new GetRecordsController();
            List<TrainShedule> shedules = controller.getRecords();
            pagination.refresh(shedules);
            table.refreshTableView();
        });
    }
}
