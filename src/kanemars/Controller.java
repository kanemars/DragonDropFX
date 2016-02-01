package kanemars;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TabPane tabPanes;
    int mouseDownStart;
    static String newline = "\n";
    static int newlineLength = newline.length();
    final Clipboard clipboard = Clipboard.getSystemClipboard();

//    public void mouseReleased(Event event) {
//        mouseDownStart = textArea.getCaretPosition();
//        String lineSelected = selectLine();
//        if (lineSelected.length() > 0) {
//            final ClipboardContent content = new ClipboardContent();
//            content.putString(lineSelected);
//            clipboard.setContent(content);
//        }
//    }
//


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final String cutAndPasteFile = DragonDropMainApplication.parameters.get(0);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(cutAndPasteFile)));
            String strLine = br.readLine();

            if (strLine != null) {
                TextArea list;
                if (strLine.startsWith("#")) {
                    list = addTab(strLine.substring(1));
                } else {
                    list = addTab("Main");
                    list.appendText(strLine + newline);
                }
                while ((strLine = br.readLine()) != null) {
                    if (strLine.startsWith("#")) {
                        list = addTab(strLine.substring(1));
                    } else {
                        list.appendText(strLine + newline);
                    }
                }
            }
        } catch (IOException ex) {

        }
    }

    private TextArea addTab(String tabTitle) {
        TextArea newArea = addTextArea ();

        Tab tab = new Tab(tabTitle);
        tabPanes.getTabs().add(tab);
        tab.setContent(newArea);
        return newArea;
    }

    private TextArea addTextArea () {
        TextArea newArea = new TextArea();

        newArea.setOnMouseClicked((MouseEvent event) -> {
            if(newArea.getSelectedText().length() == 0) {
                int caret = newArea.getCaretPosition();
                int lineStart = caret;
                int lineEnd = caret;
                while (lineStart > 0 && !newArea.getText(lineStart, lineEnd).startsWith(newline))
                    lineStart--;

                // Adjustments
                if (newArea.getText(lineStart, lineEnd).startsWith(newline))
                    lineStart += newlineLength;

                lineEnd = newArea.getText().indexOf(newline, lineEnd);

                newArea.selectRange(lineStart, lineEnd);

            }
            copyToClipboard(newArea.getSelectedText().trim());

        });
        return newArea;
    }

    private void copyToClipboard (String s) {
        if (s.length() > 0) {
            final ClipboardContent content = new ClipboardContent();
            content.putString(s);
            clipboard.setContent(content);
        }
    }
}
