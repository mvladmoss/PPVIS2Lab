package model;


public enum SpecificationsEvents {

    TRAIN_NUMBER("Train number"),
    DEPARTURE_DATE("Departure date"),
    DEPARTURE_TIME("Departure time"),
    ARRIVAL_DATE("Arrival date"),
    ARRIVAL_TIME("Arrival time"),
    DEPARTURE_STATION("Departure station"),
    ARRIVAL_STATION("Arrival station"),
    TRAVEL_TIME("Travle time");

    private String specificationName;

    private SpecificationsEvents(String specificationName){
        this.specificationName = specificationName;
    }

    public  String getSpecificationName() {
        return specificationName;
    }

    public static SpecificationsEvents getSpecification(String text){
        for(SpecificationsEvents specification : values()){
            if (specification.getSpecificationName().equals(text)){
                return specification;
            }
        }
        return TRAIN_NUMBER;
    }

}
