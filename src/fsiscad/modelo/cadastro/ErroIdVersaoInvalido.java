package fsiscad.modelo.cadastro;

/**
 * Levantado para indicar que o <a href="CadastroEntityLocalImpl.html#idVersao">
 * id de versão</a> de um registro na tabela foi modificado por outra
 * transação. Portando, os dados que a transação em curso leu e está tentando
 * gravar de volta no banco já se encontram obsoletos.
 *
 * @see CadastroEntityLocalImpl
 */
public class ErroIdVersaoInvalido extends ErroCadastro {
    public ErroIdVersaoInvalido(String msg) {
        super(msg);
    }
}
