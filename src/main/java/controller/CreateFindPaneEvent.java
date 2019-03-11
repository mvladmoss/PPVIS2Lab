package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import view.FindRecordPane;

public class CreateFindPaneEvent implements EventHandler {


    @Override
    public void handle(Event event) {
        new FindRecordPane().start();
    }
}
