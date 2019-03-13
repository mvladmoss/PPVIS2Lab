package controller;

import dao.DaoException;
import model.TrainSheduleRepository;
import model.TrainShedule;
import dao.ParseException;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import dao.SaxParser;
import dao.Dao;
import dao.XmlDao;
import dao.DomWriter;
import java.io.File;
import java.util.List;

public class SaveRecordsController{

    private static final String FILE_ERROR = "An error occurred while saving the file";
    private Stage stage;
    private Dao dao;
    private TrainSheduleRepository repository;

    public SaveRecordsController(Stage stage,TrainSheduleRepository repository){
        this.stage = stage;
        this.repository = repository;
        try {
            this.dao = new XmlDao(new SaxParser(),new DomWriter());
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(FILE_ERROR);
            alert.showAndWait();
        }
    }

    public void save() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if(file==null){
            return;
        }
        List<TrainShedule> sheduleList = repository.getAll();
        try {
            dao.save(sheduleList,file.getPath());
        } catch (DaoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(FILE_ERROR);
            alert.showAndWait();
        }
    }
}
