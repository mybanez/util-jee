package fsiscad.util;

/**
 * Levantado para indicar a ocorr�ncia de um erro durante a abertura de uma
 * conex�o JDBC.
 *
 * @see RepositorioConexoes
 */
public class ErroConexao extends Erro {
    public ErroConexao(String msg) {
        super(msg);
    }
    
    public ErroConexao(String msg, Throwable t) {
        super(msg, t);
    }
}
