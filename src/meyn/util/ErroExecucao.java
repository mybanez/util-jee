package meyn.util;

/**
 * Erro de execu��o ocorrido durante o processamento da <i>framework</i>.
 */
@SuppressWarnings("serial")
public class ErroExecucao extends RuntimeException {
	/**
	 * Cria uma instancia de erro com esta mensagem.
	 *
	 * @param msg mensagem de erro
	 */
	public ErroExecucao(String msg) {
		this(msg, null);
	}

	/**
	 * Cria uma instancia de erro de execu��o com esta mensagem e esta causa.
	 *
	 * @param msg   mensagem de erro
	 * @param causa erro original que causou o problema
	 */
	public ErroExecucao(String msg, Throwable causa) {
		super(msg, causa);

	}
}
