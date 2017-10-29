package fsiscad.contexto;

/**
 * Levantado para indicar que um v�nculo ainda n�o foi definido em um {@link
 * Contexto contexto}.
 */
public class ErroItemContextoNaoDefinido extends ErroContexto {
    public ErroItemContextoNaoDefinido(String msg) {
        super(msg);
    }
}
