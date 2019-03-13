package dao;

import model.TrainShedule;

import java.util.List;

public interface Parser {
    List<TrainShedule> parse(String xmlPath) throws ParseException;
}
