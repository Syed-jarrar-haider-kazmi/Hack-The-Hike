package com.example.hackthehike;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Comparator;

public class Questions {

    static int lastchoice=0;
    static String[] questions = {
            "longest word", "electric chair","Corn is grown","longest muscle","fingerprints","common mirror", "Roof","dot above the letter","sea creature","bright orange"

    };


    static String[][] choices = {
            { "Regards", "Typewriter", "Interstellar", "Infinite" },
            { "Neurologist", "Electrician", "Dentist", "Programmer" },
            { "Asia", "Antarctica", "Africa", "Europe" },
            { "Eyes", "Ears", "Hair", "Tongue" },
            { "Tongue", "Nose", "Ear", "Nail" },
            { "Blue", "Yellow", "Green", "Red" },
            { "Stonehenge", "Plateau of Tibet", "Saif-ul-Malook", "Babusar Top" },
            { "Tittle", "Dot", "Alpha", "Sigma" },
            { "Shark", "Octopus", "Dolphin", "Catfish" },
            { "Barret", "Parrot", "Ferret", "Carrot" },
    };

    static int[] correctChoice = {
            2, 3, 2, 4, 1, 3, 2, 1, 2, 4
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String[] getChoices(String question) {


        for(int i = 0; i < questions.length; i++) {
            if((question.contains(questions[i]))) {
                lastchoice=correctChoice[i];
                return choices[i];
            }
        }
        return null;
    }

    public static int getLastchoice() {
        return lastchoice;
    }
}