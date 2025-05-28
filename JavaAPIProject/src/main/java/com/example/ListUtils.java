package com.example;

import java.util.ArrayList;

public class ListUtils {
    public static ArrayList<String> listHelper(ArrayList<ArrayList<String>> listOfLists) {
        ArrayList<String> result = new ArrayList<>();

        if (listOfLists == null || listOfLists.isEmpty()) {
            return result;
        }

        // Use the first list to get candidate elements
        for (String element : listOfLists.get(0)) {
            boolean foundInAll = true;

            // Check if the element exists in all other lists
            for (int i = 1; i < listOfLists.size(); i++) {
                if (!listOfLists.get(i).contains(element)) {
                    foundInAll = false;
                }
            }

            // Add to result only if not already added and found in all
            if (foundInAll && !result.contains(element)) {
                result.add(element);
            }
        }

        return result;
    }
}