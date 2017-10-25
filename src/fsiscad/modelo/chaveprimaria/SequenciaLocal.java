package fsiscad.modelo.chaveprimaria;

import javax.ejb.*;

/**
 * Interface local de {@link SequenciaBean SequenciaBean}.
 */
public interface SequenciaLocal extends EJBLocalObject, Sequencia {
    String getNome();
    void setNome(String nome);
    long getValor();
    void setValor(long valor);
}
