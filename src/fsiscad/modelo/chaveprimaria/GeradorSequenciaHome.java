package fsiscad.modelo.chaveprimaria;

import java.rmi.*;
import javax.ejb.*;

/**
 * Interface <i>home</i> de {@link GeradorSequenciaBean GeradorSequenciaBean}.
 */
public interface GeradorSequenciaHome extends EJBHome {
    GeradorSequencia create() throws RemoteException, CreateException;
}
