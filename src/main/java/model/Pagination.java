package model;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
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

    public Pagination(){
        this.allTrainShedules = new ArrayList<>();
    }

    public void setFirstPage(){
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

    public void setLastPage(){
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

    public void setNextPage(){
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

    public void setPrevoisPage(){
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

    public void setAllTrainShedules(List<TrainShedule> allTrainShedules) {
        this.elementsCount = allTrainShedules.size();
        this.numberOfPages =  elementsCount== visibleCount ? 1 : elementsCount/ visibleCount + 1;
        this.allTrainShedules = allTrainShedules;
    }

    public void setAvaibleTrainShedules(ObservableList<TrainShedule> avaibleTrainShedules) {
        this.avaibleTrainShedules = avaibleTrainShedules;
    }

    public void setPagesNumber(Text text){
        text.setText(String.valueOf(numberOfPages));
    }

    public void setStartPageNumber(Text text){
        text.setText(START_PAGES_NUMBER);
    }

    public void recalculateNumberOfPages(){
        numberOfPages = elementsCount%visibleCount==0?elementsCount/visibleCount : elementsCount/visibleCount+1;
    }

    public void addNewRecord(TrainShedule trainShedule){
        allTrainShedules.add(trainShedule);
        elementsCount++;
        recalculateNumberOfPages();
        if(avaibleTrainShedules.size()!= visibleCount){
            avaibleTrainShedules.add(trainShedule);
        }
    }
}
