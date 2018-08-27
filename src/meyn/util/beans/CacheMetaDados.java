package meyn.util.beans;

import meyn.util.*;

/**
 * Cache de metadados de componentes JavaBean.
 * 
 * @see meyn.util.contexto.ContextoEmMemoria
 */
@SuppressWarnings("serial")
public final class CacheMetaDados extends Cache<String, Object> {

	/**
	 * Retorna o cache de metadados de componentes JavaBeans.
	 *
	 * @return cache de metadados
	 */
	public static CacheMetaDados getCacheMetaDados() {
		return (CacheMetaDados) getCache(CacheMetaDados.class);
	}
}
