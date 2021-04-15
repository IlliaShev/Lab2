package Interface;

import dto.GroupOfProducts;
import dto.Product;
import utils.DateBase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/** Frame to work with database */
public class DataWindow extends JFrame {

    /** Constants for window size */
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    /** DateBase field for this frame to work with */
    private DateBase db;

    /** Visual components */
    private JTextField searchField;
    private JPanel infoPanel;
    private JList<Product> productList;
    private JList<GroupOfProducts> groupList;
    private JScrollPane productScroll;
    private DefaultListModel<Product> productModel;
    private DefaultListModel<GroupOfProducts> groupModel;
    private JScrollPane groupScroll;
    private JTextArea infoTextArea;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton removeButton;
    private boolean isSearching = false;
    private List<Product> searchResults;

    /** Menu bar field */
    private JMenuBar menuBar;

    /** Constructor with DateBase param */
    public DataWindow(DateBase db){
        this.db = db;
        setTitle("DataBase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        init(this);
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
    private void init(JFrame frame){
        if(searchField == null){
            searchField = new JTextField("Search");
            searchField.setPreferredSize(new Dimension(7*WIDTH/8, HEIGHT/8));
            searchField.setSize(new Dimension(7*WIDTH/8, HEIGHT/8));
            searchField.setFont(new Font("Calibri", Font.PLAIN, 20));
            searchField.addFocusListener(new FocusListener() {
                private boolean isUntouched = true;

                @Override
                public void focusGained(FocusEvent e) {
                    if(isUntouched){
                        searchField.setText("");
                        isUntouched = false;
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(searchField.getText() == null||searchField.getText().equals("")||isUntouched){
                        isUntouched = true;
                        searchField.setText("Search");
                    }
                }
            });
            searchField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(searchField.getText()!= null&&!searchField.getText().equals("")) {
                        isSearching = true;
                        searchResults = db.getGroups().findProduct(searchField.getText());
                        if (searchResults != null && searchResults.size() != 0) {
                            groupModel.clear();
                            for(Product p: searchResults){
                                if(!groupModel.contains(p.getGroup()))
                                    groupModel.addElement(p.getGroup());
                            }
                        }else{
                            groupModel.clear();
                            productModel.clear();
                        }
                    }else{
                        searchResults = null;
                        isSearching = false;
                        refreshGroupList();
                    }
                }
            });
        }
        if(infoTextArea == null){
            infoTextArea = new JTextArea("");
            infoTextArea.setPreferredSize(new Dimension(7*WIDTH/24, 6*HEIGHT/8));
            infoTextArea.setEditable(false);
            infoTextArea.setWrapStyleWord(true);
            infoTextArea.setLineWrap(true);
            infoTextArea.setFocusable(false);
        }
        if(productList == null){
            productModel = new DefaultListModel<Product>();
            productList = new JList<>(productModel);
            //productList.setPreferredSize(new Dimension(7*WIDTH/24, 5*HEIGHT/8));
            productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            productList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Product p = productList.getSelectedValue();
                    if(p != null) {
                        infoTextArea.setText("");
                        infoTextArea.append("Product name: " + p.getName() + "\n" + "Product producer: " + p.getProducer() + "\n" +
                                "Product description: " + p.getDescription() + "\n" + "Product price: " + p.getPrice()+"\n"+
                                "Product amount in stock: "+groupList.getSelectedValue().getValue(p)+"\n"+"Total value of product in stock: "+
                                ((double)(Math.round(p.getPrice()*groupList.getSelectedValue().getValue(p)*100))/100));
                    }else{
                        infoTextArea.setText("");
                    }
                }
            });
        }
        if(productScroll == null){
            productScroll = new JScrollPane(productList);
            productScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            productScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
        if(groupList == null){
            groupModel = new DefaultListModel<GroupOfProducts>();
            groupList = new JList<>(groupModel);
         //   groupList.setPreferredSize(new Dimension(7*WIDTH/24, 5*HEIGHT/8));
            groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            for(GroupOfProducts g: db.getGroups().getListOfGroups()){
                groupModel.addElement(g);
            }
            groupList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!isSearching){
                        refreshProductList();
                    }else{
                        productModel.clear();
                        if(groupList.getSelectedValue()!= null) {
                            for (Product p : groupList.getSelectedValue().getListOfProducts()) {
                                if (searchResults.contains(p)) {
                                    productModel.addElement(p);
                                }
                            }
                        }else{
                            productModel.clear();
                        }
                    }
                }
            });
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
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ChangeValueWindow cvw = new ChangeValueWindow(frame, db, true);
                    cvw.setVisible(true);
                    groupList.clearSelection();
                    refreshProductList();
                }
            });
        }
        if(removeButton == null){
            removeButton = new JButton("Remove");
            removeButton.setPreferredSize(new Dimension(WIDTH/5, HEIGHT/ 10));
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ChangeValueWindow cvw = new ChangeValueWindow(frame, db, false);
                    cvw.setVisible(true);
                    groupList.clearSelection();
                    refreshProductList();
                }
            });
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

    private void refreshProductList(){
        infoTextArea.setText("");
        productModel.clear();
        if(groupList.getSelectedValue()!= null) {
            if(!isSearching) {
                for (Product p : groupList.getSelectedValue().getListOfProducts()) {
                    productModel.addElement(p);
                }
            }else{
                for (Product p : groupList.getSelectedValue().getListOfProducts()) {
                    if (searchResults.contains(p)) {
                        productModel.addElement(p);
                    }
                }
            }
        }
    }

    private void refreshGroupList(){
        groupModel.clear();
        for(GroupOfProducts g:db.getGroups().getListOfGroups()){
            groupModel.addElement(g);
        }
        groupList.grabFocus();
    }
    /**
     * Initialization of menubar with listeners
     *  @param frame - main frame
     */
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
        DateBase test = new DateBase();
        DataWindow ui = new DataWindow(test);
        ui.setVisible(true);
    }
}
