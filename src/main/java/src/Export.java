package src;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Export
{

    private PDDocument document;
    private float marginX;
    private float marginY;
    private ArrayList<PDPage> pages;
    private PDPageContentStream contentStream;

    Export()
    {
        this.document = new PDDocument();
        this.pages = new ArrayList<>();
        this.marginX = 64;
        this.marginY = 52;
    }

    void writePDF(String text, File file) throws IOException
    {
        // Split the text into paragraphs
        String[] paragraphs = text.split("\n");

        // Create the first page and from it derive margin locations, page dimensions , font height etc
        updateStream();
        PDPage currentPage = pages.get(pages.size() - 1);
        PDRectangle mediaRect = currentPage.getMediaBox();
        float xOffset = marginX + mediaRect.getLowerLeftX();
        float yOffset = mediaRect.getUpperRightY() - marginY;
        float width = mediaRect.getWidth() - 2 * marginX;
        float height = PDRectangle.A4.getHeight() - 2 * marginY;
        float fontHeight = PDType1Font.HELVETICA.getFontDescriptor().getFontBoundingBox().getHeight() * 11 / 1000 + 1.5f;

        // Set a counter to measure height for pagination and an array to hold each pages lines
        float currentHeight = 0;
        ArrayList<String> page = new ArrayList<>();

        for (String para : paragraphs)
        {
            for (String line : makeLines(para, width)) // Parse paragraphs into lines that fit document width
            {
                if (Math.ceil(currentHeight + fontHeight + marginY) >= Math.floor(height))
                {
                    // If document height would be exceeded write the current page, and set up contentStream and
                    // arraylist for new page
                    writePage(page, xOffset, yOffset);
                    contentStream.close();
                    updateStream();
                    page.clear();
                    currentHeight = 0;
                }
                page.add(line);
                currentHeight += (fontHeight + 1.5f);
            }
        }
        if (page.size() > 0) writePage(page, xOffset, yOffset); // If a partial page exists write it
        contentStream.close();
        document.save(file);
        document.close();
    }

    private void writePage(ArrayList<String> page, float xOffset, float yOffset) throws IOException
    {
        // Setup content stream for writing lines
        contentStream.beginText();
        contentStream.newLineAtOffset(xOffset, yOffset);
        contentStream.setCharacterSpacing(0);
        for (String line : page)
        {
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, -1.5f * 11);
        }
        contentStream.endText();
    }

    private ArrayList<String> makeLines(String text, float width) throws IOException
    {
        // Split words by space
        String[] words = text.split(" ");
        ArrayList<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (String word : words)
        {
            // Get width of the currently saved line plus the next word
            float newLineWidth = PDType1Font.HELVETICA.getStringWidth(line.toString() + " " + word) * 11 / 1000;

            // Either append the word if it fits or add the line to the arraylist and being a new line
            if (newLineWidth <= width)
            {
                line.append(" ").append(word);
            } else
            {
                lines.add(line.toString());
                line.setLength(0);
                line.append(word);
            }
        }
        lines.add(line.toString()); // Add final line to string

        return lines;
    }

    private void updateStream() throws IOException
    {
        // Create a new page, and create a content stream for it with the font set
        PDPage newPage = new PDPage();
        pages.add(newPage);
        document.addPage(newPage);
        PDPageContentStream updatedStream = new PDPageContentStream(document, pages.get(pages.size() - 1));
        updatedStream.setFont(PDType1Font.HELVETICA, 11);
        contentStream = updatedStream;
    }
}

