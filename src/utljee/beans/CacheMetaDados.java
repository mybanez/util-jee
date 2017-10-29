package utljee.beans;

import utljee.util.*;

/**
 * Cache de metadados de componentes JavaBean.
 * 
 * @see utljee.contexto.Contexto
 */
public final class CacheMetaDados extends Cache<String, Object> {

    /**
     * Retorna o cache de metadados de componentes JavaBeans.
     *
     * @return cache de metadados
     */
    public static CacheMetaDados getCacheMetaDados() {
        return (CacheMetaDados)getCache(CacheMetaDados.class.getName());
    }
}
