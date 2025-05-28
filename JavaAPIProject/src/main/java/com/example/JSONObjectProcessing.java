package com.example;

import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
    
public class JSONObjectProcessing {
public static void main(String[] args) throws Exception {

    Scanner scan = new Scanner(System.in);
    String category = "";
    String input = "";
    String answer = "";
    ArrayList<ArrayList<String>> finalList = new ArrayList<>(); //arraylist of arraylist of pokemon

    while(!answer.equals("q")) { //repeats until you quit

        System.out.println("input the category (move, ability, type, pokemon) followed by a underscore and the information or q to quit. Move, ability, and type can be mixed, but pokemon can't");
        answer = scan.nextLine();

            if (!answer.equals("q")) { //here to not execute the program with "q" as an input 
                category = answer.substring(0, answer.indexOf("_")); //seperate the answer into category and input
                input = answer.substring(answer.indexOf("_") + 1);

                if (category.equals("pokemon")) { //if the category is pokemon, talk all about it instead
                    System.out.println(input + " has the type(s) " + typeHelper.getTyping(input));
                    System.out.println(input + " is weak to " + typeHelper.getTrueWeakness(input));
                    System.out.println(input + " is resistant to " + typeHelper.getTrueResistance(input));
                    ArrayList<String> immune = typeHelper.getImmunity(input);
                    if (immune.size() == 0) {
                        System.out.println(input + " has no immunities");
                    } else {
                        System.out.println(input + " is immune to " + immune);
                    }
                    System.out.println(input + "'s best tera type is " + typeHelper.getTera(input));
                    break;
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


                for (int i = 0; i < mons.length(); i++) { //searches through the entire list to get the names
                    if (category.equals("move")) {
                        JSONObject element = mons.getJSONObject(i); //"move" doesnt require to filter through "pokemon"
                        String name = element.getString("name");
                        if (name.indexOf("-mega") == -1 && name.indexOf("-gmax") == -1) {
                            monList.add(name);
                        }
                    } else {
                        JSONObject element = mons.getJSONObject(i);
                        JSONObject pokemon = element.getJSONObject("pokemon");
                        String name = pokemon.getString("name");
                        if (name.indexOf("-mega") == -1 && name.indexOf("-gmax") == -1) { //exclude megas and g-max
                            monList.add(name);
                        }
                    }
                }

                finalList.add(monList);
            }
        }

    System.out.println(ListUtils.listHelper(finalList));

    scan.close();
    }
}