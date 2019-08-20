package src;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.opendocument.OpenOfficeParser;
import org.apache.tika.sax.BodyContentHandler;
import org.bouncycastle.asn1.cms.MetaData;
import org.xml.sax.SAXException;

import java.io.*;

public class FileIO
{

    protected String Open(Window window)
    {

        // Setting up file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("OpenOffice Documents", "*.odt"));

        File selectedFile = fileChooser.showOpenDialog(window);

        Tika tika = new Tika();

        try (InputStream is = new FileInputStream(selectedFile))
        {
            return tika.parseToString(is);
        } catch (IOException | TikaException e){ // TODO: Proper error handling

        }
        return "";
    }

    protected void Save(String text, Window window)  // Needs to change to bool if save-on-change feature implemented
    {
        // Setting up file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(window);

        if (selectedFile == null)
        {
            return;
        }

        // Requirements only need .txt saving, if more file formats are supported the following should be spun off to
        // another method
        try
        {
            System.out.println(text);
            FileWriter fileWriter = new FileWriter(selectedFile);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(text);
            writer.close();
        } catch (IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error attempting to save");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
