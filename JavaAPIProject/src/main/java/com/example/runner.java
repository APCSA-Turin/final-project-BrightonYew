package com.example;

public class runner {
    public static void main(String[] args) throws Exception {
        //System.out.println(typeHelper.getWeakness("charizard"));
        //System.out.println(typeHelper.getTera("charizard"));
        //System.out.println(typeHelper.getWeakness("gholdengo"));
        //System.out.println(typeHelper.getResistance("gholdengo"));
        //System.out.println(typeHelper.getImmunity("gholdengo"));
        String[][] chart = typeHelper.getTypeChart("zacian-crowned");
        for (int i = 0; i < 18; i++) {
            System.out.println(chart[0][i] + " " + chart[1][i] );
        }

        //System.out.println(typeHelper.getTrueResistance("gholdengo"));
        //System.out.println(typeHelper.getTrueWeakness("gholdengo"));
    }
}