package fsiscad.modelo.chaveprimaria;

import fsiscad.util.*;

/**
 * Levantado para indicar a ocorr�ncia de um erro durante a gera��o de uma
 * chave prim�ria.
 *
 * @see GeradorChavePrimaria
 */
public class ErroGeradorChavePrimaria extends Erro {
    public ErroGeradorChavePrimaria(String msg) {
        super(msg);
    }
    
    public ErroGeradorChavePrimaria(String msg, Throwable t) {
        super(msg, t);
    }
}
