import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Minigame extends JPanel{
    
    // All the components are saved here, most are not initialised at this point.
    private JFrame menu;
    private boolean clearScreen = false;
    private JButton first;
    private JButton exit;
    private JLabel infoLabel;
    private JRadioButton option;
    private ArrayList<Circle> circles = new ArrayList<>();
    private final int NUM_CIRCLES = 15;

    /*
     * Private nested helper class "Circle"
     * This is used to create Circle objects to draw
     * It has a contains method that is used to validate if
     * the user is clicking within a Circle object.
     */
    private class Circle {
        private int x, y, radius;
        public Circle(int x, int y, int radius){
            this.x = x; this.y = y; this.radius = radius;
        }

        public boolean contains(int mx, int my) {
            int dx = mx - (x + radius);
            int dy = my - (y + radius);
            return (dx * dx + dy * dy) <= radius * radius;
        }
}
    /* Minigame constructor
     * This will create a new JFrame, which will have a size of 1000x900 and a grey background
     * This will serve as the menu screen.
     */
    public Minigame(){                                          
        menu = new JFrame("Start Menu");                            // Initialise menu JFrame
        
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // This will make sure the program ends when clicking close
    
        this.setBackground(Color.DARK_GRAY);                        // Set background color and window size
        this.setPreferredSize(new Dimension(1000,900));
        this.setLayout(null);
        menu.setResizable(false);                                   // Set window to not be resizable

        menu.setLocation(450,50);
        menu.add(this);
        menu.pack();
        menu.setLayout(null);
        
       
        exit = new JButton("Exit");                                     // Initialise the exit button
        exit.setBounds(400, 790, 220,50);                              // set button size
        exit.setBackground(Color.PINK);                                // change button colour to pink
        exit.setFont(new Font("Monospaced", Font.BOLD, 25));            // set font to Monospaced, font size to 25
        
        exit.addActionListener(new ActionListener(){                // This ActionListener will close the program when pressed
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        first = new JButton("Click to Start");                          // initialise start button
        first.setBounds(380, 700, 270,70);                              // set button size
        first.setBackground(Color.PINK);                                // change button colour to pink
        first.setFont(new Font("Monospaced", Font.BOLD, 25));          //  change font type and size
       
        /*
         * This action listener is used to begin the game, when the start button is pressed.
         * It will remove set the clearScreen boolean to true, change
         * the menu JFrame text, remove the components from the JPanel,
         * and refresh the screen.
         */
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearScreen = true;
                menu.setTitle("In-Game");
                
                Minigame.this.remove(first);
                Minigame.this.remove(exit);
                Minigame.this.remove(infoLabel);
                Minigame.this.remove(option);

                Minigame.this.revalidate(); 
                Minigame.this.repaint();     
                Minigame.this.requestFocusInWindow(); 

            }
        });

        this.add(first); this.add(exit);
        menu.setVisible(true);

        // JLabel, this will add a text label over the JRadioButton below
        infoLabel = new JLabel("This mode will randomize balloon colours");
        infoLabel.setBounds(250, 600, 600, 50);
        infoLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        infoLabel.setForeground(Color.WHITE);
        
        this.add(infoLabel);

        // Radio button, this will be used to enable a "Colourful Mode"
        
        option = new JRadioButton("Colourful Mode");
        option.setFont(new Font("Monospaced", Font.BOLD, 20));
        option.setBounds(400, 650, 200, 30);
        option.setOpaque(false);
        option.setForeground(Color.WHITE);
        this.add(option);

        // Mouse and key listeners method
        setupListeners();   
    }
    // This helper method will generate Circle objects into a ArrayList, using the random package
    private void generateCircles(){
        circles.clear();
        Random rand = new Random();
        for(int i = 0; i < NUM_CIRCLES; i++){
            int r = 40;
            int x = rand.nextInt(getWidth() - 2*r);
            int y = rand.nextInt(getHeight() - 2*r);
            circles.add(new Circle(x, y, r));
        }
    }
    // This method will add the components to the JPanel
    // This method will be called after the user presses the R key
    private void showMenuComponents() {
        this.add(first);
        this.add(exit);
        this.add(infoLabel);
        this.add(option);
    
        first.setVisible(true);
        exit.setVisible(true);
        infoLabel.setVisible(true);
        option.setVisible(true);
    
        this.revalidate();
        this.repaint();
        this.requestFocusInWindow();
        generateCircles();
    }
    // This method will be used to initialise the mouse and key listeners.
    // Method is called in the Minigame constructor.
    private void setupListeners() {
        // Mouse clicks
        this.addMouseListener(new MouseListener(){
            @Override
            public void mousePressed(MouseEvent e){
                if(clearScreen){
                    for(int i = 0; i < circles.size(); i++){
                        if(circles.get(i).contains(e.getX(), e.getY())){
                            circles.remove(i);
                            repaint();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(clearScreen){
                    for(int i = 0; i < circles.size(); i++){
                        if(circles.get(i).contains(e.getX(), e.getY())){
                            circles.remove(i);
                            repaint();
                            break;
                        }
                    }
                }
    
            }

            @Override                                   // The rest of these mouse events are unused
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
        });
    
        // Key presses
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_R){
                    
                    clearScreen = false;
                    showMenuComponents();
                }
            }
        });
        
    
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    
    /*
     * This method will be used to draw the menu and in-game screen
     * The clearScreen boolean is set as on when the start button is pressed,
     * which repaints the screen to its in-game version.
     */
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g1 = (Graphics2D) g;
       if(!clearScreen){
            super.paintComponent(g);
        
            g1.setColor(Color.BLUE);                        // adding ovals on menu screen (balloons)
            g1.fillOval(400,250, 200,200);

            g1.setColor(Color.RED);
            g1.fillOval(200,350, 100,100);

            g1.setColor(Color.MAGENTA);
            g1.fillOval(700,150, 60,60);

 
            g.setColor(Color.GREEN);                            // menu text is displayed
            g.setFont(new Font("Monospaced", Font.BOLD, 70));
            g.drawString("Pop The Balloons", 200, 100);

       }else{
            
            super.paintComponent(g);
            if(circles.isEmpty()) 
                generateCircles();
        
            for(Circle c : circles){
                if(option.isSelected()){
                    g1.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255))); // random colour is selected
                } else {
                    g1.setColor(Color.BLUE);                        // colour is blue by default (if colourful mode is not selected)
                }
                g1.fillOval(c.x, c.y, c.radius * 2, c.radius * 2);
            }
        
            g1.setColor(Color.WHITE);
            g1.setFont(new Font("Monospaced", Font.BOLD, 25));
            g1.drawString("Click balloons to pop. Press R to return.", 200, 850);   // Instruction message written for in-game screen
                    
        }
    }
}


