package model;

import java.util.ArrayList;
import java.util.List;

public class TrainSheduleRepository {

    private List<TrainShedule> trainSheduleList;

    public TrainSheduleRepository(){
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


    public List<TrainShedule> getAll() {
        return trainSheduleList;
    }

    public void clear(){
        trainSheduleList.clear();
    }
}
