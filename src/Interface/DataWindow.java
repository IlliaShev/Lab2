package Interface;

import dto.GroupOfProducts;
import dto.Product;
import utils.DateBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

/**
 * Frame to work with database
 */
public class DataWindow extends JFrame {

    /**
     * Constants for window size WIDTH
     */
    private static final int WIDTH = 600;
    /**
     * Constants for window size HEIGHT
     */
    private static final int HEIGHT = 400;

    /**
     * DateBase field for this frame to work with
     */
    final private DateBase db;

    //Visual components

    /**
     * JTextField searchField
     */
    private JTextField searchField;
    /**
     * JPanel infoPanel
     */
    private JPanel infoPanel;
    /**
     * JList productList
     */
    private JList<Product> productList;
    /**
     * JList groupList
     */
    private JList<GroupOfProducts> groupList;
    /**
     * JScrollPane productScroll
     */
    private JScrollPane productScroll;
    /**
     * DefaultListModel productModel
     */
    private DefaultListModel<Product> productModel;
    /**
     * DefaultListModel groupModel
     */
    private DefaultListModel<GroupOfProducts> groupModel;
    /**
     * JScrollPane groupScroll
     */
    private JScrollPane groupScroll;
    /**
     *  JTextArea infoTextArea
     */
    private JTextArea infoTextArea;
    /**
     * JPanel buttonPanel
     */
    private JPanel buttonPanel;
    /**
     * JButton addButton
     */
    private JButton addButton;
    /**
     * JButton removeButton
     */
    private JButton removeButton;
    /**
     * JFileChooser fileChooser
     */
    private JFileChooser fileChooser;
    /**
     * boolean isSearching
     */
    private boolean isSearching = false;
    /**
     * List searchResults
     */
    private List<Product> searchResults;

    /**
     * Menu bar field
     */
    private JMenuBar menuBar;

    private final Font custom_font  = new Font("Courier New", Font.BOLD, 16);

    /**
     * Constructor with DateBase param
     * @param db  Database
     */
    public DataWindow(DateBase db) {
        this.db = db;
        setTitle("Workspace");
        setFont(custom_font);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((screenSize.width-WIDTH)/2, (screenSize.height - HEIGHT)/2,WIDTH, HEIGHT);
        setResizable(false);
        init(this);
        initMenuBar(this);
        setJMenuBar(menuBar);
        BorderLayout layout = new BorderLayout();
        layout.setVgap(5);
        setLayout(layout);
        {
            JPanel temp = new JPanel();
            temp.add(searchField);
            add(temp, BorderLayout.NORTH);
        }
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        revalidate();
        setVisible(true);
    }

