package meyn.util.modelo.entidade;

import meyn.util.ErroExecucao;

/**
 * Levantado para indicar que uma propriedade n�o est� definida em um objeto de
 * transfer�ncia.
 */
@SuppressWarnings("serial")
public class ErroPropriedadeEntidadeNaoDefinida extends ErroExecucao {
	public ErroPropriedadeEntidadeNaoDefinida(String msg) {
		super(msg);
	}
}
