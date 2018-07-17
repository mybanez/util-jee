package meyn.util.modelo.ot;

import java.beans.*;
import java.lang.reflect.*;
import java.util.*;

import meyn.util.*;
import meyn.util.beans.*;

/**
 * Suporte para implementações de objetos de transferência tipados. As subclasses
 * destes objetos devem necessariamente prover armazenamento e implementar uma interface 
 * de acesso com métodos <tt>get</tt> e <tt>set</tt> específicos para manipulaçao das 
 * suas propriedades. A implementação da interface {@link meyn.util.beans.AcessoPropriedades 
 * AcessoPropriedades} feita nesta classe procura estes métodos de acesso para a
 * leitura e escrita das propriedades. Os construtores recebem como parâmetro extra
 * o tipo da interface de acesso que o OT implementa, passando os outros 
 * parâmetros para os construtores correspondentes de {@link OTImpl OTImpl}.
 *
 * @see FabricaOT
 */
@SuppressWarnings("serial")
public abstract class OTTipado extends OTImpl {
    private Class<?> tipoAcessoProps;
    
    /**
     * Extrai os nomes das propriedades do ot a partir da interface de acesso 
     * <tt>tipoAcessoProps</tt>.
     * Ver {@link OTImpl#OTImpl(Collection) OTImpl(Collection)}.
     */
    public OTTipado(Class<?> tipoAcessoProps) {
        super(new ArrayList<String>(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet()));
        setTipoAcessoPropriedades(tipoAcessoProps);
    }
    
    /**
     * Extrai os nomes das propriedades do ot definidos em <tt>clNomesProps</tt> a partir da interface de acesso 
     * <tt>tipoAcessoProps</tt>.
     * Ver {@link OTImpl#OTImpl(Collection) OTImpl(Collection)}.
     */
    public OTTipado(Collection<String> clNomesProps, Class<?> tipoAcessoProps) {
        super(clNomesProps);
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }
    
    /**
     * Cria um objeto de transferência com as propriedades e valores 
     * definidos neste mapa e que implementa esta interface de acesso.
     *
     * @param mpProps nomes e valores das propriedades
     * @param tipoAcessoProps interface de acesso às propriedades
     */
    public OTTipado(Map<String, Object> mpProps, Class<?> tipoAcessoProps) {
        super(new ArrayList<String>(mpProps.keySet()));
        set(mpProps);        
        setTipoAcessoPropriedades(tipoAcessoProps);        
    }

    @Override	
    public final Class<?> getTipoAcessoPropriedades() {
        return tipoAcessoProps;
    }

    public final void setTipoAcessoPropriedades(Class<?> tipoAcessoProps) {
        if (!tipoAcessoProps.isAssignableFrom(getClass())) {
            throw new ErroExecucao("OT não implementa interface de acesso: "+tipoAcessoProps.getName());
        }
        this.tipoAcessoProps = tipoAcessoProps;
    }
    
    /*       SUPORTE PARA MANIPULAÇÃO DE ATRIBUTOS         */
    
    /**
     * Implementação estática da lógica do método {@link OTImpl#get(String) get(String)}.
     */
    public final static Object get(OTTipado ot, String nome) {
        try {
            validarPropriedade(ot, nome);
            Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(ot.getClass());
            PropertyDescriptor infoProp = mpInfoProp.get(nome);
            Method met = infoProp.getReadMethod();
            if (met == null) {
                throw new ErroExecucao("Método get inexistente: "+nome);
            }
        	return met.invoke(ot, new Object[]{});
        } catch (InvocationTargetException e) {
            throw new ErroExecucao("Erro lendo propriedade: "+nome, e.getTargetException());
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new ErroExecucao("Erro lendo propriedade: "+nome, e);
        }
    }
    
    /**
     * Implementação estática da lógica do método {@link OTImpl#set(String,Object) set(String,Object)}.
     */
    public final static void set(OTTipado ot, String nome, Object valor) { 
        try {
            validarPropriedade(ot, nome);
            Map<String, PropertyDescriptor> mpInfoProp = Componentes.getDescritoresPropriedades(ot.getClass());
            PropertyDescriptor infoProp = mpInfoProp.get(nome);
            Method met = infoProp.getWriteMethod();
            if (met == null) {
                throw new ErroExecucao("Método set inexistente: "+nome);
            }
            met.invoke(ot, new Object[]{ valor });
        } catch (InvocationTargetException e) {
            throw new ErroExecucao("Erro escrevendo propriedade: "+nome, e.getTargetException());
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new ErroExecucao("Erro escrevendo propriedade: "+nome, e);
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
