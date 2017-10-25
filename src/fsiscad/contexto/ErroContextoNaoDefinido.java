package fsiscad.contexto;

import fsiscad.util.*;

/**
 * Levantado para indicar que um {@link Contexto contexto} ainda não foi
 * definido.
 */
public class ErroContextoNaoDefinido extends ErroContexto {
    public ErroContextoNaoDefinido(String msg) {
        super(msg);
    }
}
