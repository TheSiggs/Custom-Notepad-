package src;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

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
            new FileChooser.ExtensionFilter("OpenOffice Text", "*.odt"),
            new FileChooser.ExtensionFilter("All", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(window);

        // If user presses cancel or closes dialog return null to cancel loading process
        if(selectedFile == null)
        {
            return null;
        }


        try(InputStream is = new FileInputStream(selectedFile);){
            Tika tika = new Tika();
            return tika.parseToString(is);
        } catch (IOException | TikaException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error attempting to load file");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
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
