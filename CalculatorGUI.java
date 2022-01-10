import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class CalculatorGUI extends JComponent implements MouseListener{
    private int WIDTH, HEIGHT;
    public CalculatorGUI(){
        WIDTH = 525;
        HEIGHT = 630;

        // setting up gui
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Calculator");
        gui.setPreferredSize(new Dimension(WIDTH+5,HEIGHT+30));
        gui.setResizable(false);
        gui.getContentPane().add(this);

        JTextField text = new JTextField();
        Font textBoxFont = new Font("Calibri",Font.PLAIN,30);
        text.setFont(textBoxFont);
        text.setHorizontalAlignment(SwingConstants.RIGHT);
        gui.add(text,BorderLayout.PAGE_START);

        JPanel symbols = new JPanel(new GridLayout(5,4));
        symbols.setBorder(new EmptyBorder(0,0,0,0));
        symbols.setOpaque(false);

        symbols.add(createButton("("));
        symbols.add(createButton(")"));
        symbols.add(createButton("CLEAR"));
        symbols.add(createButton("DELETE"));
        symbols.add(createButton("7"));
        symbols.add(createButton("8"));
        symbols.add(createButton("9"));
        symbols.add(createButton("/"));
        symbols.add(createButton("4"));
        symbols.add(createButton("5"));
        symbols.add(createButton("6"));
        symbols.add(createButton("*"));
        symbols.add(createButton("1"));
        symbols.add(createButton("2"));
        symbols.add(createButton("3"));
        symbols.add(createButton("-"));
        symbols.add(createButton("0"));
        symbols.add(createButton("."));
        symbols.add(createButton("="));
        symbols.add(createButton("+"));
        for(int i =0; i<20; i++){
            Component curr = symbols.getComponent(i);
            if(curr instanceof AbstractButton){
                System.out.println(curr.getName());
                ((AbstractButton) curr).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String symbol = ((AbstractButton) curr).getText();
                        switch (symbol) {
                            case "CLEAR":
                                System.out.println("1");
                                text.setText("");
                                break;
                            case "DELETE":
                                System.out.println('2');
                                String txt = text.getText();
                                if (txt.length() >= 1)
                                    text.setText(txt.substring(0, txt.length() - 1));
                                break;
                            case "=":
                                try {
                                    System.out.println('3');
                                    Calculations calc = new Calculations(text.getText());
                                    System.out.println(calc.getEquation());
                                    System.out.println(calc.calculate());
                                    text.setText(calc.calculate() + "");
                                }
                                catch(Exception p){
                                    text.setText("ERROR");
                                }
                                break;
                            default:
                                System.out.println("4");
                                text.setText(text.getText() + symbol);
                                break;
                        }
                    }
                });
            }
        }

        gui.add(symbols);
        gui.setSize(100,100);
        gui.setVisible(true);

        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
        gui.addMouseListener(this);
    }
    public static Component createButton(String text){
        JButton button = new JButton(text);
        button.setBackground(Color.pink);
        button.setBorder(BorderFactory.createLineBorder(Color.white));
        button.setPreferredSize(new Dimension(98,102));
        Font buttonFont = new Font("Calibri",Font.PLAIN,20);
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont);
        return button;
    }
    public void loop()
    {

    }
    public void start(final int ticks){
        Thread thread = new Thread(){
            public void run(){
                while(true){
                    loop();
                    try{
                        Thread.sleep(1000 / ticks);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public static void main(String[] args)
    {
        CalculatorGUI g = new CalculatorGUI();
        g.start(60);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
