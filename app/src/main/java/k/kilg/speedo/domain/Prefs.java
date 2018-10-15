package k.kilg.speedo.domain;

public enum Prefs {

    MEASURE_KEY("pref_measure"),
    PROVIDER_KEY("pref_provider"),
    MIN_TIME_KEY("pref_min_time"),
    DISTANCE_KEY("pref_distance");

    private final String value;

    Prefs (final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }

    public static Prefs getFieldValue(String currentValue) {
        switch (currentValue) {
            case "pref_measure":
                return MEASURE_KEY;
            case "pref_provider":
                return PROVIDER_KEY;
            case "pref_min_time":
                return MIN_TIME_KEY;
            case "pref_distance":
                return DISTANCE_KEY;
            default:
                return null;
        }
    }

    public static String getDefault(Prefs key) {
        switch (key)  {
            case MEASURE_KEY:
                return MeasureValue.getDefault();
            case PROVIDER_KEY:
                return ProviderValue.getDefault();
            case MIN_TIME_KEY:
                return MinTimeValue.getStringDefault();
            case DISTANCE_KEY:
                return DistanceValue.getStringDefault();
            default:
                return "";
        }
    }

    public enum MeasureValue {
        KMH("valueKmH"),
        MS("valueMS");

        private final String value;

        MeasureValue(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }

        // TODO: 19.08.18 какая-то хрень, как иначе получить поле енума, а не его значение????
        public static MeasureValue getFieldValue(String currentValue) {
            switch (currentValue) {
                case "valueKmH":
                    return KMH;
                case "valueMS":
                    return MS;
                default:
                    return KMH;
            }
        }

        public static String getDefault() {
            return KMH.getValue();
        }
    }

    public enum ProviderValue {
        GPS("valueGps"),
        NETWORK("valueNetwork"),
        BOTH("valueBoth");

        private final String value;

        ProviderValue(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }

        public static ProviderValue getFieldValue(String currentValue) {
            switch (currentValue) {
                case "valueGps":
                    return GPS;
                case "valueNetwork":
                    return NETWORK;
                case "valueBoth":
                    return BOTH;
                default:
                    return BOTH;
            }
        }

        public static String getDefault() {
            return BOTH.name();
        }
    }

    public enum MinTimeValue {
        value0(0),
        value5(5),
        value10(10),
        value30(30),
        value60(60);

        private final long value;

        MinTimeValue(final long newValue) {
            value = newValue;
        }

        public long getValue() {
            return value;
        }

        public static MinTimeValue getFieldValue(String currentValue) {
            switch (currentValue) {
                case "0":
                    return value0;
                case "5":
                    return value5;
                case "10":
                    return value10;
                case "30":
                    return value30;
                case "60":
                    return value60;
                default:
                    return value0;

            }
        }

        public static long getLongDefault() {
            return value0.getValue();
        }

        public static String getStringDefault() {
            return String.valueOf(value0.name());
        }
    }

    public enum DistanceValue {
        value0(0),
        value5(5),
        value10(10),
        value30(30),
        value60(60);

        private final int value;

        DistanceValue(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public static DistanceValue getFieldValue(String currentValue) {
            switch (currentValue) {
                case "0":
                    return value0;
                case "5":
                    return value5;
                case "10":
                    return value10;
                case "30":
                    return value30;
                case "60":
                    return value60;
                default:
                    return value0;

            }
        }

        public static int getIntDefault() {
            return value0.getValue();
        }

        public static String getStringDefault() {
            return String.valueOf(value0.name());
        }
    }
}