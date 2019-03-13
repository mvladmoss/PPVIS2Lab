package dao;

import model.TrainShedule;

import java.util.List;

public class XmlDao implements Dao {

    private Parser parser;
    private XmlWriter writer;

    public XmlDao(Parser parser, XmlWriter writer){
        this.parser = parser;
        this.writer = writer;
    }

    @Override
    public void save(List<TrainShedule> trainShedules, String xmlPath) throws DaoException {
        try {
            writer.write(trainShedules,xmlPath);
        } catch (WriteException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<TrainShedule> read(String xmlPath) throws DaoException {
        try {
            return parser.parse(xmlPath);
        } catch (ParseException e) {
            throw new DaoException();
        }
    }
}
