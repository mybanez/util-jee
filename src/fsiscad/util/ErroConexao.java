package fsiscad.util;

/**
 * Levantado para indicar a ocorrência de um erro durante a abertura de uma
 * conexão JDBC.
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
