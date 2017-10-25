package fsiscad.modelo.chaveprimaria;

import javax.ejb.*;

/**
 * <i>Entity bean</i> CMP que mantém as seqüências. Implementa a técnica descrita 
 * no livro <i>EJB Design Patterns</i> de Floyd Marinescu.
 *
 * @see GeradorSequenciaBean
 */
public abstract class SequenciaBean implements EntityBean, Sequencia {
    private EntityContext context;
    
    public String ejbCreate(String nome) throws CreateException {
        setNome(nome);
        setValor(0);
        return nome;
    }
    
    public void ejbPostCreate(String nome) {}
    
    public void ejbActivate() {}
    
    public void ejbLoad() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {}
    
    public void ejbStore() {}
    
    public void setEntityContext(EntityContext ctx) {
        this.context = ctx;
    }
    
    public void unsetEntityContext() {
        this.context = null;
    }
    
    public abstract String getNome();
    
    public abstract void setNome(String nome);
    
    public abstract long getValor();
    
    public abstract void setValor(long valor);
    
    public long getProximoValor(int incremento) {
        setValor(getValor() + incremento);
        return getValor();
    }
}
