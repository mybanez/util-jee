package fsiscad.modelo.chaveprimaria;

import fsiscad.modelo.ErroModelo;

/**
 * Levantado para indicar a ocorrência de um erro durante a geração de um valor
 * de seqüência.
 *
 * @see GeradorSequencia
 */
public class ErroGeradorSequencia extends ErroModelo {
    public ErroGeradorSequencia(String msg) {
        super(msg);
    }
    
    public ErroGeradorSequencia(String msg, Throwable t) {
        super(msg, t);
    }
}
