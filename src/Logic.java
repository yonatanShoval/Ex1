/*
 * Copyright (c) Moshe Ofer 2024.
 */

public class Logic {

    public static int solutionsCounter = 0;
    public static boolean[][] bord;

    public static void printBoard(boolean[][] bord) {
        /*
         TODO: Fill this function
         */
    }
    public static boolean checkBoard(int raw, int column, boolean[][] bord) {
        /*
         TODO: Fill this function
         */
        return false;
    }
    public static void placeQueensRecursive(int column, boolean[][] bord) {
        /*
         TODO: Fill this function
         */
    }


    public static int countSolutions(Queen[] queens) {
        solutionsCounter = 0;
        bord = new boolean[Main.bord_size][Main.bord_size];
        for (int i = 0; i < Main.bord_size; i++) {
            int s = queens[i].getInSquare();
            if (s != -1){
                bord[s / Main.bord_size][s % Main.bord_size] = true;
            }
        }
        placeQueensRecursive(0, bord);
        return solutionsCounter;
    }
}
