import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Search;

public class searchTest
{
    Search search;
    String text = "Paragraph 1 \r\n" + "Paragraph 1 Character12\r\n" + "Paragraph 1 \n";

    @BeforeEach
    void setUp()
    {
        search = new Search();
    }

    @Test
    void findSingleTest()
    {
        search.updateMatch("Character12", text);
        assertEquals(1, search.getResultNum());
    }

    @Test
    void findSingleValidPositionTest()
    {
        search.updateMatch("Character12", text);
        int[] result = search.nextPosition(new int[]{0, 0});

        assertAll(
            () -> assertEquals(1, result[0]),
            () -> assertEquals(12, result[1]),
            () -> assertEquals(11, result[2])
        );
    }

    @Test
    void findMultipleTest()
    {
        search.updateMatch("Paragraph", text);
        assertEquals(3, search.getResultNum());
    }

    @Test
    void findMultiplePositionTest()
    {
        search.updateMatch("Paragraph", text);
        int[] firstResult = search.nextPosition(new int[]{0, 0});
        // Use first result set caret position to what should be the end position
        int[] secondResult = search.nextPosition(new int[]{firstResult[0], firstResult[1] + firstResult[2]});

        assertAll(
            () -> assertEquals(1, secondResult[0]),
            () -> assertEquals(0, secondResult[1]),
            () -> assertEquals(9, secondResult[2])
        );
    }

    @Test
    void noResult()
    {
        search.updateMatch("no match", text);
        assertNull(search.nextPosition(new int[]{0, 0}));
    }
}
