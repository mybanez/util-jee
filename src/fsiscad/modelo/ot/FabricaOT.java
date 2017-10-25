package fsiscad.modelo.ot;

import fsiscad.beans.*;
import java.lang.reflect.*;

import java.util.*;

/**
 * Fabrica de objetos de transferência. Os métodos que não passam uma interface de
 * acesso às propriedades do ot retornam instãncias de {@link OTMapeado OTMapeado}.
 * Já os que passam uma interface de acesso retornam instancias de ot criadas através
 * de chamadas a {@link Proxy#newProxyInstance(ClassLoader,Class[],InvocationHandler) 
 * Proxy.newProxyInstance(ClassLoader,Class[],InvocationHandler)}, usando uma instância
 * de {@link OTInvocationHandler OTInvocationHandler} como <i>invocation handler</i>.
 *
 * @see OTMapeado
 * @see OTTipado
 */
public final class FabricaOT {
    
    private FabricaOT() {}
    
    /**
     * Ver {@link OTMapeado#OTMapeado() OTMapeado()}.
     */
    public static OT getInstancia() {
        return new OTMapeado();
    }
    
    /**
     * Ver {@link OTMapeado#OTMapeado(Collection) OTMapeado(Collection)}.
     */
    public static OT getInstancia(Collection<String> clNomesProps) {
        return new OTMapeado(clNomesProps);
    }
    
    /**
     * Ver {@link OTMapeado#OTMapeado(Collection,Collection) OTMapeado(Collection,Collection)}.
     */
    public static OT getInstancia(Collection<String> clNomesPropsChave, Collection<String> clNomesProps) {
        return new OTMapeado(clNomesPropsChave, clNomesProps);
    }
    
    /**
     * Ver {@link OTMapeado#OTMapeado(Map) OTMapeado(Map)}.
     */
    public static OT getInstancia(Map<String, Object> mpProps) {
        return new OTMapeado(mpProps);
    }
    
    /**
     * Ver {@link OTMapeado#OTMapeado(Collection,Map) OTMapeado(Collection,Map)}.
     */
    public static OT getInstancia(Collection<String> clNomesPropsChave, Map<String, Object> mpProps) {
        return new OTMapeado(clNomesPropsChave, mpProps);
    }
    
    /**
     * Ver {@link OTTipado#OTTipado(Class) OTTipado(Class)}.
     */
    public static OT getInstancia(Class tipoAcessoProps) {
        return getInstancia(new ArrayList(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet()), tipoAcessoProps);
    }
    
    /**
     * Ver {@link OTTipado#OTTipado(Collection,Class) OTTipado(Collection,Class)}.
     */
    public static OT getInstancia(Collection<String> clNomesProps, Class tipoAcessoProps) {
        return getInstancia(Collections.<String>emptyList(), clNomesProps, tipoAcessoProps);
    }
    
    /**
     * Ver {@link OTTipado#OTTipado(Collection,Collection,Class) OTTipado(Collection,Collection,Class)}.
     */
    public static OT getInstancia(Collection<String> clNomesPropsChave, Collection<String> clNomesProps, Class tipoAcessoProps) {
        ClassLoader carregador = Thread.currentThread().getContextClassLoader();
        Class[] tipos = { OT.class, tipoAcessoProps };
        OTInvocationHandler ot = new OTInvocationHandler(clNomesPropsChave, clNomesProps, tipoAcessoProps);
        return (OT)Proxy.newProxyInstance(carregador, tipos, ot);
    }
    
    /**
     * Ver {@link OTTipado#OTTipado(Map,Class) OTTipado(Map,Class)}.
     */
    public static OT getInstancia(Map<String, Object> mpProps, Class tipoAcessoProps) {
        return getInstancia(Collections.<String>emptyList(), mpProps, tipoAcessoProps);
    }
    
    /**
     * Ver {@link OTTipado#OTTipado(Collection,Map,Class) OTTipado(Collection,Map,Class)}.
     */
    public static OT getInstancia(Collection<String> clNomesPropsChave, Map<String, Object> mpProps, Class tipoAcessoProps) {
        ClassLoader carregador = Thread.currentThread().getContextClassLoader();
        Class[] tipos = { OT.class, tipoAcessoProps };
        OTInvocationHandler ot = new OTInvocationHandler(clNomesPropsChave, mpProps, tipoAcessoProps);
        return (OT)Proxy.newProxyInstance(carregador, tipos, ot);
    }    
}
