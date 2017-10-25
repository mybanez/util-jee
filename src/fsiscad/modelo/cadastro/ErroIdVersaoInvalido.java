package fsiscad.modelo.cadastro;

/**
 * Levantado para indicar que o <a href="CadastroEntityLocalImpl.html#idVersao">
 * id de vers�o</a> de um registro na tabela foi modificado por outra
 * transa��o. Portando, os dados que a transa��o em curso leu e est� tentando
 * gravar de volta no banco j� se encontram obsoletos.
 *
 * @see CadastroEntityLocalImpl
 */
public class ErroIdVersaoInvalido extends ErroCadastro {
    public ErroIdVersaoInvalido(String msg) {
        super(msg);
    }
}
