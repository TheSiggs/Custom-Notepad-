package src;

import javafx.stage.FileChooser;

import java.io.*;

public class FileIO
{

    protected String Open()
    {

        // Setting up file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String fileExtension = selectedFile.getName().split("\\.")[1];

        // Select correct loader for file type, docx to be implemented later
        if(fileExtension.equals("txt"))
        {
            return OpenText(selectedFile);
        }
        else{
            return "";
        }
    }

    private String OpenText(File file)
    {
        // String builder and buffered reader to allow for reading large text files
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String curLine = reader.readLine();

            while(curLine != null){
                text.append(curLine).append("\n");
                curLine = reader.readLine();
            }
        } catch (FileNotFoundException e) // TODO: proper warning boxes
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return text.toString();
    }
}
