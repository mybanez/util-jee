package utljee.modelo.ot;

import utljee.util.ErroExecucao;

/**
 * Levantado para indicar que uma propriedade não está definida em um objeto de
 * transferência.
 */
public class ErroPropriedadeOTNaoDefinida extends ErroExecucao {
    public ErroPropriedadeOTNaoDefinida(String msg) {
        super(msg);
    }
}
