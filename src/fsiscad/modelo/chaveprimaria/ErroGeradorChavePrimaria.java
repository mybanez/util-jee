package fsiscad.modelo.chaveprimaria;

import fsiscad.util.*;

/**
 * Levantado para indicar a ocorrência de um erro durante a geração de uma
 * chave primária.
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
