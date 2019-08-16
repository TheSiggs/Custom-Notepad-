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
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(null);
        String fileExtension = selectedFile.getName().split("\\.")[1];

        // Select correct loader for file type, docx to be implemented later
        if (fileExtension.equals("txt"))
        {
            return OpenText(selectedFile);
        } else
        {
            return "";
        }
    }

    protected void Save(String text)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showSaveDialog(null);

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
            e.printStackTrace();
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
