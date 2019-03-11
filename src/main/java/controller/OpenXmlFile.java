package controller;

import repository.RepositoryException;
import model.Pagination;
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

public class OpenXmlFile implements EventHandler {

    private static final String FILE_ERROR = "An error occurred while opening the file";
    private Stage stage;
    private Repository repository;
    private Table table;

    public OpenXmlFile(Stage stage, Table table){
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
        try {
            Pagination pagination = table.getPagination();
            pagination.getAvaibleTrainShedules().clear();
            pagination.getAllTrainShedules().clear();
            pagination.setAllTrainShedules(repository.read(file.getPath()));
            pagination.setFirstPage();
            pagination.setPagesNumber(table.getAllPagesText());
            pagination.setStartPageNumber(table.getCurrentPageText());
            table.setTotalRecordsNumber(pagination.getElementsCount());
        } catch (RepositoryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(FILE_ERROR);
            alert.showAndWait();
        }
    }
}
