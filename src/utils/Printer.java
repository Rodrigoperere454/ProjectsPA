package utils;

import model.Log;

import java.awt.*;
import java.awt.print.*;
import java.util.List;

public class Printer {

    public static void printExtract(List<Log> logs) {
        PrinterJob job = PrinterJob.getPrinterJob();

        job.setJobName("Extrato de Ações do Processo");

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int pageIndex) {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

                int x = (int) pf.getImageableX() + 20;
                int y = (int) pf.getImageableY() + 20;
                int lineHeight = 15;

                g.drawString("Extrato de Ações do Processo:", x, y);
                y += lineHeight;

                for (Log log : logs) {
                    g.drawString("Data e Hora: " + log.getDataHora(), x, y);
                    y += lineHeight;
                    g.drawString("Utilizador: " + log.getUsername(), x, y);
                    y += lineHeight;
                    g.drawString("Descrição: " + log.getAcao(), x, y);
                    y += lineHeight * 2; // Espaçamento extra entre registos
                }

                return Printable.PAGE_EXISTS;
            }
        });

        // Tentar mostrar a janela de diálogo da impressora
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
                System.out.println("Impressão concluída.");
            } catch (PrinterException e) {
                System.err.println("Erro ao imprimir: " + e.getMessage());
            }
        } else {
            System.out.println("Impressão cancelada pelo utilizador.");
        }
    }
}

