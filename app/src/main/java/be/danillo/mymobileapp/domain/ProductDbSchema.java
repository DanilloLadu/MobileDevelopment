package be.danillo.mymobileapp.domain;

public class ProductDbSchema {
    public static final class dbTables {
        public static final String NAMEPRODUCT = "Products";
        public static final String NAMEINGREDIENTS = "Ingredients";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String AMMOUNT = "ammount";
        }
    }

}
