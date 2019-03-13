package controller;

import model.TrainSheduleRepository;
import model.TrainShedule;

import java.util.List;

public class FindAllRecordsController {

    private TrainSheduleRepository repository;

    public FindAllRecordsController(TrainSheduleRepository repository){
        this.repository = repository;
    }

    public List<TrainShedule> getRecords(){
        return repository.getAll();
    }
}
