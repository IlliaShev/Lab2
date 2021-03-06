package Interface;

import dto.GroupOfProducts;
import dto.Product;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

/**
 * Class of interface for change value window
 */
public class ChangeValueWindow extends JDialog {
    /**
     * WIDTH
     */
    private static final int WIDTH = 280;
    /**
     * HEIGHT
     */
    private static final int HEIGHT = 210;
    /**
     * db
     */
    private final DateBase db;
    /**
     * choosePanel
     */
    private JPanel choosePanel;
    /**
     * buttonPanel
     */
    private JPanel buttonPanel;
    /**
     * groupBox
     */
    private JComboBox<GroupOfProducts> groupBox;
    /**
     * productBox
     */
    private JComboBox<Product> productBox;
    /**
     * amountField
     */
    private JTextField amountField;
    /**
     * isAdding
     */
    private final boolean isAdding;


    private final Font custom_font  = new Font("Courier New", Font.BOLD, 14);


    /**
     * Constructor ChangeValueWindow
     *
     * @param owner    JFrame
     * @param db       DateBase
     * @param isAdding boolean
     */
    public ChangeValueWindow(JFrame owner, DateBase db, boolean isAdding) {
        super(owner, true);
        this.db = db;
        this.isAdding = isAdding;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        if (isAdding)
            setTitle("Add product to stock");
        else
            setTitle("Remove product from stock");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((screenSize.width-WIDTH)/2, (screenSize.height - HEIGHT)/2,WIDTH, HEIGHT);
        setResizable(false);
        init(this);
        setLayout(new BorderLayout());
        add(choosePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    /**
     * init
     *
     * @param frame JFrame
     */
    private void init(JDialog frame) {
        choosePanel = new JPanel(new GridLayout(3, 2));
        {
            choosePanel.add(new JLabel("Group", JLabel.CENTER));
            groupBox = new JComboBox<>();
            groupBox.setFont(custom_font);
            for (GroupOfProducts g : db.getGroups().getListOfGroups()) {
                groupBox.addItem(g);
            }
            groupBox.setSelectedItem(null);
            groupBox.setEditable(false);
            groupBox.setMaximumRowCount(5);
            groupBox.setFocusable(false);
            groupBox.setPreferredSize(new Dimension(WIDTH / 5, HEIGHT / 7));
            groupBox.addActionListener(e -> {
                productBox.removeAllItems();
                GroupOfProducts group = (GroupOfProducts) groupBox.getSelectedItem();
                if (group != null) {
                    for (Product p : group.getListOfProducts()) {
                        productBox.addItem(p);
                    }
                }
                productBox.setSelectedItem(null);
            });
            //       temp.add(groupBox);
            choosePanel.add(groupBox);
        }
        {
            choosePanel.add(new JLabel("Product", JLabel.CENTER));
            productBox = new JComboBox<>();
            productBox.setFont(custom_font);
            productBox.setEditable(false);
            productBox.setFocusable(false);
            productBox.setMaximumRowCount(5);
            productBox.setPreferredSize(new Dimension(WIDTH / 5, HEIGHT / 7));
            choosePanel.add(productBox);
        }
        choosePanel.add(new JLabel("Amount", JLabel.CENTER));
        amountField = new JTextField();
        amountField.setFont(custom_font);
        amountField.setText("0");
        amountField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        amountField.addActionListener(e -> {
            try {
                Integer.parseInt(amountField.getText());
            } catch (NumberFormatException ex) {
                amountField.setText("0");
            }
        });
        choosePanel.add(amountField);
        buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setFont(custom_font);
        {
            JPanel temp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton okButton = new JButton("Ok");
            okButton.setFont(custom_font);
            okButton.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 7));
            okButton.addActionListener(e -> {
                GroupOfProducts group = (GroupOfProducts) groupBox.getSelectedItem();
                Product product = (Product) productBox.getSelectedItem();
                if (group != null && product != null) {
                    int amount;
                    try {
                        amount = Integer.parseInt(amountField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter correct amount", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (isAdding) {
                        if (group.addProduct(product, amount)) {
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error happened, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        if (!group.discardProduct(product, amount)) {
                            JOptionPane.showMessageDialog(frame, "Not enough product in stock", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please choose group and product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            temp.add(okButton);
            buttonPanel.add(temp);
        }
        {
            JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEADING));
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFont(custom_font);
            cancelButton.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 7));
            cancelButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
            temp.add(cancelButton);
            buttonPanel.add(temp);
        }
    }
}
