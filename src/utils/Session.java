package utils;

import model.Utilizador;


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
