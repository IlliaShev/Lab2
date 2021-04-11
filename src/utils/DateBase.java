package utils;

import dto.Product;
import java.util.HashMap;

public class DateBase {

    private String path;
    private final HashMap<String, HashMap<String, Product>> product = new HashMap<>();


    public DateBase(String path) {
        this.path = path;
    }
}

