/*
 * Copyright (c) Moshe Ofer 2024.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
/*
    The Main class
    NO CHANGES NEEDED HERE AT ALL!
 */
public class Main {
    public static final int bord_size = 8;
    public static List<Object> fields = new ArrayList<>();
    public static Queen[] queens = new Queen[bord_size];
    public static int SQUARE_SIZE = 65;
    public static int numberOfSolutions;
    public static JFrame frame = new JFrame();
    public static Label l1;

    public static void main(String[] args) {
        //creating 8 queens on the board
        for (int i = 0; i < bord_size; i++) {
            queens[i] = new Queen(i);
            queens[i].setBounds(700, 520 - (i * SQUARE_SIZE), (int) (0.6 * SQUARE_SIZE), (int) (0.6 * SQUARE_SIZE));
            frame.add(queens[i]);
        }
        Label l = new Label("Possible solutions: ");
        l.setBounds(600, 20, 150, 30);
        l.setFont(new Font("ARIEL", Font.PLAIN, 18));
        frame.add(l);

        // Calculate the solutions and update the label
        numberOfSolutions = Logic.countSolutions(queens);
        l1 = new Label(String.valueOf(numberOfSolutions));
        l1.setBounds(750, 20, 25, 30);
        l1.setForeground(new Color(238, 52, 52));
        l1.setFont(new Font("ARIEL", Font.BOLD, 18));
        frame.add(l1);

        // Creating the board with 64 JButtons
        int fieldCounter = 0;
        for (int i = 0; i < bord_size; i++) {
            for (int j = 0; j < bord_size; j++) {
                fields.add(fieldCounter, new JButton());
                ((JButton) fields.get(fieldCounter)).setBounds(50 + (SQUARE_SIZE * j), 50 + (SQUARE_SIZE * i), SQUARE_SIZE, SQUARE_SIZE);
                if ((i + j) % 2 == 0) ((JButton) fields.get(fieldCounter)).setBackground(new Color(152, 102, 0));
                else ((JButton) fields.get(fieldCounter)).setBackground(Color.white);

                frame.add((Component) fields.get(fieldCounter));
                ((JButton) fields.get(fieldCounter)).setEnabled(false);
                int finalFieldCounter = fieldCounter;

                // When a queen had been pressed, if a button is pressed the queen moves to this button
                ((JButton) fields.get(fieldCounter)).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        //if one player had been pressed
                        if (Queen.queenClicked != -1) {
                            if (Queen.emptySquare(finalFieldCounter)) {
                                queens[Queen.queenClicked].setLocation(((JButton) Main.fields.get(finalFieldCounter)).getX() + Queen.CENTER_A, ((JButton) Main.fields.get(finalFieldCounter)).getY() + Queen.CENTER_A);
                                queens[Queen.queenClicked].setInSquare(finalFieldCounter);
                                int sol = Logic.countSolutions(queens);
                                l1.setText(String.valueOf(sol));
                                frame.add(Main.l1);
                                frame.repaint();
                            }
                            // The image changes to the one without the green line
                            queens[Queen.queenClicked].drawQueen(Queen.queenImg);
                            Queen.queenClicked = -1;
                        }
                    }
                });
                fieldCounter++;
            }
        }

        frame.setSize(800, 650);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
