package meyn.util.contexto;

/**
 * Levantado para indicar que um {@link ContextoEmMemoria contexto} ainda n�o
 * foi definido.
 */
@SuppressWarnings("serial")
public class ErroContextoNaoDefinido extends ErroContexto {
	public ErroContextoNaoDefinido(String msg) {
		super(msg);
	}
}
