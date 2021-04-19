package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class InfoWindow extends JDialog {
    /**
     * WIDTH
     */
    private static final int WIDTH = 400;
    /**
     * HEIGHT
     */
    private static final int HEIGHT = 300;
    private JTextArea info;
    private JScrollPane infoScroll;
    private JButton okButton;
    private final Font custom_font  = new Font("Courier New", Font.BOLD, 14);


    public InfoWindow(String title, String infoString){
        super((Frame) null, true);
        setTitle(title);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setBounds((screenSize.width-WIDTH)/2, (screenSize.height - HEIGHT)/2,WIDTH, HEIGHT);
        setResizable(false);
        init(this);
        info.setText(infoString);
        setLayout(new BorderLayout());
        add(infoScroll, BorderLayout.CENTER);
        {
            JPanel temp = new JPanel();
            temp.add(okButton);
            add(temp, BorderLayout.SOUTH);
        }
    }

    private void init(JDialog frame){
        info = new JTextArea();
        info.setFont(custom_font);
        info.setEditable(false);
        infoScroll = new JScrollPane(info);
        infoScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        okButton = new JButton("Ok");
        okButton.setFont(custom_font);
        okButton.setPreferredSize(new Dimension(WIDTH/3, HEIGHT/6));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}
