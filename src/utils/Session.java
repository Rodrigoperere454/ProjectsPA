package utils;

import model.Utilizador;

/**
 * Classe que representa a sessão do utilizador autenticado.
 * Permite armazenar e recuperar o utilizador autenticado, verificar se há uma sessão ativa e limpar a sessão.
 */
public class Session {
    private static Utilizador utilizadorAutenticado;

    public static void setUtilizador(Utilizador utilizador) {
        utilizadorAutenticado = utilizador;
    }

    public static Utilizador getUtilizador() {
        return utilizadorAutenticado;
    }

    public static void limparSessao() {
        utilizadorAutenticado = null;
    }

    public static boolean isAutenticado() {
        return utilizadorAutenticado != null;
    }
}
