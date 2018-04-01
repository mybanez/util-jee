package meyn.util.modelo.entidade;

/**
 * Levantado para indicar que uma consulta por chave prim�ria n�o retornou
 * nenhum item.
 */
public class ErroItemNaoEncontrado extends ErroCadastro {
    public ErroItemNaoEncontrado(String msg) {
        super(msg);
    }
}
