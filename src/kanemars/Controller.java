package kanemars;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextArea textArea;
    public TabPane tabPanes;
    int mouseDownStart;
    static String newline = "\n";
    static int newlineLength = newline.length();
    final Clipboard clipboard = Clipboard.getSystemClipboard();

    public void mouseReleased(Event event) {
        mouseDownStart = textArea.getCaretPosition();
        String lineSelected = selectLine();
        if (lineSelected.length() > 0) {
            final ClipboardContent content = new ClipboardContent();
            content.putString(lineSelected);
            clipboard.setContent(content);
        }
    }

    private String selectLine() {
        int lineStart = mouseDownStart;
        int lineEnd = mouseDownStart;
        String text = textArea.getText();
        while (lineStart > 0 && !textArea.getText(lineStart, lineEnd).startsWith(newline))
            lineStart--;

        // Adjustments
        if (textArea.getText(lineStart, lineEnd).startsWith(newline))
            lineStart += newlineLength;

        // styledText.getText(lineStart, end)
        lineEnd = textArea.getText().indexOf(newline, lineEnd);

        return textArea.getText(lineStart, lineEnd).trim();

    }

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
        Tab tab = new Tab(tabTitle);
        tabPanes.getTabs().add(tab);
        return textArea;
    }
}
