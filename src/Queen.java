/*
 * Copyright (c) Moshe Ofer 2024.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;


/*
    The queen class
    NO CHANGES NEEDED HERE AT ALL!
 */
public class Queen extends JComponent {
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;
    public static final int CENTER_A = 13;
    private final int queenNumber;
    private int inSquare;
    public static int queenClicked = -1;
    public static Image queenImg = null;
    public static Image queenPressImg = null;

    public Queen(int queenNumber) {
        this.queenNumber = queenNumber;
        this.inSquare = -1;

        try {
            queenImg = ImageIO.read(new File("src/graphics/q.png"));
            queenPressImg = ImageIO.read(new File("src/graphics/qPress.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        drawQueen(queenImg);

        Image finalQueenImg = queenImg;
        Image finalQueenPressImg = queenPressImg;
        addMouseListener(new MouseListener() {
            //in case the user clicked the queen in order to move her withe clicks
            @Override
            public void mouseClicked(MouseEvent e) {
                //if no other queen pressed
                if (queenClicked ==-1){
                    drawQueen(finalQueenPressImg);
                    queenClicked = getQueenNumber();
                }
                //other queen already pressed
                else{
                    Main.queens[queenClicked].drawQueen(finalQueenImg);
                    setLocation(700,520 - (Queen.this.queenNumber * Main.SQUARE_SIZE));
                    setInSquare(-1);
                    queenClicked =-1;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                screenX = e.getXOnScreen();
                screenY = e.getYOnScreen();

                myX = getX();
                myY = getY();

                // Release the spot on the bord
                if (inSquare>-1){
                    Logic.bord[inSquare / Main.bord_size][inSquare % Main.bord_size] = false;
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                putQueenOnSquare(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }

        });

        // Allowing drag a queen
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //if the user clicked and then dragged
                if (queenClicked !=-1){
                    for (int i = 0; i < Main.bord_size; i++) {
                        Main.queens[i].drawQueen(queenImg);
                    }
                    queenClicked = -1;
                }
                int deltaX = e.getXOnScreen() - screenX;
                int deltaY = e.getYOnScreen() - screenY;

                setLocation(myX + deltaX, myY + deltaY);
            }

            @Override
            public void mouseMoved(MouseEvent e) { }

        });
    }

    // Find the Button that the mouse on it, and replace the queen on this button
    private void putQueenOnSquare(MouseEvent e) {
        boolean onSquare = false;
        for (int i = 0; i < 64; i++) {
            if(onSquare(e.getXOnScreen(),e.getYOnScreen(),i)){
                if (emptySquare(i)){
                    setLocation(((JButton) Main.fields.get(i)).getX() + CENTER_A,
                            ((JButton) Main.fields.get(i)).getY() + CENTER_A);
                    onSquare = true;
                    setInSquare(i);
                    int sol;
                    if (illegalMove(i)){
                        sol = 0;
                    }
                    else {
                        sol = Logic.countSolutions(Main.queens);
                    }
                    Main.l1.setText(String.valueOf(sol));
                    Main.frame.add(Main.l1);
                    Main.frame.repaint();
                    break;
                }
            }
        }

        // If not on a button, move the queen to the initial place
        if (!onSquare){
            setLocation(700,520-(queenNumber * Main.SQUARE_SIZE));
            setInSquare(-1);
            int numberOfSolutions = Logic.countSolutions(Main.queens);
            Main.l1.setText(String.valueOf(numberOfSolutions));
            Main.frame.add(Main.l1);
            Main.frame.repaint();
        }
    }

    void drawQueen(Image queenImage) {
        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawImage(queenImage,x,y,width,height, Main.frame);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(0,0,0,0);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });
        repaint();
    }

    private boolean illegalMove(int index) {
        return !Logic.checkBoard(index/ Main.bord_size,index% Main.bord_size, Logic.bord);
    }

    public static boolean emptySquare(int index) {
        for (int i = 0; i < Main.bord_size; i++) {
            if(Main.queens[i].getInSquare() == index){
                return false;
            }
        }
        return true;
    }

    private boolean onSquare(int xOnScreen, int yOnScreen,int index) {
        int SQUARE_SIZE = Main.SQUARE_SIZE;
        int buttonX = ((JButton) Main.fields.get(index)).getX();
        int buttonY = ((JButton) Main.fields.get(index)).getY();
        return buttonX - 20 <= xOnScreen && buttonX + 20 + SQUARE_SIZE >= xOnScreen
                && buttonY - 20 <= yOnScreen && buttonY + 20 + SQUARE_SIZE >= yOnScreen;
    }

    public int getQueenNumber() {
        return this.queenNumber;
    }

    public int getInSquare() {
        return inSquare;
    }



    public void setInSquare(int inSquare) {
        this.inSquare = inSquare;
    }
}
