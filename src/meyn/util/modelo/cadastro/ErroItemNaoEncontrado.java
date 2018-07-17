package meyn.util.modelo.cadastro;

/**
 * Levantado para indicar que uma consulta por chave prim�ria n�o retornou
 * nenhum item.
 */
@SuppressWarnings("serial")
public class ErroItemNaoEncontrado extends ErroCadastro {
    public ErroItemNaoEncontrado(String msg) {
        super(msg);
    }
}
