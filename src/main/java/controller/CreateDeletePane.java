package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import view.DeleteRecordPane;

public class CreateDeletePane implements EventHandler {

    @Override
    public void handle(Event event) {
        new DeleteRecordPane().start();
    }

}
