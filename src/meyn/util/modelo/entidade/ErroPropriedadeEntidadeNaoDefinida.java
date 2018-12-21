package meyn.util.modelo.entidade;

import meyn.util.ErroExecucao;

/**
 * Levantado para indicar que uma propriedade não está definida em um objeto de
 * transferência.
 */
@SuppressWarnings("serial")
public class ErroPropriedadeEntidadeNaoDefinida extends ErroExecucao {
	public ErroPropriedadeEntidadeNaoDefinida(String msg) {
		super(msg);
	}
}
