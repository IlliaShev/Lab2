import Interface.DataWindow;
import utils.DateBase;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        DateBase test = new DateBase();
        DataWindow ui = new DataWindow(test);
    }
}
