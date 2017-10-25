package fsiscad.modelo.chaveprimaria;

import fsiscad.modelo.ErroModelo;

/**
 * Levantado para indicar a ocorr�ncia de um erro durante a gera��o de um valor
 * de seq��ncia.
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
