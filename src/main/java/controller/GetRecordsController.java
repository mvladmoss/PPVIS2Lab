package controller;

import model.MainTrainSheduleRepository;
import model.TrainShedule;

import java.util.List;

public class GetRecordsController {

    public List<TrainShedule> getRecords(){
        MainTrainSheduleRepository repository = MainTrainSheduleRepository.getInstance();
        return repository.getAll();
    }
}
