package fsiscad.modelo.chaveprimaria;

import java.rmi.*;
import javax.ejb.*;

/**
 * Interface remota de {@link GeradorSequenciaBean GeradorSequenciaBean}.
 */
public interface GeradorSequencia extends EJBObject {
    /**
     * Retorna o pr�ximo valor desta seq��ncia.
     *
     * @return pr�ximo valor da seq��ncia
     *
     * @throws ErroSequencia se ocorrer um erro durante a gera��o do pr�ximo
     *         valor da seq��ncia
     */
    long getProximoValorSequencia(String chvSeq)
    throws RemoteException, ErroGeradorSequencia;
}
