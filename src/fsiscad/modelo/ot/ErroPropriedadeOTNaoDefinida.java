package fsiscad.modelo.ot;

import fsiscad.util.ErroExecucao;

/**
 * Levantado para indicar que uma propriedade n�o est� definida em um objeto de
 * transfer�ncia.
 */
public class ErroPropriedadeOTNaoDefinida extends ErroExecucao {
    public ErroPropriedadeOTNaoDefinida(String msg) {
        super(msg);
    }
}
