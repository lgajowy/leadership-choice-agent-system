package com.pl.leadership_choice.proof_of_concept;


import com.pl.leadership_choice.proof_of_concept.infrastructure.JadeBootThread;

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
