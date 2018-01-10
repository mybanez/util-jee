package meyn.util.contexto;

/**
 * Levantado para indicar que um {@link Contexto contexto} já foi definido.
 */
public class ErroContextoJaDefinido extends ErroContexto {
    public ErroContextoJaDefinido(String msg) {
        super(msg);
    }
}
