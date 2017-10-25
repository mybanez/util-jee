package fsiscad.modelo.ot;

import java.lang.reflect.*;
import java.util.*;

/**
 * Implementação da interface {@link java.lang.reflect.InvocationHandler java.lang.reflect.InvocationHandler}
 * para objetos de transferência.
 *
 * @see java.lang.reflect.Proxy
 **/
public class OTInvocationHandler extends OTMapeado implements InvocationHandler {
    private Class tipoAcessoProps;
    
    public OTInvocationHandler(Collection<String> clNomesPropsChave, Collection<String> clNomesProps, Class tipoAcessoProps) {
        super(clNomesPropsChave, clNomesProps);
        this.tipoAcessoProps = tipoAcessoProps;
    }
    
    public OTInvocationHandler(Collection<String> clNomesPropsChave, Map<String, Object> mpProps, Class tipoAcessoProps) {
        super(clNomesPropsChave, mpProps);
        this.tipoAcessoProps = tipoAcessoProps;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String nomeMetodo = method.getName();
        Class[] tiposParams = method.getParameterTypes();
        Class tipoRetorno = method.getReturnType();
        if (tiposParams.length == 0) {
            if (nomeMetodo.equals("getNomesPropriedadesChave")) {
                return getNomesPropriedadesChave();
            }
            if (nomeMetodo.equals("getNomesPropriedades")) {
                return getNomesPropriedades();
            }
            if (nomeMetodo.equals("getTipoAcessoPropriedades")) {
                return tipoAcessoProps;
            }
        }
        if (nomeMetodo.startsWith("get")) {
            if (nomeMetodo.length() > 3) {
                return get(getNomePropriedade(nomeMetodo)); //getXXX()
            }
            if (tiposParams.length == 1) {
                if (String.class.isAssignableFrom(args[0].getClass())) {
                    return get((String)args[0]); //get(String)
                }
                return get((Collection)args[0]); //get(Collection)
            }
            return get(); //get()
        }
        if (nomeMetodo.startsWith("set")) {
            if (nomeMetodo.length() > 3) {
                set(getNomePropriedade(nomeMetodo), args[0]); //setXXX(...)
                return null;
            }
            if (tiposParams.length == 2) {
                set((String)args[0], args[1]); //set(String,Object)
                return null;
            }
            set((Map)args[0]); //set(Map)
            return null;
        }
        throw new UnsupportedOperationException(proxy.getClass()+": "+nomeMetodo);
    }
    
    private String getNomePropriedade(String nomeMetodo) {
        return Character.toLowerCase(nomeMetodo.charAt(3))+nomeMetodo.substring(4);
    }
}