package view;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import model.TrainShedule;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Pagination {

    private static final String NO_MORE_PAGES = "There is no more pages";
    private static final String START_PAGES_NUMBER = "1";

    private int visibleCount = 5;
    private List<TrainShedule> allTrainShedules;
    private ObservableList<TrainShedule> avaibleTrainShedules;
    private int numberOfPages;
    private int selectedPage;
    private int elementsCount;

     Pagination(){
        this.allTrainShedules = new ArrayList<>();
    }

     void setFirstPage(){
        if(allTrainShedules.isEmpty()){
            this.selectedPage = 0;
            return;
        }
        this.selectedPage = 1;
        avaibleTrainShedules.clear();
        if(elementsCount < visibleCount){
            avaibleTrainShedules.addAll(allTrainShedules.subList(0,elementsCount));
        }else{
            avaibleTrainShedules.addAll(allTrainShedules.subList(0, visibleCount));
        }
        System.out.println(avaibleTrainShedules.size());
    }

     void setLastPage(){
        if(numberOfPages==1){
            return;
        }
        avaibleTrainShedules.clear();
        int notFullRecordsNumber = (elementsCount)% visibleCount;
        int lastElementIndex = allTrainShedules.size();
        if(notFullRecordsNumber==0){
            avaibleTrainShedules.addAll(allTrainShedules.subList(lastElementIndex- visibleCount,lastElementIndex));
        }else{
            avaibleTrainShedules.addAll(allTrainShedules.subList(lastElementIndex-notFullRecordsNumber,lastElementIndex));
        }
        selectedPage = numberOfPages;
    }

     void setNextPage(){
        int nextRecordIndex = selectedPage* visibleCount;
        if(nextRecordIndex> allTrainShedules.size() || elementsCount==0 || numberOfPages==1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(NO_MORE_PAGES);
            alert.showAndWait();
            return;
        }
        int lastAvaibleRecordIndex = nextRecordIndex + visibleCount;
        if(allTrainShedules.size() < lastAvaibleRecordIndex){
            lastAvaibleRecordIndex = allTrainShedules.size();
        }
        avaibleTrainShedules.clear();
        avaibleTrainShedules.addAll(allTrainShedules.subList(nextRecordIndex, lastAvaibleRecordIndex));
        selectedPage++;
    }

     void setPrevoisPage(){
        if((selectedPage-1) <= 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(NO_MORE_PAGES);
            alert.showAndWait();
            return;
        }
        int previosRecordAvaible = (selectedPage-1)==0? 0 : (selectedPage-2)* visibleCount;
        avaibleTrainShedules.clear();
        avaibleTrainShedules.addAll(allTrainShedules.subList(previosRecordAvaible, previosRecordAvaible+ visibleCount));
        selectedPage--;
    }

     void setAllTrainShedules(List<TrainShedule> allTrainShedules) {
        this.elementsCount = allTrainShedules.size();
        this.numberOfPages =  elementsCount== visibleCount ? 1 : elementsCount/ visibleCount + 1;
        this.allTrainShedules = allTrainShedules;
    }

     void setAvaibleTrainShedules(ObservableList<TrainShedule> avaibleTrainShedules) {
        this.avaibleTrainShedules = avaibleTrainShedules;
    }

     void setPagesNumber(Text text){
        text.setText(String.valueOf(numberOfPages));
    }

     void setStartPageNumber(Text text){
        text.setText(START_PAGES_NUMBER);
    }

     void recalculateNumberOfPages(){
        numberOfPages = elementsCount%visibleCount==0?elementsCount/visibleCount : elementsCount/visibleCount+1;
    }

     void addNewRecord(TrainShedule trainShedule){
        allTrainShedules.add(trainShedule);
        elementsCount++;
        recalculateNumberOfPages();
        if(avaibleTrainShedules.size()!= visibleCount){
            avaibleTrainShedules.add(trainShedule);
        }
    }

    void refresh(List<TrainShedule> shedules){
        getAllTrainShedules().clear();
        getAvaibleTrainShedules().clear();
        setAllTrainShedules(shedules);
        recalculateNumberOfPages();
        setFirstPage();
    }
}
