package fsiscad.util;

/**
 * Levantado para indicar que um {@link RepositorioConexoes reposit�rio de
 * conex�es} ainda n�o foi definido.
 */
public class ErroRepositorioConexoesNaoDefinido extends ErroExecucao {
    public ErroRepositorioConexoesNaoDefinido(String msg) {
        super(msg);
    }
}
