package com.example;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class typeHelper {
    public static String getTyping (String mon) throws Exception {
        String type1 = "";
        String type2 = "";
        String jsonString = API.getData("https://pokeapi.co/api/v2/pokemon" + "/" + mon);
        JSONObject obj = new JSONObject(jsonString);

        JSONArray typeList = obj.getJSONArray("types"); //gets the array, containing the info for the types
        
        JSONObject element1 = typeList.getJSONObject(0); //access one of the 2 arrays
        JSONObject test1 = element1.getJSONObject("type"); //access the "type" json, after "slot" WHY IS NAME SO HIIDEN AWAY
        type1 = test1.getString("name"); //acces the type's name

        if (typeList.length() == 2) { //only get the second type if there are 2 types
            JSONObject element2 = typeList.getJSONObject(1);
            JSONObject test2 = element2.getJSONObject("type");
            type2 = test2.getString("name");
        }

        return type1 + " " + type2;
    }

    public static ArrayList<String> getWeakness (String mon) throws Exception {
        String types = getTyping(mon);
        String type1 = types.substring(0, types.indexOf(" "));
        String type2 = types.substring(types.indexOf(" ") + 1);
        ArrayList<String> weaknessList = new ArrayList<String>();
  

        String jsonString = API.getData("https://pokeapi.co/api/v2/type/" + type1);
        JSONObject obj = new JSONObject(jsonString);
        JSONObject test = obj.getJSONObject("damage_relations");
        JSONArray arr = test.getJSONArray("double_damage_from");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject element = arr.getJSONObject(i);
            String type = element.getString("name"); //acces the type's name
            weaknessList.add(type);
        }

        if (!type2.equals("")) { //if there is a second type
            String jsonString2 = API.getData("https://pokeapi.co/api/v2/type/" + type2);
            JSONObject obj2 = new JSONObject(jsonString2);
            JSONObject test2 = obj2.getJSONObject("damage_relations");
            JSONArray arr2 = test2.getJSONArray("double_damage_from");

            for (int i = 0; i < arr2.length(); i++) {
                JSONObject element = arr2.getJSONObject(i);
                String type = element.getString("name"); //acces the type's name
                weaknessList.add(type);
            }
        }

        return weaknessList;
    }


        public static ArrayList<String> getResistance (String mon) throws Exception { //just getweakness but copy and pasted with "half_damage_from" instead
        String types = getTyping(mon);
        String type1 = types.substring(0, types.indexOf(" "));
        String type2 = types.substring(types.indexOf(" ") + 1);
        ArrayList<String> resistanceList = new ArrayList<String>();
  

        String jsonString = API.getData("https://pokeapi.co/api/v2/type/" + type1);
        JSONObject obj = new JSONObject(jsonString);
        JSONObject test = obj.getJSONObject("damage_relations");
        JSONArray arr = test.getJSONArray("half_damage_from");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject element = arr.getJSONObject(i);
            String type = element.getString("name"); //access the type's name
            resistanceList.add(type);
        }

        if (!type2.equals("")) { //if there is a second type
            String jsonString2 = API.getData("https://pokeapi.co/api/v2/type/" + type2);
            JSONObject obj2 = new JSONObject(jsonString2);
            JSONObject test2 = obj2.getJSONObject("damage_relations");
            JSONArray arr2 = test2.getJSONArray("half_damage_from");

            for (int i = 0; i < arr2.length(); i++) {
                JSONObject element = arr2.getJSONObject(i);
                String type = element.getString("name"); //acces the type's name
                resistanceList.add(type);
            }
        }

        return resistanceList;
    }

        public static ArrayList<String> getImmunity (String mon) throws Exception { //just getweakness but copy and pasted with "no_damage_from" instead
        String types = getTyping(mon);
        String type1 = types.substring(0, types.indexOf(" "));
        String type2 = types.substring(types.indexOf(" ") + 1);
        ArrayList<String> immunityList = new ArrayList<String>();
  

        String jsonString = API.getData("https://pokeapi.co/api/v2/type/" + type1);
        JSONObject obj = new JSONObject(jsonString);
        JSONObject test = obj.getJSONObject("damage_relations");
        JSONArray arr = test.getJSONArray("no_damage_from");

        for (int i = 0; i < arr.length(); i++) {
            JSONObject element = arr.getJSONObject(i);
            String type = element.getString("name"); //access the type's name
            immunityList.add(type);
        }

        if (!type2.equals("")) { //if there is a second type
            String jsonString2 = API.getData("https://pokeapi.co/api/v2/type/" + type2);
            JSONObject obj2 = new JSONObject(jsonString2);
            JSONObject test2 = obj2.getJSONObject("damage_relations");
            JSONArray arr2 = test2.getJSONArray("no_damage_from");

            for (int i = 0; i < arr2.length(); i++) {
                JSONObject element = arr2.getJSONObject(i);
                String type = element.getString("name"); //acces the type's name
                immunityList.add(type);
            }
        }

        return immunityList;
    }

    public static ArrayList<String> getTrueWeakness (String mon) throws Exception { //gets the weaknesses considering resistances
        ArrayList<String> weakness = new ArrayList<>();
        String[][] chart = getTypeChart(mon);
        for (int i = 0; i < chart[0].length; i++) { //for each element, if the associated type has the values 2 or 4, add it to the list
            if (chart[1][i].equals("2.0") || chart[1][i].equals("4.0")) {
                weakness.add(chart[0][i]);
            }
        }
        return weakness;
    }

        public static ArrayList<String> getTrueResistance (String mon) throws Exception { //gets the resistances considering weaknesses
        ArrayList<String> resistance = new ArrayList<>();
        String[][] chart = getTypeChart(mon);
        for (int i = 0; i < chart[0].length; i++) {//for each element, if the associated type has the values 0.5 or 0.25, add it to the list
            if (chart[1][i].equals("0.5") || chart[1][i].equals("0.25")) {
                resistance.add(chart[0][i]);
            }
        }
        return resistance;
    }


        public static String getTera (String mon) throws Exception {
            ArrayList<String> weaknessList = getTrueWeakness(mon);
            int bestScore = 0;
            String bestType = "";
            for (int i = 1; i < 19; i++) { //searches through each type
                ArrayList<String> resistanceList = new ArrayList<>();
                int score = 0;
                String jsonString = API.getData("https://pokeapi.co/api/v2/type/" + i); //for resistances
                JSONObject obj = new JSONObject(jsonString);
                JSONObject test = obj.getJSONObject("damage_relations");
                JSONArray arr = test.getJSONArray("half_damage_from");

                for (int j = 0; j < arr.length(); j++) { //makes a list of all the resistances
                    JSONObject element = arr.getJSONObject(j);
                    String type = element.getString("name"); //acces the type's name
                    resistanceList.add(type);
                }

                JSONArray arr2 = test.getJSONArray("no_damage_from"); //for immunities

                for (int j = 0; j < arr2.length(); j++) { //makes a list of all the resistances
                    JSONObject element = arr2.getJSONObject(j);
                    String type = element.getString("name"); //acces the type's name
                    resistanceList.add(type);
                }


                for (int j = 0; j < weaknessList.size(); j++) { //goes through the arrays, and if a weakness and a resistance match, increase the score
                    if (resistanceList.contains(weaknessList.get(j))) {
                        score++;
                    }
                }
                if (score > bestScore) { //if the current score is better than the recored score, replace it
                    bestScore = score;
                    bestType = obj.getString("name");
                }
            }
            return bestType;
        }

        public static String[][] getTypeChart (String mon) throws Exception {
            String[][] typeChart = new String[2][18];

            for (int i = 0; i < 18; i++) {
                String jsonString = API.getData("https://pokeapi.co/api/v2/type/" + (i + 1));
                JSONObject obj = new JSONObject(jsonString);
                String name = obj.getString("name");
                //System.out.println(name);
                typeChart[0][i] = name;
                typeChart[1][i] = "1";
            }

            ArrayList<String> weakness = getWeakness(mon);
            ArrayList<String> resistence = getResistance(mon);
            ArrayList<String> immunity = getImmunity(mon);

            for (int i = 0; i < weakness.size(); i++) { //for each weakness
                int index = 0;;
                for (int j = 0; j < 18; j++) {
                    if (typeChart[0][j].equals(weakness.get(i))) { //if it matches the weakness
                        index = j;
                    }
                }
                typeChart[1][index] = Double.toString(Double.parseDouble(typeChart[1][index]) * 2); //converts the "number" to a double. does the calculation, and then converts it back to a string to be stored
            }

            for (int i = 0; i < resistence.size(); i++) { //for each resistance
                int index = 0;;
                for (int j = 0; j < 18; j++) {
                    if (typeChart[0][j].equals(resistence.get(i))) { //if it matches the resistence
                        index = j;
                    }
                }
                typeChart[1][index] = Double.toString(Double.parseDouble(typeChart[1][index]) * 0.5);
            }
            
            
            for (int i = 0; i < immunity.size(); i++) { //for each immunity
                int index = 0;;
                for (int j = 0; j < 18; j++) {
                    if (typeChart[0][j].equals(immunity.get(i))) { //if it matches the immunity
                        index = j;
                    }
                }
                typeChart[1][index] = Double.toString(Double.parseDouble(typeChart[1][index]) * 0);
            }


            return typeChart;
        }
    }
