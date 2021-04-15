package Interface;

import dto.GroupOfProducts;
import dto.Product;
import utils.DateBase;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class ChangeValueWindow extends JDialog {
    private static final int WIDTH = 280;
    private static final int HEIGHT = 210;

    private DateBase db;

    private JPanel choosePanel;
    private JPanel buttonPanel;
    private JComboBox<GroupOfProducts> groupBox;
    private JComboBox<Product> productBox;
    private JTextField amountField;
    private JButton okButton;
    private JButton cancelButton;
    private boolean isAdding;

    public ChangeValueWindow(JFrame owner, DateBase db, boolean isAdding){
        super(owner, true);
        this.db = db;
        this.isAdding = isAdding;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        if(isAdding)
            setTitle("Додати товар на склад");
        else
            setTitle("Списати товар зі складу");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        init(this);
        setLayout(new BorderLayout());
        add(choosePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private void init(JDialog frame){
        choosePanel = new JPanel(new GridLayout(3, 2));
        {
            choosePanel.add(new JLabel("Group"));
            groupBox = new JComboBox<>();
            for(GroupOfProducts g: db.getGroups().getListOfGroups()){
                groupBox.addItem(g);
            }
            groupBox.setSelectedItem(null);
            groupBox.setEditable(false);
            groupBox.setAutoscrolls(true);
            groupBox.setFocusable(false);
            groupBox.setPreferredSize(new Dimension(WIDTH/5, HEIGHT/7));
            groupBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    productBox.removeAllItems();
                    GroupOfProducts group = (GroupOfProducts) groupBox.getSelectedItem();
                    if(group != null){
                        for(Product p: group.getListOfProducts()){
                            productBox.addItem(p);
                        }
                    }
                    productBox.setSelectedItem(null);
                }
            });
     //       temp.add(groupBox);
            choosePanel.add(groupBox);
        }
        {
            choosePanel.add(new JLabel("Product"));
            productBox = new JComboBox<>();
            productBox.setEditable(false);
            productBox.setFocusable(false);
            productBox.setAutoscrolls(true);
            productBox.setPreferredSize(new Dimension(WIDTH/5, HEIGHT/7));
            choosePanel.add(productBox);
        }
        choosePanel.add(new JLabel("Amount"));
        amountField = new JTextField();
        amountField.setText("0");
        amountField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(!Character.isDigit(e.getKeyChar())) e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
        amountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Integer.parseInt(amountField.getText());
                }catch (NumberFormatException ex){
                    amountField.setText("0");
                }
            }
        });
        choosePanel.add(amountField);
        buttonPanel = new JPanel(new GridLayout(1,2));
        {
            JPanel temp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            okButton = new JButton("Ok");
            okButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/7));
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GroupOfProducts group = (GroupOfProducts) groupBox.getSelectedItem();
                    Product product = (Product) productBox.getSelectedItem();
                    if(group!=null&&product!=null){
                        int amount = 0;
                        try{
                            amount = Integer.parseInt(amountField.getText());
                        }catch(NumberFormatException ex){
                            JOptionPane.showMessageDialog(frame, "Please enter correct amount", "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        if(isAdding) {
                            if(group.addProduct(product, amount)) {
                                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            }else{
                                JOptionPane.showMessageDialog(frame, "Error happened, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            if (!group.discardProduct(product, amount)) {
                                JOptionPane.showMessageDialog(frame, "Not enough product in stock", "Error", JOptionPane.ERROR_MESSAGE);
                            }else{
                                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame, "Please choose group and product.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            temp.add(okButton);
            buttonPanel.add(temp);
        }
        {
            JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEADING));
            cancelButton = new JButton("Cancel");
            cancelButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/7));
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            });
            temp.add(cancelButton);
            buttonPanel.add(temp);
        }
    }
}
