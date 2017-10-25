package fsiscad.modelo.chaveprimaria;

import java.rmi.*;
import javax.ejb.*;

/**
 * Interface remota de {@link GeradorSequenciaBean GeradorSequenciaBean}.
 */
public interface GeradorSequencia extends EJBObject {
    /**
     * Retorna o próximo valor desta seqüência.
     *
     * @return próximo valor da seqüência
     *
     * @throws ErroSequencia se ocorrer um erro durante a geração do próximo
     *         valor da seqüência
     */
    long getProximoValorSequencia(String chvSeq)
    throws RemoteException, ErroGeradorSequencia;
}
