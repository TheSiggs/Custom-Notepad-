package src;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.*;

public class FileIO
{

    protected String Open(File file) throws IOException, TikaException
    {
        InputStream is = new FileInputStream(file);
        Tika tika = new Tika();
        return tika.parseToString(is);

    }

    protected void Save(String text, File file) throws IOException  // Needs to change to bool if save-on-change feature implemented
    {
        System.out.println(text);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(text);
        writer.close();
    }
}
