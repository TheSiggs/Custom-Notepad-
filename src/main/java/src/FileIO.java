package src;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class FileIO
{

    protected String Open(Window window)
    {

        // Setting up file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(window);

        // Select correct loader for file type, docx to be implemented later
        try
        {
            String fileExtension = selectedFile.getName().split("\\.")[1];
            if (fileExtension.equals("txt"))
            {
                return OpenText(selectedFile);
            } else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid File Extension");
                alert.setContentText("Please check file and try again.");
                alert.showAndWait();
                return null;
            }
        } catch (NullPointerException e) // NullPointer will occur if user cancels open
        {
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

    private String OpenText(File file)
    {
        // String builder and buffered reader to allow for reading large text files
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String curLine = reader.readLine();

            while (curLine != null)
            {
                text.append(curLine).append("\n");
                curLine = reader.readLine();
            }
        } catch (IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error attempting to open");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return text.toString();
    }
}
