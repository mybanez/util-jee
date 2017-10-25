package fsiscad.util;

import java.io.*;

/**
 * Erro ocorrido durante o processamento da <i>framework</i>.
 */
public class Erro extends Exception {
    /**
     * Cria uma instancia de erro com esta mensagem.
     *
     * @param msg mensagem de erro
     */
    public Erro(String msg) {
        this(msg, null);
    }
    
    /**
     * Cria uma instancia de erro com esta mensagem e esta causa.
     *
     * @param msg mensagem de erro
     * @param causa erro original que causou o problema
     */
    public Erro(String msg, Throwable causa) {
        super(msg, causa);
    }
}
