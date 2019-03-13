package dao;

import model.TrainShedule;
import java.util.List;

public interface XmlWriter {
    void write(List<TrainShedule> trainShedules, String path) throws WriteException;
}
