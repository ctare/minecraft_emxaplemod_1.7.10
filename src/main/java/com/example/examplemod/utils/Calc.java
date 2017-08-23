package com.example.examplemod.utils;

import java.util.Random;

/**
 * Created by ctare on 2017/08/23.
 */
public class Calc {
    public static Random random = new Random();
    public static int randint(int max){
       return random.nextInt(max);
    }

    public static int randint(int min, int max){
        return random.nextInt(max) + min;
    }

    public static float random(){
        return random.nextFloat();
    }
}
