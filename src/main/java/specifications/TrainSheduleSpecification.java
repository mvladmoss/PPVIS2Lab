package specifications;

import model.TrainShedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

public class TrainSheduleSpecification {

    public static Predicate<TrainShedule> findByTrainNumber(long trainNumber){
        return p -> p.getTrain().getId()==trainNumber;
    }

    public static Predicate<TrainShedule> findByDepartureStation(String stationName){
        return p -> p.getDepatureStation().getStationName().equalsIgnoreCase(stationName);
    }

    public static Predicate<TrainShedule> findByArrivalStation(String stationName){
        return p -> p.getArriveStation().getStationName().equalsIgnoreCase(stationName);
    }

    public static Predicate<TrainShedule> findByDepartureDate(LocalDate departureDate){
        return p -> p.getDepatureDate().getYear() == departureDate.getYear()
                    && p.getDepatureDate().getMonth() == departureDate.getMonth()
                    && p.getDepatureDate().getDayOfMonth() == departureDate.getDayOfMonth();
    }

    public static Predicate<TrainShedule> findByArrivalDate(LocalDate arrivalDate){
        return p -> p.getArrivalDate().getYear() == arrivalDate.getYear()
                && p.getArrivalDate().getMonth() == arrivalDate.getMonth()
                && p.getArrivalDate().getDayOfMonth() == arrivalDate.getDayOfMonth();
    }

    public static Predicate<TrainShedule> findByDepartureTimePeriod(LocalTime startTime, LocalTime endTime){

        return p -> ((p.getDepatureDate().getHour() < endTime.getHour())
                    || (p.getDepatureDate().getHour() == endTime.getHour() && p.getDepatureDate().getMinute() <= endTime.getMinute()))
                    && ((p.getDepatureDate().getHour() > startTime.getHour())
                    || (p.getDepatureDate().getHour()==startTime.getHour() && p.getDepatureDate().getMinute()>=startTime.getMinute()));
    }

    public static Predicate<TrainShedule> findByArrivalTimePeriod(LocalTime startTime, LocalTime endTime){
        return p -> ((p.getArrivalDate().getHour() < endTime.getHour())
                || (p.getArrivalDate().getHour() == endTime.getHour() && p.getArrivalDate().getMinute() <= endTime.getMinute()))
                && ((p.getArrivalDate().getHour() > startTime.getHour())
                || (p.getArrivalDate().getHour()==startTime.getHour() && p.getArrivalDate().getMinute()>=startTime.getMinute()));
    }

    public static Predicate<TrainShedule> findByTravelTime(Duration travelTime){
        return p -> p.getTravelDuration().toMinutes() == travelTime.toMinutes();
    }
}
