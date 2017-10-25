package fsiscad.modelo.chaveprimaria;

import javax.ejb.*;

/**
 * Interface <i>home</i> de {@link SequenciaBean SequenciaBean}.
 */
public interface SequenciaLocalHome extends EJBLocalHome {
    SequenciaLocal create(String nome) throws CreateException;
    SequenciaLocal findByPrimaryKey(String chave) throws FinderException;
}
