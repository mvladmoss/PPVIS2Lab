package dao;

import model.TrainShedule;

import java.util.List;

public interface Dao {

    void save(List<TrainShedule> trainShedules, String xmlPath) throws DaoException;
    List<TrainShedule> read(String xmlPath) throws DaoException;
}
