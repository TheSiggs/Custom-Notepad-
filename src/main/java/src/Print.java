package src;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;

import java.util.ArrayList;

public class Print
{
    private String text;
    private PrinterJob printer;
    private double pHeight;
    private double pWidth;
    private String[] toPrint;

    public Print(String text)
    {
        this.text = text;
        this.printer = PrinterJob.createPrinterJob();
    }

    public boolean setPrintSettings(Window window)
    {

        if (printer.showPageSetupDialog(window))
        {
            this.pHeight = printer.getJobSettings().getPageLayout().getPrintableHeight();
            this.pWidth = printer.getJobSettings().getPageLayout().getPrintableWidth();
            return true;
        } else
        {
            return false;
        }
    }

    public void print(Window window)
    {
        buildDoc();
        PageRange pageRange = new PageRange(1, toPrint.length);
        printer.getJobSettings().setPageRanges(pageRange);
        if (printer.showPrintDialog(window))

        {
            boolean sPrint = false;
            PageLayout layout = printer.getJobSettings().getPageLayout();
            for (PageRange pr : printer.getJobSettings().getPageRanges())
            {
                for (int p = pr.getStartPage(); p <= pr.getEndPage(); p++)
                {
                    Text page = new Text(toPrint[p - 1]);
                    page.setFont(Font.font("System", 11));
                    TextFlow printArea = new TextFlow(page);
                    printArea.setPrefHeight(layout.getPrintableHeight());
                    printArea.setPrefWidth(layout.getPrintableWidth());

                    sPrint = printer.printPage(layout, printArea);

                    if (!sPrint)
                    {
                        //TODO: error message
                        break;
                    }
                }
            }
            if (sPrint) printer.endJob();
        }

    }

    private void buildDoc()
    {
        // Set up font metrics
        Font font = new Font("System", 11);
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);

        // Use font metrics to calculate how many lines there are
        double pageLines = pHeight / fm.getLineHeight();

        String[] uncheckedLines = text.split("\n");
        ArrayList<String> paginatedDocs = new ArrayList<>();

        int lineNumber = 0;
        int pageNumber = 0;

        StringBuilder currentPage = new StringBuilder();

        for (String toTest : uncheckedLines)
        {
            if ((int) fm.computeStringWidth(toTest) < pWidth)
            {
                currentPage.append(toTest).append("\n");
                lineNumber++;
            } else
            {
                StringBuilder buildingLine = new StringBuilder();

                for (String s : toTest.split(""))
                {
                    String currentLine = buildingLine.toString();
                    if (fm.computeStringWidth(currentLine + s) < pWidth)
                    {
                        buildingLine.append(s);
                    } else
                    {
                        currentPage.append(buildingLine.toString());
                        buildingLine.setLength(0);
                        lineNumber++;

                        if (lineNumber == (int) pageLines)
                        {
                            paginatedDocs.add(pageNumber, currentPage.toString());
                            currentPage.setLength(0);
                            lineNumber = 0;
                            pageNumber++;
                        }
                    }
                }
                currentPage.append("\n");
            }
            if (lineNumber == (int) pageLines)
            {
                paginatedDocs.add(pageNumber, currentPage.toString());
                currentPage.setLength(0);
                lineNumber = 0;
                pageNumber++;
            }
        }
        paginatedDocs.add(pageNumber, currentPage.toString());
        this.toPrint = paginatedDocs.toArray(new String[0]);
    }
}
