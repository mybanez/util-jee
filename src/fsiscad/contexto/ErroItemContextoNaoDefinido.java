package fsiscad.contexto;

import fsiscad.util.*;

/**
 * Levantado para indicar que um vínculo ainda não foi definido em um {@link
 * Contexto contexto}.
 */
public class ErroItemContextoNaoDefinido extends ErroContexto {
    public ErroItemContextoNaoDefinido(String msg) {
        super(msg);
    }
}
