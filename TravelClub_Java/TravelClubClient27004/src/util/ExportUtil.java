package util;

import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

public class ExportUtil {

    //  CSV Export 
    public static void exportToCSV(JTable table, String reportTitle) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save CSV Report");
        chooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        chooser.setSelectedFile(new File(reportTitle.replaceAll(" ", "_") + "_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv"));

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (!file.getName().endsWith(".csv")) file = new File(file.getAbsolutePath() + ".csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("# " + reportTitle + " - Generated: " + new Date());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < table.getColumnCount(); i++) {
                sb.append(table.getColumnName(i));
                if (i < table.getColumnCount() - 1) sb.append(",");
            }
            pw.println(sb.toString());
            for (int r = 0; r < table.getRowCount(); r++) {
                sb = new StringBuilder();
                for (int c = 0; c < table.getColumnCount(); c++) {
                    Object val = table.getValueAt(r, c);
                    sb.append(val != null ? val.toString().replace(",", ";") : "");
                    if (c < table.getColumnCount() - 1) sb.append(",");
                }
                pw.println(sb.toString());
            }
            JOptionPane.showMessageDialog(null, "CSV exported successfully!\n" + file.getAbsolutePath(),
                    "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Export failed: " + e.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─── HTML Export ──────────────────────────────────────────────────────────
    public static void exportToHTML(JTable table, String reportTitle) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save HTML Report");
        chooser.setFileFilter(new FileNameExtensionFilter("HTML Files", "html"));
        chooser.setSelectedFile(new File(reportTitle.replaceAll(" ", "_") + "_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html"));

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (!file.getName().endsWith(".html")) file = new File(file.getAbsolutePath() + ".html");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
            pw.println("<title>" + reportTitle + "</title>");
            pw.println("<style>body{font-family:Arial;margin:20px;}h2{color:#2c3e50;}");
            pw.println("table{border-collapse:collapse;width:100%;}");
            pw.println("th{background:#2980b9;color:white;padding:8px;text-align:left;}");
            pw.println("td{padding:7px;border-bottom:1px solid #ddd;}");
            pw.println("tr:nth-child(even){background:#f2f2f2;}</style></head><body>");
            pw.println("<h2>" + reportTitle + "</h2>");
            pw.println("<p>Generated: " + new Date() + "</p>");
            pw.println("<table><tr>");
            for (int i = 0; i < table.getColumnCount(); i++)
                pw.print("<th>" + table.getColumnName(i) + "</th>");
            pw.println("</tr>");
            for (int r = 0; r < table.getRowCount(); r++) {
                pw.print("<tr>");
                for (int c = 0; c < table.getColumnCount(); c++) {
                    Object val = table.getValueAt(r, c);
                    pw.print("<td>" + (val != null ? val.toString() : "") + "</td>");
                }
                pw.println("</tr>");
            }
            pw.println("</table></body></html>");
            JOptionPane.showMessageDialog(null, "HTML Report exported successfully!\n" + file.getAbsolutePath(),
                    "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Export failed: " + e.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─── PDF Export (using Java Print API — no external JAR needed) ───────────
    public static void exportToPDF(JTable table, String reportTitle) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save PDF Report");
        chooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        chooser.setSelectedFile(new File(reportTitle.replaceAll(" ", "_") + "_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf"));

        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (!file.getName().endsWith(".pdf")) file = new File(file.getAbsolutePath() + ".pdf");

        final File finalFile = file;
        final TableModel model = table.getModel();
        final String title = reportTitle;

        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        pf.setOrientation(PageFormat.LANDSCAPE);

        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            int pageWidth  = (int) pageFormat.getImageableWidth();
            int y = 0;

            // Title
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(new Color(30, 60, 114));
            g2.drawString(title, 0, y += 20);

            // Date
            g2.setFont(new Font("Arial", Font.PLAIN, 9));
            g2.setColor(Color.GRAY);
            g2.drawString("Generated: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 0, y += 14);
            y += 6;

            int cols = model.getColumnCount();
            int colWidth = pageWidth / cols;
            int rowHeight = 16;

            // Header row
            g2.setFont(new Font("Arial", Font.BOLD, 9));
            g2.setColor(new Color(41, 128, 185));
            g2.fillRect(0, y, pageWidth, rowHeight);
            g2.setColor(Color.WHITE);
            for (int c = 0; c < cols; c++)
                g2.drawString(model.getColumnName(c), c * colWidth + 3, y + 11);
            y += rowHeight;

            // Data rows
            g2.setFont(new Font("Arial", Font.PLAIN, 8));
            for (int r = 0; r < model.getRowCount(); r++) {
                g2.setColor(r % 2 == 0 ? new Color(245, 247, 250) : Color.WHITE);
                g2.fillRect(0, y, pageWidth, rowHeight);
                g2.setColor(Color.DARK_GRAY);
                for (int c = 0; c < cols; c++) {
                    Object val = model.getValueAt(r, c);
                    String text = val != null ? val.toString() : "";
                    if (text.length() > 20) text = text.substring(0, 18) + "..";
                    g2.drawString(text, c * colWidth + 3, y + 11);
                }
                y += rowHeight;
            }

            // Border
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRect(0, 40, pageWidth, y - 40);

            return Printable.PAGE_EXISTS;
        }, pf);

        // Print to file (PDF via system print-to-PDF or printer)
        try {
            job.setJobName(title);
            boolean ok = job.printDialog();
            if (ok) {
                job.print();
                JOptionPane.showMessageDialog(null,
                        "PDF printed/exported successfully!",
                        "Export Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PDF export failed: " + e.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
