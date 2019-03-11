package controller;

import repository.RepositoryException;
import model.TrainShedule;
import parser.ParseException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import parser.SaxParser;
import repository.Repository;
import repository.XmlRepository;
import view.Table;
import writer.DomWriter;
import java.io.File;
import java.util.List;

public class SaveRecordsInXmlEvent implements EventHandler {

    private static final String FILE_ERROR = "An error occurred while saving the file";
    private Stage stage;
    private Repository repository;
    private Table table;

    public SaveRecordsInXmlEvent(Stage stage,Table table){
        this.stage = stage;
        this.table = table;
        try {
            this.repository = new XmlRepository(new SaxParser(),new DomWriter());
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(FILE_ERROR);
            alert.showAndWait();
        }
    }

    @Override
    public void handle(Event event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file==null){
            return;
        }
        List<TrainShedule> sheduleList = table.getPagination().getAllTrainShedules();
        try {
            repository.save(sheduleList,file.getPath());
        } catch (RepositoryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(FILE_ERROR);
            alert.showAndWait();
        }
    }
}
