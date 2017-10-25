package fsiscad.modelo.ot;

import fsiscad.util.*;
import fsiscad.beans.*;
import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Suporte para implementações de objetos de transferência tipados. As subclasses
 * destes objetos devem necessariamente prover armazenamento e implementar uma interface 
 * de acesso com métodos <tt>get</tt> e <tt>set</tt> específicos para manipulaçao das 
 * suas propriedades. A implementação da interface {@link fsiscad.beans.AcessoPropriedades 
 * AcessoPropriedades} feita nesta classe procura estes métodos de acesso para a
 * leitura e escrita das propriedades. Os construtores recebem como parâmetro extra
 * o tipo da interface de acesso que o OT implementa, passando os outros 
 * parâmetros para os construtores correspondentes de {@link OTImpl OTImpl}.
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
     * Cria um objeto de transferência com as propriedades e valores 
     * definidos neste mapa e que implementa esta interface de acesso.
     *
     * @param mpProps nomes e valores das propriedades
     * @param tipoAcessoProps interface de acesso às propriedades
     */
    public OTTipado(Map<String, Object> mpProps, Class tipoAcessoProps) {
        super(new ArrayList(mpProps.keySet()));
        set(mpProps);        
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }
    
    /**
     * Cria um objeto de transferência com os propriedades chave definidas nesta 
     * coleção, com as propriedades e valores definidos neste mapa e que implementa 
     * esta interface de acesso.
     *
     * @param clNomesPropsChave nomes das propriedades que compõem a
     *        chave primária
     * @param mpProps nomes e valores das propriedades
     * @param tipoAcessoProps interface de acesso às propriedades
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
            throw new ErroExecucao("OT não implementa interface de acesso: "+tipoAcessoProps.getName());
        }
        this.tipoAcessoProps = tipoAcessoProps;
    }
    
    /*       SUPORTE PARA MANIPULAÇÃO DE ATRIBUTOS         */
    
    /**
     * Implementação estática da lógica do método {@link OTImpl#get(String) get(String)}.
     */
    public final static Object get(OT ot, String nome) {
        validarPropriedade(ot, nome);
        Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(ot.getClass());
        PropertyDescriptor infoProp = mpInfoProp.get(nome);
        if (infoProp == null || infoProp.getReadMethod() == null) {
            throw new ErroExecucao("Não existe método de escrita para a propriedade: "+nome);
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
     * Implementação estática da lógica do método {@link OTImpl#set(String,Object) set(String,Object)}.
     */
    public final static void set(OT ot, String nome, Object valor) { 
        validarPropriedade(ot, nome);
        Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(ot.getClass());
        PropertyDescriptor infoProp = mpInfoProp.get(nome);
        if (infoProp == null || infoProp.getWriteMethod() == null) {
            throw new ErroExecucao("Não existe método de escrita para a propriedade: "+nome);
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
