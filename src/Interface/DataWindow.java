package Interface;

import dto.Product;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/** Frame to work with database */
public class DataWindow extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    /** DateBase field for this frame to work with */
    private DateBase db;

    /** Visual components */
    private JTextField searchField;
    private JPanel infoPanel;
    private JList<Product> productList;
    private JList<String> groupList;
    private JScrollPane productScroll;
    private JScrollPane groupScroll;
    private JTextArea infoTextArea;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton removeButton;

    private JMenuBar menuBar;

    /** Constructor with DateBase param */
    //public DataWindow(DateBase db){
        public DataWindow(){
      //  this.db = db;
        setTitle("DataBase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        init();
        initMenuBar(this);
        setJMenuBar(menuBar);
        BorderLayout layout = new BorderLayout();
        layout.setVgap(5);
        setLayout(layout);
        add(searchField, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    /** Initialization of visual components */
    private void init(){
        if(searchField == null){
            searchField = new JTextField("Search");
            searchField.setPreferredSize(new Dimension(7*WIDTH/8, HEIGHT/8));
            searchField.setSize(new Dimension(7*WIDTH/8, HEIGHT/8));
            searchField.setFont(new Font("Calibri", Font.PLAIN, 20));
        }
        if(infoTextArea == null){
            infoTextArea = new JTextArea("");
            infoTextArea.setPreferredSize(new Dimension(7*WIDTH/24, 6*HEIGHT/8));
            infoTextArea.setEditable(false);
        }
        if(productList == null){
            productList = new JList<>();
            //productList.setPreferredSize(new Dimension(7*WIDTH/24, 5*HEIGHT/8));
            productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        }
        if(productScroll == null){
            productScroll = new JScrollPane(productList);
            productScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            productScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
        if(groupList == null){
            groupList = new JList<>();
         //   groupList.setPreferredSize(new Dimension(7*WIDTH/24, 5*HEIGHT/8));
            groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        }
        if(groupScroll == null){
            groupScroll = new JScrollPane(groupList);
            groupScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            groupScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
        if(infoPanel == null){
            infoPanel = new JPanel();
            GridLayout layout = new GridLayout(1, 3);
            layout.setHgap(5);
            layout.setVgap(10);
            infoPanel.setLayout(layout);
            infoPanel.add(groupScroll);
            infoPanel.add(productScroll);
            infoPanel.add(infoTextArea);
        }
        if(addButton == null){
            addButton = new JButton("Add");
            addButton.setPreferredSize(new Dimension(WIDTH/5, HEIGHT/ 10));

        }
        if(removeButton == null){
            removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(WIDTH/5, HEIGHT/ 10));

        }
        if(buttonPanel == null){
            buttonPanel = new JPanel(new GridLayout(1, 2));
            JPanel temp = new JPanel();
            temp.setLayout(new FlowLayout(FlowLayout.RIGHT));
            temp.add(addButton);
            buttonPanel.add(temp);
            temp = new JPanel();
            temp.setLayout(new FlowLayout(FlowLayout.LEFT));
            temp.add(removeButton);
            buttonPanel.add(temp);
        }
    }

    private void initMenuBar(JFrame frame){
        if(menuBar == null){
            menuBar = new JMenuBar();
            JMenu file = new JMenu("File");
            menuBar.add(file);
            {
                JMenuItem open = new JMenuItem("Open");
                open.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem save = new JMenuItem("Save");
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem close = new JMenuItem("Close");
                close.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem exit = new JMenuItem("Exit");
                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    }
                });
                file.add(open);
                file.add(save);
                file.add(close);
                file.add(exit);
            }
            JMenu edit = new JMenu("Edit");
            {
                JMenu group = new JMenu("Group");
                JMenuItem add = new JMenuItem("Add");
                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem editItem = new JMenuItem("Edit");
                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem remove = new JMenuItem("Remove");
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                group.add(add);
                group.add(editItem);
                group.add(remove);
                edit.add(group);
            }
            {
                JMenu product = new JMenu("Product");
                JMenuItem add = new JMenuItem("Add");
                add.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem editItem = new JMenuItem("Edit");
                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                JMenuItem remove = new JMenuItem("Remove");
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { }
                });
                product.add(add);
                product.add(editItem);
                product.add(remove);
                edit.add(product);
            }
            menuBar.add(edit);
            JMenuItem info = new JMenuItem("Info");
            info.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { }
            });
            menuBar.add(info);
        }
    }


    public static void main(String[] args){
        DataWindow ui = new DataWindow();
        ui.setVisible(true);
    }
}
