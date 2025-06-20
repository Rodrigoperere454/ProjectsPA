package utils;

import model.Log;

import java.awt.*;
import java.awt.print.*;
import java.util.List;
import utils.LogFileManager;

/**
 * Classe responsável por imprimir o extrato de ações do processo.
 */
public class Printer {

    /**
     * Método para imprimir o extrato de ações do processo.
     *
     * @param logs Lista de logs a serem impressos.
     * @return void
     */
    public static void printExtract(List<Log> logs) {
        PrinterJob job = PrinterJob.getPrinterJob();
        List<Log> logList = LogFileManager.readLogFile();

        job.setJobName("Extrato de Ações do Processo");

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int pageIndex) {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

                int x = (int) pf.getImageableX() + 20; // Margem esquerda
                int y = (int) pf.getImageableY() + 20; // Margem superior
                int lineHeight = 15; // Altura da linha

                g.drawString("Extrato de Ações do Processo:", x, y);
                y += lineHeight * 2;

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

        // mostra a caixa de diálogo de impressão
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