    /**
     * init
     * @param frame JFrame
     */
    private void init(JFrame frame) {
        if (searchField == null) {
            searchField = new JTextField("Search");
            searchField.setSize(new Dimension(7 * WIDTH / 8, HEIGHT / 8));
            searchField.setFont(custom_font);
            searchField.setPreferredSize(new Dimension( 19*WIDTH/20 , HEIGHT / 8));
            searchField.addFocusListener(new FocusListener() {
                private boolean isUntouched = true;

                @Override
                public void focusGained(FocusEvent e) {
                    if (isUntouched) {
                        searchField.setText("");
                        isUntouched = false;
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (searchField.getText() == null || searchField.getText().equals("") || isUntouched) {
                        isUntouched = true;
                        searchField.setText("Search");
                    }
                }
            });
            searchField.addActionListener(e -> {
                if (searchField.getText() != null && !searchField.getText().equals("")) {
                    isSearching = true;
                    searchResults = db.getGroups().findProduct(searchField.getText());
                    if (searchResults != null && searchResults.size() != 0) {
                        groupModel.clear();
                        for (Product p : searchResults) {
                            if (!groupModel.contains(p.getGroup()))
                                groupModel.addElement(p.getGroup());
                        }
                    } else {
                        groupModel.clear();
                        productModel.clear();
                    }
                } else {
                    searchResults = null;
                    isSearching = false;
                    refreshGroupList();
                }
            });
        }
        if (infoTextArea == null) {
            infoTextArea = new JTextArea("");
            infoTextArea.setFont(custom_font);
            infoTextArea.setEditable(false);
            infoTextArea.setFocusable(true);
        }
        if (productList == null) {
            productModel = new DefaultListModel<>();
            productList = new JList<>(productModel);
            productList.setFont(custom_font);
            productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            productList.addListSelectionListener(e -> {
                Product p = productList.getSelectedValue();
                infoTextArea.setText("");
                if (p != null) {
                    infoTextArea.append("Product name: " + p.getName() + "\n" + "Product producer: " + p.getProducer() + "\n" +
                            "Product description: " + p.getDescription() + "\n" + "Product price: " + p.getPrice() + "\n" +
                            "Product amount in stock: " + groupList.getSelectedValue().getValue(p) + "\n" + "Total value of product in stock: " +
                            ((double) (Math.round(p.getPrice() * groupList.getSelectedValue().getValue(p) * 100)) / 100));
                }
            });
        }
        if (productScroll == null) {
            productScroll = new JScrollPane(productList);
            productScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            productScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
        if (groupList == null) {
            groupModel = new DefaultListModel<>();
            groupList = new JList<>(groupModel);
            groupList.setFont(custom_font);
            groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            for (GroupOfProducts g : db.getGroups().getListOfGroups()) {
                groupModel.addElement(g);
            }
            groupList.addListSelectionListener(e -> {
                if (!isSearching) {
                    refreshProductList();
                } else {
                    productModel.clear();
                    if (groupList.getSelectedValue() != null) {
                        for (Product p : groupList.getSelectedValue().getListOfProducts()) {
                            if (searchResults.contains(p)) {
                                productModel.addElement(p);
                            }
                        }
                    } else {
                        productModel.clear();
                    }
                }
            });
        }
        if (groupScroll == null) {
            groupScroll = new JScrollPane(groupList);
            groupScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            groupScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }
        if (infoPanel == null) {
            infoPanel = new JPanel();
            JScrollPane infoTextAreaScroll = new JScrollPane(infoTextArea);
            infoPanel.setFont(custom_font);
            GridLayout layout = new GridLayout(1, 3);
            layout.setHgap(5);
            layout.setVgap(10);
            infoPanel.setLayout(layout);
            infoPanel.add(groupScroll);
            infoPanel.add(productScroll);
            infoPanel.add(infoTextAreaScroll);
        }
        if (addButton == null) {
            addButton = new JButton("Add");
            addButton.setFont(custom_font);
            addButton.setPreferredSize(new Dimension(WIDTH / 5, HEIGHT / 10));
            addButton.addActionListener(e -> {
                if(db.getGroups().isEmpty()){
                    JOptionPane.showMessageDialog(null, "There is no products in stock");
                    return;
                }
                ChangeValueWindow cvw = new ChangeValueWindow(frame, db, true);
                cvw.setVisible(true);
                groupList.clearSelection();
                refreshProductList();
            });
        }
        if (removeButton == null) {
            removeButton = new JButton("Remove");
            removeButton.setFont(custom_font);
            removeButton.setPreferredSize(new Dimension(WIDTH / 5, HEIGHT / 10));
            removeButton.addActionListener(e -> {
                if(db.getGroups().isEmpty()){
                    JOptionPane.showMessageDialog(null, "There is no products in stock");
                    return;
                }
                ChangeValueWindow cvw = new ChangeValueWindow(frame, db, false);
                cvw.setVisible(true);
                groupList.clearSelection();
                refreshProductList();
            });
        }
        if (buttonPanel == null) {
            buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.setFont(custom_font);
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

    /**
     * Update product list
     */
    private void refreshProductList() {
        infoTextArea.setText("");
        productModel.clear();
        if (groupList.getSelectedValue() != null) {
            if (!isSearching) {
                for (Product p : groupList.getSelectedValue().getListOfProducts()) {
                    productModel.addElement(p);
                }
            } else {
                for (Product p : groupList.getSelectedValue().getListOfProducts()) {
                    if (searchResults.contains(p)) {
                        productModel.addElement(p);
                    }
                }
            }
        }
    }

    /**
     * Update group list
     */
    private void refreshGroupList() {
        groupModel.clear();
        for (GroupOfProducts g : db.getGroups().getListOfGroups()) {
            groupModel.addElement(g);
        }
        groupList.grabFocus();
    }

    /**
     * Initialization of menubar with listeners
     *
     * @param frame - main frame
     */
    private void initMenuBar(JFrame frame) {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            menuBar.setFont(custom_font);
            JMenu file = new JMenu("File");
            file.setFont(custom_font);
            menuBar.add(file);
            {
                JMenuItem save = new JMenuItem("Save");
                save.setFont(custom_font);
                save.addActionListener(e -> {
                    fileChooser = new JFileChooser(".");
                    fileChooser.setFont(custom_font);
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int response = fileChooser.showSaveDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        try {
                            File fileToSave = fileChooser.getSelectedFile();
                            if(fileToSave.isDirectory()){
                                fileToSave = new File(fileToSave.getAbsolutePath()+"\\out.txt");
                            }
                            BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave));
                            bw.write(db.getGroups().getAllInfo());
                            bw.close();
                        } catch(FileNotFoundException ex){
                            JOptionPane.showMessageDialog(frame, "Choose correct file", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                });
                JMenuItem exit = new JMenuItem("Exit");
                exit.setFont(custom_font);
                exit.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
                file.add(save);
                file.add(exit);
            }
            JMenu edit = new JMenu("Edit");
            edit.setFont(custom_font);
            {
                JMenu group = new JMenu("Group");
                group.setFont(custom_font);
                JMenuItem add = new JMenuItem("Add");
                add.setFont(custom_font);
                add.addActionListener(e -> {
                    SetGroup setter = new SetGroup(frame, "Add group", db, null);
                    setter.setVisible(true);
                    if(setter.getResult()!=null){
                        db.getGroups().addGroup(setter.getResult());
                        refreshGroupList();
                    }
                    if(isSearching){
                        isSearching = false;
                        searchResults = null;
                        searchField.setText("");
                        groupList.grabFocus();
                    }
                    refreshGroupList();
                });
                JMenuItem editItem = new JMenuItem("Edit");
                editItem.setFont(custom_font);
                editItem.addActionListener(e -> {
                    if(db.getGroups().getListOfGroups().size()==0){
                        JOptionPane.showMessageDialog(null, "There is no group of products in stock");
                        return;
                    }
                    ChooseGroup chooser = new ChooseGroup(frame, "Edit group", db);
                    chooser.setVisible(true);
                    if(chooser.getChosenGroup()!=null){
                        SetGroup setter = new SetGroup(frame, "Edit group", db, chooser.getChosenGroup());
                        setter.setVisible(true);
                        if(setter.getResult()!=null){
                            chooser.getChosenGroup().setNameOfGroup(setter.getResult().getNameOfGroup());
                            chooser.getChosenGroup().setDescription(setter.getResult().getDescription());
                            refreshGroupList();
                        }
                    }
                    if(isSearching){
                        isSearching = false;
                        searchResults = null;
                        searchField.setText("");
                        groupList.grabFocus();
                    }
                    refreshGroupList();
                });
                JMenuItem remove = new JMenuItem("Remove");
                remove.setFont(custom_font);
                remove.addActionListener(e -> {
                    if(db.getGroups().getListOfGroups().size()==0){
                        JOptionPane.showMessageDialog(null, "There is no group of products in stock");
                        return;
                    }
                    ChooseGroup chooser = new ChooseGroup(frame, "Remove group", db);
                    chooser.setVisible(true);
                    db.getGroups().deleteGroup(chooser.getChosenGroup());
                    if(isSearching){
                        isSearching = false;
                        searchResults = null;
                        searchField.setText("");
                        groupList.grabFocus();
                    }
                    refreshGroupList();
                });
                group.add(add);
                group.add(editItem);
                group.add(remove);
                edit.add(group);
            }
            {
                JMenu product = new JMenu("Product");
                product.setFont(custom_font);
                JMenuItem add = new JMenuItem("Add");
                add.setFont(custom_font);
                add.addActionListener(e -> {
                    if(db.getGroups().getListOfGroups().size()==0){
                        JOptionPane.showMessageDialog(null, "There is no group of products in stock");
                        return;
                    }
                    ChooseGroup chooser = new ChooseGroup(frame, "Choose group", db);
                    chooser.setVisible(true);
                    if(chooser.getChosenGroup()!=null){
                        SetProduct setter = new SetProduct(frame, "Add product", db, null);
                        setter.setVisible(true);
                        if(setter.getResult()!=null){
                            chooser.getChosenGroup().addProduct(setter.getResult());
                            refreshGroupList();
                        }
                    }
                    if(isSearching){
                        isSearching = false;
                        searchResults = null;
                        searchField.setText("");
                        groupList.grabFocus();
                    }
                    refreshGroupList();
                });
                JMenuItem editItem = new JMenuItem("Edit");
                editItem.setFont(custom_font);
                editItem.addActionListener(e -> {
                    if(db.getGroups().isEmpty()){
                        JOptionPane.showMessageDialog(null, "There is no products in stock");
                        return;
                    }
                    ChooseProduct chooser = new ChooseProduct(frame, "Edit product", db);
                    chooser.setVisible(true);
                    if(chooser.getResult()!=null){
                        SetProduct setter = new SetProduct(frame, "Edit product", db, chooser.getResult());
                        setter.setVisible(true);
                        if(setter.getResult()!=null){
                            Product p = chooser.getResult();
                            p.setName(setter.getResult().getName());
                            p.setDescription(setter.getResult().getDescription());
                            p.setProducer(setter.getResult().getProducer());
                            p.setPrice(setter.getResult().getPrice());
                        }
                    }
                    if(isSearching){
                        isSearching = false;
                        searchResults = null;
                        searchField.setText("");
                        groupList.grabFocus();
                    }
                    refreshGroupList();
                });
                JMenuItem remove = new JMenuItem("Remove");
                remove.setFont(custom_font);
                remove.addActionListener(e -> {
                    if(db.getGroups().isEmpty()){
                        JOptionPane.showMessageDialog(null, "There is no products in stock");
                        return;
                    }
                    ChooseProduct chooser = new ChooseProduct(frame, "Remove product", db);
                    chooser.setVisible(true);
                    if(chooser.getResult()!=null){
                        chooser.getResult().getGroup().deleteProduct(chooser.getResult());
                    }
                    if(isSearching){
                        isSearching = false;
                        searchResults = null;
                        searchField.setText("");
                        groupList.grabFocus();
                    }
                    refreshGroupList();
                });
                product.add(add);
                product.add(editItem);
                product.add(remove);
                edit.add(product);
            }
            menuBar.add(edit);
            JMenu info = new JMenu("Info");
            info.setFont(custom_font);
            {
                JMenuItem infoGroup = new JMenuItem("Group");
                infoGroup.setFont(custom_font);
                infoGroup.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(db.getGroups().getListOfGroups().size()==0){
                            JOptionPane.showMessageDialog(null, "There is no group of products in stock");
                            return;
                        }
                        ChooseGroup chooser = new ChooseGroup(frame, "Info about group", db);
                        chooser.setVisible(true);
                        if(chooser.getChosenGroup()!=null){
                            InfoWindow info = new InfoWindow("Group info", chooser.getChosenGroup().getGroupInfo());
                            info.setVisible(true);
                        }
                    }
                });
                JMenuItem infoStock = new JMenuItem("Stock");
                infoStock.setFont(custom_font);
                infoStock.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(db.getGroups().isEmpty()){
                            JOptionPane.showMessageDialog(null, "There is no products in stock");
                            return;
                        }
                        InfoWindow info = new InfoWindow("Stock info", db.getGroups().getAllProducts());
                        info.setVisible(true);
                    }
                });
                info.add(infoGroup);
                info.add(infoStock);
            }
            menuBar.add(info);
        }
    }

}
