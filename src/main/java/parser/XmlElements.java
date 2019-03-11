package parser;

public enum XmlElements {
        TRAIN_SHEDULE("train_shedule"),
        TRAIN_NUMBER("train_number"),
        DEPATURE_STATION("depature_station"),
        ARRIVAL_STATION("arrival_station"),
        DEPATURE_DATE("depature_date"),
        ARRIVAL_DATE("arrival_date"),
        TRAVEL_TIME("travel_time");

        private String name;

        XmlElements(String name){
            this.name = name;
        }

    public String getName() {
        return name;
    }

    public static boolean contains(String test) {
                for (XmlElements element : XmlElements.values()) {
                        if (element.name().equals(test)) {
                                return true;
                        }
                }
                return false;
        }

}
