package com.pl.leadership_choice.application_launching_utils;


/**
 * Created by lukasz on 17.01.15.
 */
public class Main {
    public static void main(String[] args) {
        try {
            new JadeBootThread().run();
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
