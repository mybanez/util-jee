package meyn.util.contexto;

/**
 * Levantado para indicar que um {@link ContextoEmMemoria contexto} já foi
 * definido.
 */
@SuppressWarnings("serial")
public class ErroContextoJaDefinido extends ErroContexto {
	public ErroContextoJaDefinido(String msg) {
		super(msg);
	}
}
