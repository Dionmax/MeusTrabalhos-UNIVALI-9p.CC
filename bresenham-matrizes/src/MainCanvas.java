import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainCanvas extends JPanel implements Runnable {

    Thread runner;
    boolean ativo = true;
    int paintcounter = 0;

    BufferedImage imageBuffer;
    byte bufferDeVideo[];

    Random rand = new Random();

    byte memoriaPlacaVideo[];
    short paleta[][];

    int framecount = 0;
    int fps = 0;

    Font f = new Font("", Font.PLAIN, 30);

    int clickX = 0;
    int clickY = 0;
    int mouseX = 0;
    int mouseY = 0;

    int Largura = 640;
    int Altura = 480;
    int pixelSize = Largura * Altura;

    BufferedImage imgtmp = null;

    Point rect[] = new Point[4];
    Vetor2d rect2[] = new Vetor2d[4];

    public MainCanvas() {
        setSize(Largura, Altura);

        rect[0] = new Point(10, 10);
        rect[1] = new Point(110, 10);
        rect[2] = new Point(110, 110);
        rect[3] = new Point(10, 110);


        rect2[0] = new Vetor2d(100, 100, 1);
        rect2[1] = new Vetor2d(200, 100, 1);
        rect2[2] = new Vetor2d(200, 200, 1);
        rect2[3] = new Vetor2d(100, 200, 1);

        try {
            imgtmp = ImageIO.read(getClass().getResource("fundo.jpg"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        imageBuffer = new BufferedImage(640, 480, BufferedImage.TYPE_4BYTE_ABGR);
        //imageBuffer.getGraphics().drawImage(imgtmp, 0, 0, null);

        bufferDeVideo = ((DataBufferByte) imageBuffer.getRaster().getDataBuffer()).getData();

        //memoriaPlacaVideo = new byte[W*H];

        paleta = new short[255][3];

        for (int i = 0; i < 255; i++) {
            paleta[i][0] = (short) rand.nextInt(255);
            paleta[i][1] = (short) rand.nextInt(255);
            paleta[i][2] = (short) rand.nextInt(255);
        }

        MainClass.frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].translate(10, 0);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].translate(-10, 0);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].translate(0, -10);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].translate(0, 10);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].scale(1.2f, 1.2f);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].scale(0.8f, 0.8f);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_R) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].rotate(30);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_E) {
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].rotate(-30);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    float cx = rect[0].x + ((rect[2].x - rect[0].x) / 2.0f);
                    float cy = rect[0].y + ((rect[2].y - rect[0].y) / 2.0f);

                    for (int i = 0; i < rect.length; i++) {
                        rect[i].translate(-cx, -cy);
                    }
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].rotate(30);
                    }
                    for (int i = 0; i < rect.length; i++) {
                        rect[i].translate(cx, cy);
                    }
                }
            }
        });

//		MainClass.frame.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				Matriz3x3 mat = new Matriz3x3();
//				mat.setIdentity();
//
//				if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
//					mat.setTranslate(10, 0);
//				}
//				if(e.getKeyCode()==KeyEvent.VK_LEFT) {
//					mat.setTranslate(-10, 0);
//				}
//				if(e.getKeyCode()==KeyEvent.VK_UP) {
//					mat.setTranslate(0,-10);
//				}
//				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
//					mat.setTranslate(0, 10);
//				}
//
//				System.out.println("MULTIPLICANDO");
//				for(int i = 0; i < rect2.length;i++) {
//					rect2[i] = mat.multiplicaVetor(rect2[i]);
//				}
//			}
//		});

        addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                clickX = e.getX();
                clickY = e.getY();
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

    public void setPixel(int x, int y, int r, int g, int b, int a) {
        int index = (x + y * Altura * 4);

        bufferDeVideo[index] = (byte) (0x00ff & a);
        bufferDeVideo[index + 1] = (byte) (0x00ff & b);
        bufferDeVideo[index + 2] = (byte) (0x00ff & g);
        bufferDeVideo[index + 3] = (byte) (0x00ff & r);
    }

    @Override
    public void paint(Graphics g) {

        g.setFont(f);

        g.setColor(Color.white);
        g.fillRect(0, 0, Largura, 480);
//		g.setColor(Color.black);
//		g.drawLine(0, 0, 640, 480);

        // Usem o Beseram no buffer
        //g.drawImage(imageBuffer,0,0,null);

        g.setColor(Color.black);
        g.drawLine((int) rect[0].x, (int) rect[0].y, (int) rect[1].x, (int) rect[1].y);
        g.drawLine((int) rect[1].x, (int) rect[1].y, (int) rect[2].x, (int) rect[2].y);
        g.drawLine((int) rect[2].x, (int) rect[2].y, (int) rect[3].x, (int) rect[3].y);
        g.drawLine((int) rect[3].x, (int) rect[3].y, (int) rect[0].x, (int) rect[0].y);

//		g.setColor(Color.black);
//		g.drawLine((int)rect2[0].x, (int)rect2[0].y, (int)rect2[1].x, (int)rect2[1].y);
//		g.drawLine((int)rect2[1].x, (int)rect2[1].y, (int)rect2[2].x, (int)rect2[2].y);
//		g.drawLine((int)rect2[2].x, (int)rect2[2].y, (int)rect2[3].x, (int)rect2[3].y);
//		g.drawLine((int)rect2[3].x, (int)rect2[3].y, (int)rect2[0].x, (int)rect2[0].y);


        //g.setColor(Color.BLUE);
        //g.drawLine(clickX, clickY, mouseX, mouseY);

        g.setColor(Color.black);
        g.drawString("FPS " + fps, 10, 25);
    }

    public void start() {
        runner = new Thread(this);
        runner.start();
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        long segundo = time / 1000;

        while (ativo) {
            paintImmediately(0, 0, Largura, Altura);
            paintcounter += 100;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            long newtime = System.currentTimeMillis();
            long novoSegundo = newtime / 1000;
            framecount++;

            if (novoSegundo != segundo) {
                fps = framecount;
                framecount = 0;
                segundo = novoSegundo;
            }
        }
    }
}
