package Interface;

import dto.GroupOfProducts;
import dto.Product;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SetProduct extends JDialog {
    /**
     * WIDTH
     */
    private static final int WIDTH = 250;
    /**
     * HEIGHT
     */
    private static final int HEIGHT = 200;
    private JPanel setPanel;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField producerField;
    private JTextField priceField;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private DateBase db;
    private Product result;
    private final Font custom_font  = new Font("Courier New", Font.BOLD, 14);


    public SetProduct(JFrame frame, String title, DateBase db, Product product) {
        super(frame, true);
        this.db = db;
        setTitle(title);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((screenSize.width-WIDTH)/2, (screenSize.height - HEIGHT)/2,WIDTH, HEIGHT);
        setResizable(false);
        init(this, product);
        setLayout(new BorderLayout());
        add(setPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void init(JDialog frame, Product prod){
        nameField = new JTextField();
        nameField.setFont(custom_font);
        nameField.setFont(custom_font);
        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
               if(e.getKeyChar()!=' '&&e.getKeyChar()!='_'&&e.getKeyChar()!='\''&&!Character.isLetter(e.getKeyChar())){
                   e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) { }
        });
        descriptionField = new JTextField();
        descriptionField.setFont(custom_font);
        producerField = new JTextField();
        producerField.setFont(custom_font);
        priceField = new JTextField();
        priceField.setFont(custom_font);
        priceField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Double.parseDouble(priceField.getText());
                }catch(NumberFormatException ex){
                    priceField.setText("0");
                }
            }
        });
        priceField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
             if(!Character.isDigit(e.getKeyChar())&&e.getKeyChar()!='.'){
                 e.consume();
             }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        if(prod!=null){
            nameField.setText(prod.getName());
            nameField.setFont(custom_font);
            descriptionField.setText(prod.getDescription());
            descriptionField.setFont(custom_font);
            producerField.setText(prod.getProducer());
            producerField.setFont(custom_font);
            priceField.setText(""+prod.getPrice());
            priceField.setFont(custom_font);

        }
        setPanel = new JPanel(new GridLayout(4,2));
        {
            JLabel temp = new JLabel("Name", JLabel.CENTER);
            temp.setFont(custom_font);
            setPanel.add(temp);
        }
        setPanel.add(nameField);
        {
            JLabel temp = new JLabel("Description", JLabel.CENTER);
            temp.setFont(custom_font);
            setPanel.add(temp);
        }
        setPanel.add(descriptionField);
        {
            JLabel temp = new JLabel("Producer", JLabel.CENTER);
            temp.setFont(custom_font);
            setPanel.add(temp);
        }
        setPanel.add(producerField);
        {
            JLabel temp = new JLabel("Price", JLabel.CENTER);
            temp.setFont(custom_font);
            setPanel.add(temp);
        }
        setPanel.add(priceField);
        okButton = new JButton("Ok");
        okButton.setFont(custom_font);
        okButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Double.parseDouble(priceField.getText());
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Enter correct data", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(isCorrectName(nameField.getText())&&isNotEmpty(producerField.getText())){
                    if((prod != null&&prod.getName().equals(nameField.getText()))||isUnique(nameField.getText())){
                        result = new Product(nameField.getText(), isNotEmpty(descriptionField.getText())?descriptionField.getText():"-", producerField.getText(), Double.parseDouble(priceField.getText()));
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    }else{
                        JOptionPane.showMessageDialog(null, "This name is already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Enter correct data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(custom_font);
        cancelButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = null;
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        buttonPanel = new JPanel(new GridLayout(1,2));
        {
            JPanel temp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            temp.add(okButton);
            buttonPanel.add(temp);
        }
        {
            JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
            temp.add(cancelButton);
            buttonPanel.add(temp);
        }
    }

    public Product getResult(){
        return result;
    }

    private boolean isUnique(String name){
        for(GroupOfProducts g: db.getGroups().getListOfGroups()){
            for(Product p: g.getListOfProducts()){
                if(p.getName().equals(name)) return false;
            }
        }
        return true;
    }

    private static boolean isNotEmpty(String s){
        if(s == null||s.equals("")) return false;
        return true;
    }

    private static boolean isCorrectName(String name){
        if(name == null||name.equals("")) return false;
        for(Character c: name.toCharArray()){
            if(!(Character.isLetter(c)||c==' '||c=='_'||c=='\''||c=='`')){
                return false;
            }
        }
        return true;
    }
}
