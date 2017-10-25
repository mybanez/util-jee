package fsiscad.modelo.ot;

import fsiscad.util.*;
import fsiscad.beans.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Suporte para implementa��es de objetos de transfer�ncia tipados. As subclasses
 * destes objetos devem necessariamente prover armazenamento e implementar uma interface 
 * de acesso com m�todos <tt>get</tt> e <tt>set</tt> espec�ficos para manipula�ao das 
 * suas propriedades. A implementa��o da interface {@link fsiscad.beans.AcessoPropriedades 
 * AcessoPropriedades} feita nesta classe procura estes m�todos de acesso para a
 * leitura e escrita das propriedades. Os construtores recebem como par�metro extra
 * o tipo da interface de acesso que o OT implementa, passando os outros 
 * par�metros para os construtores correspondentes de {@link OTImpl OTImpl}.
 *
 * @see FabricaOT
 */
public abstract class OTTipado extends OTImpl {
    private Class tipoAcessoProps;
    
    /**
     * Extrai os nomes das propriedades do ot a partir da interface de acesso 
     * <tt>tipoAcessoProps</tt>.
     * Ver {@link OTImpl#OTImpl(Collection) OTImpl(Collection)}.
     */
    public OTTipado(Class tipoAcessoProps) {
        super(new ArrayList(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet()));
        setTipoAcessoPropriedades(tipoAcessoProps);
    }
    
    /**
     * Extrai os nomes das propriedades do ot a partir da interface de acesso 
     * <tt>tipoAcessoProps</tt>.
     * Ver {@link OTImpl#OTImpl(Collection,Collection) OTImpl(Collection,Collection)}.
     */
    public OTTipado(Collection<String> clNomesProps, Class tipoAcessoProps) {
        super(clNomesProps);
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }
    
    /**
     * Ver {@link OTImpl#OTImpl(Collection,Collection) OTImpl(Collection,Collection)}.
     */
    public OTTipado(Collection<String> clNomesPropsChave, Collection<String> clNomesProps, Class tipoAcessoProps) {
        super(clNomesPropsChave, clNomesProps);
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }
    
    /**
     * Cria um objeto de transfer�ncia com as propriedades e valores 
     * definidos neste mapa e que implementa esta interface de acesso.
     *
     * @param mpProps nomes e valores das propriedades
     * @param tipoAcessoProps interface de acesso �s propriedades
     */
    public OTTipado(Map<String, Object> mpProps, Class tipoAcessoProps) {
        super(new ArrayList(mpProps.keySet()));
        set(mpProps);        
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }
    
    /**
     * Cria um objeto de transfer�ncia com os propriedades chave definidas nesta 
     * cole��o, com as propriedades e valores definidos neste mapa e que implementa 
     * esta interface de acesso.
     *
     * @param clNomesPropsChave nomes das propriedades que comp�em a
     *        chave prim�ria
     * @param mpProps nomes e valores das propriedades
     * @param tipoAcessoProps interface de acesso �s propriedades
     */
    public OTTipado(Collection<String> clNomesPropsChave, Map<String, Object> mpProps, Class tipoAcessoProps) {
        super(clNomesPropsChave, new ArrayList(mpProps.keySet()));
        set(mpProps);        
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }

    public final Class getTipoAcessoPropriedades() {
        return tipoAcessoProps;
    }

    public final void setTipoAcessoPropriedades(Class tipoAcessoProps) {
        if (!tipoAcessoProps.isAssignableFrom(getClass())) {
            throw new ErroExecucao("OT n�o implementa interface de acesso: "+tipoAcessoProps.getName());
        }
        this.tipoAcessoProps = tipoAcessoProps;
    }
    
    /*       SUPORTE PARA MANIPULA��O DE ATRIBUTOS         */
    
    /**
     * Implementa��o est�tica da l�gica do m�todo {@link OTImpl#get(String) get(String)}.
     */
    public final static Object get(OT ot, String nome) {
        validarPropriedade(ot, nome);
        Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(ot.getClass());
        PropertyDescriptor infoProp = mpInfoProp.get(nome);
        if (infoProp == null || infoProp.getReadMethod() == null) {
            throw new ErroExecucao("N�o existe m�todo de escrita para a propriedade: "+nome);
        }
        try {
            return infoProp.getReadMethod().invoke(ot, new Object[]{});
        } catch (InvocationTargetException ite) {
            throw new ErroExecucao("Erro lendo propriedade '"+nome+"'", ite.getTargetException());
        } catch (Exception e) {
            throw new ErroExecucao("Erro lendo propriedade '"+nome+"'", e);
        }
    }
    
    /**
     * Implementa��o est�tica da l�gica do m�todo {@link OTImpl#set(String,Object) set(String,Object)}.
     */
    public final static void set(OT ot, String nome, Object valor) { 
        validarPropriedade(ot, nome);
        Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(ot.getClass());
        PropertyDescriptor infoProp = mpInfoProp.get(nome);
        if (infoProp == null || infoProp.getWriteMethod() == null) {
            throw new ErroExecucao("N�o existe m�todo de escrita para a propriedade: "+nome);
        }
        try {
            infoProp.getWriteMethod().invoke(ot, new Object[]{ valor });
        } catch (InvocationTargetException ite) {
            throw new ErroExecucao("Erro escrevendo propriedade '"+nome+"'", ite.getTargetException());
        } catch (Exception e) {
            throw new ErroExecucao("Erro escrevendo propriedade '"+nome+"'", e);
        }
    }
    
    /*          INTERFACE AcessoPropriedades           */
    
    public final Object get(String nome) {
        return get(this, nome);
    }
    
    public final void set(String nome, Object valor) {
        set(this, nome, valor);
    }    
}
