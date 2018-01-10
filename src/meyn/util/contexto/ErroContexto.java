package meyn.util.contexto;

import meyn.util.*;

/**
 * Levantado para indicar um erro de {@link Contexto contexto}.
 */
public class ErroContexto extends ErroExecucao {
    public ErroContexto(String msg) {
        super(msg);
    }
    
    public ErroContexto(String msg, Throwable t) {
        super(msg, t);
    }
}
