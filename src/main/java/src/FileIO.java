package src;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import java.io.*;


public class FileIO
{

    protected String Open(File file) throws IOException, TikaException, SAXException
    {
        // Loading a config to suppress the optional warnings for OCR and SQL dependencies, which are not used
        File tikaConf = new File(FileIO.class.getResource("tika-config.xml").getFile());
        TikaConfig config = new TikaConfig(tikaConf);
        InputStream is = new FileInputStream(file);
        Tika tika = new Tika(config);
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
