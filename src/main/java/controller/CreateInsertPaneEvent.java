package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import view.InsertRecordPane;
import view.Table;

public class CreateInsertPaneEvent implements EventHandler {

    private Table table;

    public CreateInsertPaneEvent(Table table){
        this.table = table;
    }

    @Override
    public void handle(Event event) {
        new InsertRecordPane(table).start();
    }
}
