package fsiscad.modelo.chaveprimaria;

import fsiscad.modelo.ErroModelo;

/**
 * Levantado para indicar a ocorrência de um erro durante a geração de um UUID.
 *
 * @see GeradorUUID
 */
public class ErroGeradorUUID extends ErroModelo {
    public ErroGeradorUUID(String msg) {
        super(msg);
    }
    
    public ErroGeradorUUID(String msg, Throwable t) {
        super(msg, t);
    }
}
