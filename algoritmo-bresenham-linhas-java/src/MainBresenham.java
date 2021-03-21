import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class MainBresenham extends JPanel implements Runnable {

    Thread runner;

    int clickX = 0;
    int clickY = 0;
    int mouseX = 0;
    int mouseY = 0;

    ArrayList<Integer> a1 = new ArrayList<>(0);
    ArrayList<Integer> b1 = new ArrayList<>(0);
    ArrayList<Integer> a2 = new ArrayList<>(0);
    ArrayList<Integer> b2 = new ArrayList<>(0);


    BufferedImage imageBuffer;
    byte[] bufferDeVideo;

    public MainBresenham() {
        imageBuffer = new BufferedImage(640, 480, BufferedImage.TYPE_4BYTE_ABGR);

        bufferDeVideo = ((DataBufferByte) imageBuffer.getRaster().getDataBuffer()).getData();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

                a1.add(clickX);
                b1.add(clickY);

                clickX = e.getX();
                clickY = e.getY();

                a2.add(clickX);
                b2.add(clickY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent arg0) {
                // TODO Auto-generated method stub
                mouseX = arg0.getX();
                mouseY = arg0.getY();
            }

            @Override
            public void mouseDragged(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);

        g.setColor(Color.black);

        g.fillRect(0, 0, 640, 480);

        g.drawImage(imageBuffer, 0, 0, null);
    }

    public void start() {
        runner = new Thread(this);
        runner.start();
    }

    public void clearVideoBuffer(Color cor) {
        for (int cont = 0; cont < (640 * 480); cont++) {
            bufferDeVideo[(cont * 4)] = (byte) cor.getAlpha();
            bufferDeVideo[(cont * 4) + 1] = (byte) cor.getBlue();
            bufferDeVideo[(cont * 4) + 2] = (byte) cor.getGreen();
            bufferDeVideo[(cont * 4) + 3] = (byte) cor.getRed();
        }

        drawHist();
    }

    public void printPixel(int x, int y, Color color) {
        int pos = (x * 4) + ((y * 4) * 640);
        if ((x >= 0 && x < 640) && (y >= 0 && y < 480)) {
            bufferDeVideo[pos] = (byte) color.getAlpha();
            bufferDeVideo[pos + 1] = (byte) color.getBlue();
            bufferDeVideo[pos + 2] = (byte) color.getGreen();
            bufferDeVideo[pos + 3] = (byte) color.getRed();
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2) {

        //variaveis auxiliares para alteração dos pixels

        int deltaX = x2 - x1;

        int deltaY = y2 - y1;

        //armazenamento dos eixos

        int eixoMaior, eixoMenor;

        int incrementoX0 = 0, incrementoY0 = 0, incrementoX1 = 0, incrementoY1 = 0, numerador;

        //testes para analisar se os eixos estão crescendo ou decrescendo de acordo com o delta

        if (deltaX > 0) {
            incrementoX0 = incrementoX1 = 1;
        }

        if (deltaX < 0) {
            incrementoX0 = incrementoX1 = -1;
        }

        if (deltaY > 0) {
            incrementoY0 = 1;
        }

        if (deltaY < 0) {
            incrementoY0 = -1;
        }

        if (Math.abs(deltaX) >= Math.abs(deltaY)) {
            eixoMaior = Math.abs(deltaX);
            eixoMenor = Math.abs(deltaY);
        } else {
            eixoMaior = Math.abs(deltaY);
            eixoMenor = Math.abs(deltaX);
            if (deltaY > 0) {
                incrementoY1 = 1;
            }
            if (deltaY < 0) {
                incrementoY1 = -1;
            }
            incrementoX1 = 0;
        }

        numerador = eixoMaior / 2;

        for (int i = 0; i <= eixoMaior; i++) {
            printPixel(x1, y1, Color.GREEN);
            numerador += eixoMenor;
            if (numerador > eixoMaior) {
                numerador -= eixoMaior;
                x1 += incrementoX0;
                y1 += incrementoY0;
            } else {
                x1 += incrementoX1;
                y1 += incrementoY1;
            }

        }
    }

    public void drawHist() {
        for (int i = 0; i < a1.size(); i++) {
            drawLine(a1.get(i), b1.get(i), a2.get(i), b2.get(i));
        }
    }

    @Override
    public void run() {
        while (true) {
            paintImmediately(0, 0, 640, 480);

            clearVideoBuffer(Color.black);

            drawLine(clickX, clickY, mouseX, mouseY);

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
