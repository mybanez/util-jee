package fsiscad.util;

/**
 * Levantado para indicar que um {@link RepositorioConexoes reposit�rio de
 * conex�es} j� foi definido.
 */
public class ErroRepositorioConexoesJaDefinido extends ErroExecucao {
    public ErroRepositorioConexoesJaDefinido(String msg) {
        super(msg);
    }
}
