package model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainTrainSheduleRepository {

    private static MainTrainSheduleRepository instance;
    private List<TrainShedule> trainSheduleList;

    public static MainTrainSheduleRepository getInstance() {
        if(instance==null){
            instance = new MainTrainSheduleRepository();
        }
        return instance;
    }

    private MainTrainSheduleRepository(){
        trainSheduleList = new ArrayList<>();
    }

    public void add(TrainShedule object) {
        trainSheduleList.add(object);
    }

    public void addAll(List<TrainShedule> objectList) {
        trainSheduleList.addAll(objectList);
    }

    public void remove(TrainShedule trainShedule) {
        trainSheduleList.remove(trainShedule);
    }

    public void removeAll(List<TrainShedule> sheduleList){
        trainSheduleList.removeAll(sheduleList);
    }

    public List<TrainShedule> query(Predicate<TrainShedule> specification) {
        return trainSheduleList.stream()
                .filter(specification)
                .collect(Collectors.toList());
    }

    public List<TrainShedule> getAll() {
        return trainSheduleList;
    }

    public void clear(){
        trainSheduleList.clear();
    }
}
