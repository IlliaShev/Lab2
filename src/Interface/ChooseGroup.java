package Interface;

import dto.GroupOfProducts;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ChooseGroup extends JDialog {
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
    private JComboBox<GroupOfProducts> groupBox;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel buttonPanel;
    private JPanel chooserPanel;
    private GroupOfProducts chosenGroup;
    private final Font custom_font  = new Font("Courier New", Font.BOLD, 14);



    public ChooseGroup(JFrame owner, String title, DateBase db) {
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
        add(chooserPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void init(JDialog frame){
        groupBox = new JComboBox<>();
        groupBox.setFont(custom_font);
        for(GroupOfProducts g: db.getGroups().getListOfGroups()){
            groupBox.addItem(g);
        }
        groupBox.setSelectedItem(null);
        groupBox.setMaximumRowCount(5);
        groupBox.setPreferredSize(new Dimension((int) (WIDTH/2.2), HEIGHT/5));
        okButton = new JButton("Ok");
        okButton.setFont(custom_font);
        okButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupOfProducts g = (GroupOfProducts) groupBox.getSelectedItem();
                if(g!=null){
                    chosenGroup = g;
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }else{
                    JOptionPane.showMessageDialog(frame, "Please choose a group", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(custom_font);
        cancelButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenGroup = null;
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        chooserPanel = new JPanel(new GridLayout(1, 2));
        {
            JLabel temp = new JLabel("Group", JLabel.CENTER);
            temp.setFont(custom_font);
            chooserPanel.add(temp);
        }
        {
            JPanel temp = new JPanel(new GridBagLayout());
            temp.add(groupBox);
            chooserPanel.add(temp);
        }
      //  chooserPanel.add(groupBox);
        buttonPanel = new JPanel(new GridLayout(1, 2));
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

    public GroupOfProducts getChosenGroup(){
        return chosenGroup;
    }
}
