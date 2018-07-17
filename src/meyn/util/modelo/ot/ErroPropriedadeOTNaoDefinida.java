package meyn.util.modelo.ot;

import meyn.util.ErroExecucao;

/**
 * Levantado para indicar que uma propriedade não está definida em um objeto de
 * transferência.
 */
@SuppressWarnings("serial")
public class ErroPropriedadeOTNaoDefinida extends ErroExecucao {
    public ErroPropriedadeOTNaoDefinida(String msg) {
        super(msg);
    }
}
