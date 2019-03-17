package com.chad.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static javax.swing.UIManager.put;

public class ValidWords {
    private static List<String> ValidList = Collections.unmodifiableList(
            new ArrayList<>(Arrays.asList(
                    "how",
                    "many",
                    "much",
                    "is",
                    "Credits",
                    "?"
            )));

    public static boolean iscontains(String word){
        return ValidList.contains(word);
    }

}
