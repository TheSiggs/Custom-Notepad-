package src;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.print.PageLayout;
import javafx.print.PageRange;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Optional;

public class Print
{
    private final PrinterJob printer;
    private double pHeight;
    private double pWidth;

    public Print()
    {
        this.printer = PrinterJob.createPrinterJob();
    }

    public boolean setPrintSettings(Window window)
    {
        // Check that user didn't press cancel or close window
        if (printer.showPageSetupDialog(window))
        {
            // Get the page height and width to the settings from the dialog settings
            this.pHeight = printer.getJobSettings().getPageLayout().getPrintableHeight();
            this.pWidth = printer.getJobSettings().getPageLayout().getPrintableWidth();
            return true;
        } else
        {
            return false;
        }
    }

    public void print(Window window, String text)
    {
        // Build paginated string array
        final String[] toPrint = buildDoc(text);

        // Set the page range
        PageRange pageRange = new PageRange(1, toPrint.length);
        printer.getJobSettings().setPageRanges(pageRange);
        if (printer.showPrintDialog(window))
        {
            boolean sPrint = false;
            PageLayout layout = printer.getJobSettings().getPageLayout();

            // Use page range to allow for printing duplicate pages
            for (PageRange pr : printer.getJobSettings().getPageRanges())
            {
                for (int p = pr.getStartPage(); p <= pr.getEndPage(); p++)
                {
                    // Set the current page text and set font
                    Text page = new Text(toPrint[p - 1]);
                    page.setFont(Font.font("System", 11));
                    TextFlow printArea = new TextFlow(page);

                    // Set printArea height and width to match print settings
                    printArea.setPrefHeight(layout.getPrintableHeight());
                    printArea.setPrefWidth(layout.getPrintableWidth());

                    sPrint = printer.printPage(layout, printArea);

                    if (!sPrint)
                    {
                        // If a page fails to have a job created create dialog to cancel print attempt;
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Error Printing");
                        alert.setHeaderText("Error Printing Page " + p);
                        alert.setContentText("Do you want to continue printing?");

                        ButtonType con = new ButtonType("Continue");
                        ButtonType cancel = new ButtonType("Cancel");

                        alert.getButtonTypes().setAll(con, cancel);
                        Optional<ButtonType> res = alert.showAndWait();

                        if (res.get() != con)
                        {
                            printer.cancelJob();
                            break;
                        }
                    }
                }
            }
            if (sPrint) printer.endJob();
        }
    }

    private String[] buildDoc(String text)
    {
        // Set up font metrics
        Font font = new Font("System", 11);
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);

        // Use font metrics to calculate how many lines there are
        double pageLines = pHeight / fm.getLineHeight();

        // Split the the text to be printed into paragraphs and create an arraylist to store paginated strings
        String[] uncheckedLines = text.split("\n");
        ArrayList<String> paginatedDocs = new ArrayList<>();

        int lineNumber = 0;

        StringBuilder currentPage = new StringBuilder();

        // Iterate over each paragraph to test
        for (String toTest : uncheckedLines)
        {
            // If a paragraph is less than the width of the page add it to the current page and
            if ((int) fm.computeStringWidth(toTest) < pWidth)
            {
                currentPage.append(toTest).append("\n");
                lineNumber++;
            } else
            {
                // If too large split the string and iterate over them
                StringBuilder buildingLine = new StringBuilder();
                for (String s : toTest.split(""))
                {
                    String currentLine = buildingLine.toString();
                    if (fm.computeStringWidth(currentLine + s) < pWidth)
                    {
                        // If the currently built up string plus the next string is less than width add the split string
                        // to string builder
                        buildingLine.append(s);
                    } else
                    {
                        // Otherwise add the string to the current page
                        currentPage.append(buildingLine.toString());
                        buildingLine.setLength(0);
                        lineNumber++;

                        if (lineNumber == (int) pageLines)
                        {
                            // If the end of the page has been reached add the current page to the arraylist
                            paginatedDocs.add(currentPage.toString());
                            currentPage.setLength(0);
                            lineNumber = 0;
                        }
                    }
                }
                currentPage.append("\n");
            }
            if (lineNumber == (int) pageLines)
            {
                // If the end of the page has been reached add the current page to the arraylist
                paginatedDocs.add(currentPage.toString());
                currentPage.setLength(0);
                lineNumber = 0;
            }
        }
        paginatedDocs.add(currentPage.toString());
        return paginatedDocs.toArray(new String[0]);
    }
}
