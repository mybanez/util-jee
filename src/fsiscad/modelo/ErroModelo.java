package fsiscad.modelo;

import fsiscad.util.*;

/**
 * Levantado para indicar a ocorr�ncia de um erro durante algum processamento
 * na camada dos modelos.
 */
public class ErroModelo extends Erro {
    public ErroModelo(String msg) {
        super(msg);
    }
    
    public ErroModelo(String msg, Throwable t) {
        super(msg, t);
    }
}
