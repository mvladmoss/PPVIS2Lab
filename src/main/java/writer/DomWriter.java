package writer;

import parser.ParseException;
import model.TrainShedule;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static parser.XmlElements.*;

public class DomWriter implements XmlWriter {

    private static final String LIST = "list";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private DocumentBuilder builder;

    @Override
    public void write(List<TrainShedule> trainShedules,String path) throws WriteException {
        Document doc = builder.newDocument();
        Element rootElement = doc.createElement(LIST);
        for (TrainShedule trainShedule : trainShedules) {
            Element trainSheduleElement = doc.createElement(TRAIN_SHEDULE.getName());
            Element trainNumberElement = doc.createElement(TRAIN_NUMBER.getName());
            trainNumberElement.appendChild(doc.createTextNode(String.valueOf(trainShedule.getTrain().getId())));
            trainSheduleElement.appendChild(trainNumberElement);
            Element departureStationElement = doc.createElement(DEPATURE_STATION.getName());
            departureStationElement.appendChild(doc.createTextNode(trainShedule.getDepatureStation().getStationName()));
            trainSheduleElement.appendChild(departureStationElement);
            Element arrivalStationElement = doc.createElement(ARRIVAL_STATION.getName());
            arrivalStationElement.appendChild(doc.createTextNode(trainShedule.getArriveStation().getStationName()));
            trainSheduleElement.appendChild(arrivalStationElement);
            Element departureDateElement = doc.createElement(DEPATURE_DATE.getName());
            departureDateElement.appendChild(doc.createTextNode(trainShedule.getDepatureDate().format(dateTimeFormatter)));
            trainSheduleElement.appendChild(departureDateElement);
            Element arrivalDateElement = doc.createElement(ARRIVAL_DATE.getName());
            arrivalDateElement.appendChild(doc.createTextNode(trainShedule.getArrivalDate().format(dateTimeFormatter)));
            trainSheduleElement.appendChild(arrivalDateElement);
            Element travelDurationElement = doc.createElement(TRAVEL_TIME.getName());
            String duration = trainShedule.getDurationStringProperty().getValue();
            duration = duration.replace("m","");
            duration = duration.replace(" ",":");
            duration = duration.replace("h","");
            travelDurationElement.appendChild(doc.createTextNode(duration));
            trainSheduleElement.appendChild(travelDurationElement);
            rootElement.appendChild(trainSheduleElement);
        }
        doc.appendChild(rootElement);

        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.METHOD, "xml");
            t.setOutputProperty(OutputKeys.INDENT, "yes");    t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(path)));
        } catch (FileNotFoundException | TransformerException exception) {
            throw new WriteException(exception);
        }
    }

    public DomWriter() throws ParseException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ParseException(e);
        }
    }
}
