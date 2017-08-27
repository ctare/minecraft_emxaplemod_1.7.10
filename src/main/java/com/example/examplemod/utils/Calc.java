package com.example.examplemod.utils;

import java.util.Random;

/**
 * Created by ctare on 2017/08/23.
 */
public final class Calc {
    private Calc(){}

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

    public static abstract class Counter {
        private int count;
        private final int INTERVAL;

        public Counter(int INTERVAL) {
            this.INTERVAL = INTERVAL;
        }

        public final void update(){
            if(count++ >= INTERVAL){
                action();
                count = 0;
            }
        }

        public final void restart(){
            count = 0;
        }

        public abstract void action();
    }
}
