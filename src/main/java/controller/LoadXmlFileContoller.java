package controller;

import dao.*;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.TrainSheduleRepository;
import model.TrainShedule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadXmlFileContoller  {

    private static final String FILE_ERROR = "An error occurred while opening the file";

    private Stage stage;
    private Dao dao;
    private TrainSheduleRepository repository;

    public LoadXmlFileContoller(Stage stage,TrainSheduleRepository repository){
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

    public List<TrainShedule> getData() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        List<TrainShedule> trainShedules = new ArrayList<>();
        if(file==null){
            return trainShedules;
        }
        try {
            trainShedules = dao.read(file.getPath());
            repository.clear();
            repository.addAll(trainShedules);
            return trainShedules;
        } catch (DaoException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(FILE_ERROR);
            alert.showAndWait();
        }
        return trainShedules;
    }
}
