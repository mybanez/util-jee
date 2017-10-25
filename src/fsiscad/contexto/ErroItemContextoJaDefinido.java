package fsiscad.contexto;

import fsiscad.util.*;

/**
 * Levantado para indicar que um v�nculo j� foi definido em um {@link Contexto
 * contexto}.
 */
public class ErroItemContextoJaDefinido extends ErroContexto {
    public ErroItemContextoJaDefinido(String msg) {
        super(msg);
    }
}
