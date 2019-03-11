package repository;

import writer.WriteException;
import model.TrainShedule;
import parser.ParseException;
import parser.Parser;
import writer.XmlWriter;
import java.util.List;

public class XmlRepository implements Repository {

    private Parser parser;
    private XmlWriter writer;

    public XmlRepository(Parser parser, XmlWriter writer){
        this.parser = parser;
        this.writer = writer;
    }

    @Override
    public void save(List<TrainShedule> trainShedules, String xmlPath) throws RepositoryException {
        try {
            writer.write(trainShedules,xmlPath);
        } catch (WriteException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<TrainShedule> read(String xmlPath) throws RepositoryException {
        try {
            return parser.parse(xmlPath);
        } catch (ParseException e) {
            throw new RepositoryException();
        }
    }
}
