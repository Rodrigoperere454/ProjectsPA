import controller.DBconfig;
import view.dialogs.DBDataDialog;
import view.frames.InicialMenuFrame;

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

        Connection connection = null;

        connection = DBconfig.getConnection();
        if (connection == null) {
            DBDataDialog dialog = new DBDataDialog();
            dialog.setVisible(true);
        }
    }
}