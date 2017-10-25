package fsiscad.util;

/**
 * Levantado para indicar que um {@link RepositorioConexoes repositório de
 * conexões} já foi definido.
 */
public class ErroRepositorioConexoesJaDefinido extends ErroExecucao {
    public ErroRepositorioConexoesJaDefinido(String msg) {
        super(msg);
    }
}
