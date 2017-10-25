package fsiscad.util;

import java.io.*;

/**
 * Erro de execução ocorrido durante o processamento da <i>framework</i>.
 */
public class ErroExecucao extends RuntimeException {
    /**
     * Cria uma instancia de erro com esta mensagem.
     *
     * @param msg mensagem de erro
     */
    public ErroExecucao(String msg) {
        this(msg, null);
    }
    
    /**
     * Cria uma instancia de erro de execução com esta mensagem e esta causa.
     *
     * @param msg mensagem de erro
     * @param causa erro original que causou o problema
     */
    public ErroExecucao(String msg, Throwable causa) {
        super(msg, causa);
    }
}
