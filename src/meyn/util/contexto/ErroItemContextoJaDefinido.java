package meyn.util.contexto;

/**
 * Levantado para indicar que um vínculo já foi definido em um {@link Contexto
 * contexto}.
 */
public class ErroItemContextoJaDefinido extends ErroContexto {
    public ErroItemContextoJaDefinido(String msg) {
        super(msg);
    }
}
