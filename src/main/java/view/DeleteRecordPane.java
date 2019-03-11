package view;

import controller.specifications.ActionSpecification;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpecificationsEvents;
import java.util.ArrayList;
import java.util.List;

public class DeleteRecordPane {

    private static final int START_WINDOW_WIDTH = 370;
    private static final int START_WINDOW_HEIGHT = 280;
    private static final double INITIAL_TOP_ANCHOR = 0d;
    private static final String CHOOSE_BUTTON = "Choose";
    private static final String DELETE_PANE = "Delete pane";
    private ToggleGroup toggleGroup;
    private List<RadioButton> buttonList;

    public void start(){
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root,START_WINDOW_WIDTH,START_WINDOW_HEIGHT);
        Stage stage = new Stage();
        stage.setTitle(DELETE_PANE);
        stage.setScene(scene);
        createSpecifications(root);
        createChooseButton(root);
        stage.showAndWait();
    }

    private void createSpecifications(Pane pane){
        toggleGroup = new ToggleGroup();
        buttonList = new ArrayList<>();
        double initialTopAnchor = INITIAL_TOP_ANCHOR;
        for(SpecificationsEvents specification : SpecificationsEvents.values()){
            RadioButton buttonSpecification = createButtonSpecification(specification,initialTopAnchor);
            pane.getChildren().add(buttonSpecification);
            buttonList.add(buttonSpecification);
            initialTopAnchor+=30;
        }
    }

    private RadioButton createButtonSpecification(SpecificationsEvents specificationsEvents,double topAnchor){
        RadioButton specification = new RadioButton(specificationsEvents.getSpecificationName());
        specification.setToggleGroup(toggleGroup);
        AnchorPane.setTopAnchor(specification,topAnchor);
        AnchorPane.setLeftAnchor(specification,50d);
        return specification;
    }

    private void createChooseButton(Pane pane){
        Button findButton = new Button();
        findButton.setText(CHOOSE_BUTTON);
        pane.getChildren().add(findButton);
        AnchorPane.setTopAnchor(findButton,240d);
        AnchorPane.setLeftAnchor(findButton,50d);
        findButton.setOnAction(event -> {
            for(RadioButton button : buttonList){
                if (button.isSelected()){
                    SpecificationsEvents specification = SpecificationsEvents.getSpecification(button.getText());
                    new ActionSpecification(new Table(),specification,true).handle(event);
                }
            }
        });
    }
}
