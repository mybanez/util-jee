package fsiscad.controle;

import fsiscad.util.*;

/**
 * Levantado para indicar a ocorrência de um erro durante algum processamento
 * na camada de controle.
 */
public class ErroControle extends Erro {
    public ErroControle(String msg) {
        super(msg);
    }
    
    public ErroControle(String msg, Throwable t) {
        super(msg, t);
    }
}
