import static org.junit.jupiter.api.Assertions.*;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXException;
import src.FileIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ioTest
{

    // test file uses CRLF newlines, and both append/insertText methods append a LF newline at the end of the text
    private final String text = "File for unit test\r\n" + " Prepended space\r\n" + "Paragraph 2\n";

    @TempDir
    static Path tempDir;

    @Test
    void openTest() throws TikaException, IOException, SAXException
    {
        File file = new File(getClass().getClassLoader().getResource("test.txt").getFile());
        assertEquals(new FileIO().Open(file), text);
    }

    @Test
    void saveTest() throws IOException
    {
        Path path = tempDir.resolve("savefile.txt");
        // Trim text as opening it will insert the new line
        new FileIO().Save(text.trim(), path.toFile());
        assertAll(
            () -> assertTrue(Files.exists(tempDir)),
            () -> assertEquals(new FileIO().Open(path.toFile()), text));
    }

    @Test
    void overwriteTest() throws IOException, TikaException, SAXException
    {
        String newTest = "This single line should replace all other lines";
        File file = new File(tempDir.toFile(), "saveFile.txt");
        new FileIO().Save(newTest, file);
        assertEquals(new FileIO().Open(file), newTest + "\n");
    }

    @Test
    void noFileTest() throws TikaException, SAXException
    {
        File file = new File("nonfile");
        assertThrows(IOException.class,
            () ->new FileIO().Open(file));
    }
}
