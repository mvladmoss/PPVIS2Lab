package repository;

import model.TrainShedule;

import java.util.List;

public interface Repository {

    void save(List<TrainShedule> trainShedules, String xmlPath) throws  RepositoryException;
    List<TrainShedule> read(String xmlPath) throws RepositoryException;
}
