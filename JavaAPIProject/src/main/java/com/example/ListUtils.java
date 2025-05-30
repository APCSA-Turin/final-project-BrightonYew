package com.example;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public static ArrayList<String> processFilter (ArrayList<String> filters) throws Exception {
        ArrayList<ArrayList<String>> finalList = new ArrayList<>(); //arraylist of arraylist of pokemon
        for (int i = 0; i < filters.size(); i++) { //for each filter
            String filter = filters.get(i); //get the singular filter
            String category = filter.substring(0, filter.indexOf("_")); //seperate the answer into category and input
            String input = filter.substring(filter.indexOf("_") + 1);

            if (category.equals("pokemon")) { //if the category is pokemon, talk all about it instead
                ArrayList<String> monStuff = new ArrayList<>();
                monStuff.add(input);
                monStuff.add("type(s): " + typeHelper.getTyping(input));
                monStuff.add("Weak to " + typeHelper.getTrueWeakness(input));
                monStuff.add("Resistant to " + typeHelper.getTrueResistance(input));
                ArrayList<String> immune = typeHelper.getImmunity(input);
                if (immune.size() == 0) {
                    monStuff.add("Has no immunities");
                } else {
                    monStuff.add("Immune to " + immune);
                }
                monStuff.add("The best tera type is " + typeHelper.getTera(input));
                return monStuff;
            }

            String jsonString = API.getData("https://pokeapi.co/api/v2/" + category + "/" + input);

            JSONObject obj = new JSONObject(jsonString);
            JSONArray mons;
            if (category.equals("move")) { //here to call different keys for the different categories
                mons = obj.getJSONArray("learned_by_pokemon");  //it is not getstring because the brackets are arrays, not strings
            } else {
                mons = obj.getJSONArray("pokemon");
            }
            ArrayList<String> monList = new ArrayList<String>(); //list of all the pokemon fitting ONE category


            for (int j = 0; j < mons.length(); j++) { //searches through the entire list to get the names
                if (category.equals("move")) {
                    JSONObject element = mons.getJSONObject(j); //"move" doesnt require to filter through "pokemon"
                    String name = element.getString("name");
                    if (name.indexOf("-mega") == -1 && name.indexOf("-gmax") == -1) {
                        monList.add(name);
                    }
                } else {
                    JSONObject element = mons.getJSONObject(j);
                    JSONObject pokemon = element.getJSONObject("pokemon");
                    String name = pokemon.getString("name");
                    if (name.indexOf("-mega") == -1 && name.indexOf("-gmax") == -1) { //exclude megas and g-max
                        monList.add(name);
                    }
                }
            }

            finalList.add(monList);
        }

        return ListUtils.listHelper(finalList);

        }
    }