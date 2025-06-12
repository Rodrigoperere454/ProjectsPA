import controller.DBconfig;
import controller.UtilizadorController;
import view.frames.FabMenuFrame;
import view.frames.InicialMenuFrame;
import view.frames.TechMenuFrame;
import view.frames.AdminMenuFrame;

import java.sql.Connection;

public class Main {
    /**
     * Função principal, verifica a conexão com a base de dados e inicia o programa
     * @param args
     */
    public static void main(String[] args) throws Exception {
        InicialMenuFrame menuInicial = new InicialMenuFrame();
        menuInicial.setSize(400, 400);
        menuInicial.setVisible(true);

        /*TechMenuFrame techMenuFrame = new TechMenuFrame();
        techMenuFrame.setSize(400, 500);
        techMenuFrame.setVisible(true);*/

        /*FabMenuFrame fabMenuFrame = new FabMenuFrame();
        fabMenuFrame.setSize(400, 500);
        fabMenuFrame.setVisible(true);*/

        /*AdminMenuFrame adminMenuFrame = new AdminMenuFrame();
        adminMenuFrame.setSize(400, 580);
        adminMenuFrame.setVisible(true);*/

        Connection connection = null;

        while (connection == null) {
            connection = DBconfig.getConnection();
            Thread.sleep(1000);
            if (connection == null) {
                System.out.println("Se é a sua primeira vez no programa este erro é normal, caso este erro seja persistente contacte o suporte(Professor Marco de PA).");
                DBconfig.configurarBD();
            }
        }
    }
}