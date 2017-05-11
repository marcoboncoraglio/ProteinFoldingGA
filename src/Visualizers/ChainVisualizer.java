package Visualizers;

import Model.Amminoacid;
import Model.Chain;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Math.abs;

/**
 * Created by marco on 11/05/17.
 */

public class ChainVisualizer extends Frame {
    private Chain chain;

    public ChainVisualizer(Chain c) {
        super("Chain Visualizer");
        setSize(1200, 800);
        setBackground(Color.white);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);

        chain = c;
    }

    int[] findCenter() {
        int averageX = 0;
        int averageY = 0;
        int[] returnArray = new int[2];

        for (Amminoacid a : chain.getAmminoChain()) {
            averageX += a.getX();
            averageY += a.getY();
        }

        averageX = averageX / chain.getAmminoChain().size();
        averageY = averageY / chain.getAmminoChain().size();
        returnArray[0] = averageX * 50;
        returnArray[1] = averageY * 50;

        return returnArray;
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(graphics);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        plotGrid(g);
    }

    public void plotGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int[] center = findCenter();

        for (int i = 0; i < chain.getAmminoChain().size(); i++) {
            if (chain.getAmminoChain().get(i).isHydrophobic())
                g2d.fillOval(abs(center[0]) + chain.getAmminoChain().get(i).getX() * 50, abs(center[1]) + chain.getAmminoChain().get(i).getY() * 50, 10, 10);
            else
                g2d.drawOval(abs(center[0]) + chain.getAmminoChain().get(i).getX() * 50, abs(center[1]) + chain.getAmminoChain().get(i).getY() * 50, 10, 10);

            if (i != 0)
                g2d.drawLine(abs(center[0]) + chain.getAmminoChain().get(i - 1).getX() * 50,
                        abs(center[1]) + chain.getAmminoChain().get(i - 1).getY() * 50,
                        abs(center[0]) + chain.getAmminoChain().get(i).getX() * 50,
                        abs(center[1]) + chain.getAmminoChain().get(i).getY() * 50);
        }
    }
}
