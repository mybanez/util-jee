package utljee.modelo.ot;

import utljee.util.ErroExecucao;

/**
 * Levantado para indicar que uma propriedade n�o est� definida em um objeto de
 * transfer�ncia.
 */
public class ErroPropriedadeOTNaoDefinida extends ErroExecucao {
    public ErroPropriedadeOTNaoDefinida(String msg) {
        super(msg);
    }
}
