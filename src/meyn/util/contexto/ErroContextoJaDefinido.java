package meyn.util.contexto;

/**
 * Levantado para indicar que um {@link ContextoEmMemoria contexto} j� foi
 * definido.
 */
@SuppressWarnings("serial")
public class ErroContextoJaDefinido extends ErroContexto {
	public ErroContextoJaDefinido(String msg) {
		super(msg);
	}
}
