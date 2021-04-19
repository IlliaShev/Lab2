package Interface;

import dto.GroupOfProducts;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SetGroup extends JDialog {

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
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private DateBase db;
    private GroupOfProducts result;
    private final Font custom_font  = new Font("Courier New", Font.BOLD, 14);


    public SetGroup(JFrame owner, String title, DateBase db, GroupOfProducts group){
        super(owner, true);
        this.db = db;
        setTitle(title);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((screenSize.width-WIDTH)/2, (screenSize.height - HEIGHT)/2,WIDTH, HEIGHT);
        setResizable(false);
        init(this, group);
        setLayout(new BorderLayout());
        add(setPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void init(JDialog frame, GroupOfProducts group){
        boolean isEditing = !(group==null);
        nameField = new JTextField();
        nameField.setFont(custom_font);
        if(isEditing) {
            nameField.setText(group.getNameOfGroup());
        }
        nameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
             if(e.getKeyChar()!=' '&&e.getKeyChar()!='_'&&e.getKeyChar()!='\''&&!Character.isLetter(e.getKeyChar()))
                 e.consume();
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(nameField.getText() != null&&!nameField.getText().equals("")&&isCorrectName(nameField.getText()))){
                    nameField.setText("Name");
                    nameField.setFont(custom_font);
                    JOptionPane.showMessageDialog(null, "Некоректне ім\'я", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        descriptionField = new JTextField();
        descriptionField.setFont(custom_font);
        if(isEditing){
            descriptionField.setText(group.getDescription());
        }
        setPanel = new JPanel(new GridLayout(2, 2));
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
        okButton = new JButton("Ok");
        okButton.setFont(custom_font);
        okButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameField.getText()!=null&&!nameField.getText().equals("")&&isCorrectName(nameField.getText())){
                    boolean temp = true;
                    for(GroupOfProducts g: db.getGroups().getListOfGroups()){
                        if(g.getNameOfGroup().equals(nameField.getText())&&(group==null||!group.getNameOfGroup().equals(nameField.getText()))){
                            temp = false;
                            break;
                        }
                    }
                    if(temp){
                        result = new GroupOfProducts(nameField.getText(), (descriptionField.getText()==null||descriptionField.getText().equals(""))?"-":descriptionField.getText());
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    }else{
                        JOptionPane.showMessageDialog(null, "Таке ім\'я вже існує", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Введіть кореткне ім\'я", "Error", JOptionPane.WARNING_MESSAGE);
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

    public GroupOfProducts getResult(){
        return result;
    }

    private static boolean isCorrectName(String name){
        for(Character c: name.toCharArray()){
            if(!(Character.isLetter(c)||c==' '||c=='_'||c=='\''||c=='`')){
                return false;
            }
        }
        return true;
    }
}
