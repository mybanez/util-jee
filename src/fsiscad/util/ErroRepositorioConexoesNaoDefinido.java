package fsiscad.util;

/**
 * Levantado para indicar que um {@link RepositorioConexoes repositório de
 * conexões} ainda não foi definido.
 */
public class ErroRepositorioConexoesNaoDefinido extends ErroExecucao {
    public ErroRepositorioConexoesNaoDefinido(String msg) {
        super(msg);
    }
}
