package dao;

import model.Station;
import model.Train;
import model.TrainShedule;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SaxParser extends DefaultHandler implements Parser {

    private SAXParser saxParser;
    private XmlElements currentTag;
    private TrainShedule currentTrainShedule;
    private List<TrainShedule> trainShedules;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public List<TrainShedule> parse(String path) throws ParseException {
        createParser();
        trainShedules = new ArrayList<>();
        try {
            saxParser.parse(path,this);
        } catch (SAXException | IOException e) {
            throw new ParseException("Error while parsing file:" + path,e);

        }
        return trainShedules;
    }

    private void createParser() throws ParseException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new ParseException("Error while creating Sax Parser");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String tagName = qName.toUpperCase();
        if(XmlElements.contains(tagName)){
            currentTag = XmlElements.valueOf(tagName);
            switch (currentTag){
                case TRAIN_SHEDULE:{
                    currentTrainShedule = new TrainShedule();
                    trainShedules.add(currentTrainShedule);
                    break;
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if(currentTag!=null){
            switch (currentTag){
                case TRAIN_NUMBER:{
                    String trainNumberString = new String(ch,start,length);
                    int trainNumber = Integer.parseInt(trainNumberString);
                    currentTrainShedule.setTrain(new Train(trainNumber));
                    break;
                }
                case DEPATURE_STATION:{
                    String depatureStation = new String(ch,start,length);
                    currentTrainShedule.setDepatureStation(new Station(depatureStation));
                    break;
                }
                case ARRIVAL_STATION:{
                    String arrivalStation = new String(ch,start,length);
                    currentTrainShedule.setArriveStation(new Station(arrivalStation));
                    break;
                }
                case DEPATURE_DATE:{
                    String departureDateString = new String(ch,start,length);
                    LocalDateTime depatureDate = LocalDateTime.parse(departureDateString, dateTimeFormatter);
                    currentTrainShedule.setDepatureDate(depatureDate);
                    break;
                }
                case ARRIVAL_DATE:{
                    String arrivalDateString = new String(ch,start,length);
                    LocalDateTime arrivalDate = LocalDateTime.parse(arrivalDateString, dateTimeFormatter);
                    currentTrainShedule.setArrivalDate(arrivalDate);
                    break;
                }

                case TRAVEL_TIME:{
//                    java.text
                    String travelTimeString = new String(ch,start,length);
                    String[] parameters = travelTimeString.split(":");
                    int hours = Integer.parseInt(parameters[0]);
                    int minutes = Integer.parseInt(parameters[1]);
                    Duration duration = Duration.ofHours(hours);
                    duration = duration.plusMinutes(minutes);
                    currentTrainShedule.setTravelDuration(duration);
                    break;
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        currentTag=null;
    }
}
