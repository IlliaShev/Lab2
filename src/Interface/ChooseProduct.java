package Interface;

import dto.GroupOfProducts;
import dto.Product;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ChooseProduct extends JDialog {
    /**
     * WIDTH
     */
    private static final int WIDTH = 250;
    /**
     * HEIGHT
     */
    private static final int HEIGHT = 200;
    /**
     * db
     */
    private final DateBase db;
    private JPanel choosePanel;
    private JComboBox<GroupOfProducts> groupBox;
    private JComboBox<Product> productBox;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private Product result;
    private final Font custom_font  = new Font("Courier New", Font.BOLD, 20);


    public ChooseProduct(JFrame owner, String title, DateBase db) {
        super(owner, true);
        this.db = db;
        setTitle(title);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((screenSize.width-WIDTH)/2, (screenSize.height - HEIGHT)/2,WIDTH, HEIGHT);
        setResizable(false);
        init(this);
        setLayout(new BorderLayout());
        add(choosePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void init(JDialog frame){
        groupBox = new JComboBox<>();
        groupBox.setFont(custom_font);
        for(GroupOfProducts g: db.getGroups().getListOfGroups()){
            groupBox.addItem(g);
        }
        groupBox.setPreferredSize(new Dimension((int) (WIDTH/2.2), HEIGHT/5));
        groupBox.setSelectedItem(null);
        groupBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupBox.getSelectedItem()!=null){
                    GroupOfProducts g = (GroupOfProducts) groupBox.getSelectedItem();
                    if(g.getListOfProducts().size()>0){
                        productBox.removeAllItems();
                        for(Product p: g.getListOfProducts()){
                            productBox.addItem(p);
                        }
                        productBox.setSelectedItem(null);
                    }else{
                        JOptionPane.showMessageDialog(null, "Ця группа не містить продуктів", "Warning", JOptionPane.WARNING_MESSAGE);
                        groupBox.setSelectedItem(null);
                    }
                }
            }
        });
        productBox = new JComboBox<>();
        productBox.setFont(custom_font);
        productBox.setSelectedItem(null);
        productBox.setPreferredSize(new Dimension((int) (WIDTH/2.2), HEIGHT/5));
        choosePanel = new JPanel(new GridLayout(2,2));
        choosePanel.add(new JLabel("Group", JLabel.CENTER));
        {
            JPanel temp = new JPanel(new GridBagLayout());
            temp.add(groupBox);
            choosePanel.add(temp);
        }
        choosePanel.add(new JLabel("Product", JLabel.CENTER));
        {
            JPanel temp = new JPanel(new GridBagLayout());
            temp.add(productBox);
            choosePanel.add(temp);
        }
        okButton = new JButton("Ok");
        okButton.setFont(custom_font);
        okButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupBox.getSelectedItem()!=null&&productBox.getSelectedItem()!=null){
                    result = (Product) productBox.getSelectedItem();
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }else{
                    JOptionPane.showMessageDialog(null, "Оберіть коректну группу та продукт", "Warning", JOptionPane.WARNING_MESSAGE);
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


}
