package kanemars;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    public TextArea textArea;
    int mouseDownStart;
    static String newline = "\n";
    static int newlineLength = newline.length();
    final Clipboard clipboard = Clipboard.getSystemClipboard();
    static Logger log = Logger.getLogger("DragonDropFX");

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
}
