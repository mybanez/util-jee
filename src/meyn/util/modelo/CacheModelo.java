package meyn.util.modelo;

import meyn.util.*;

/**
 * Cache de objetos da camada dos modelos. O cache é mantido no contexto 
 * da aplicação JEE, vinculado ao nome desta classe. É garantida a existência 
 * de <b>um único cache por <i>class loader</i></b>.
 * 
 * @see meyn.util.contexto.ContextoEmMemoria
 */
@SuppressWarnings("serial")
public class CacheModelo extends Cache<Object, Object> {
    /**
     * Retorna o cache de objetos da camada dos modelos.
     *
     * @return cache da camada dos modelos
     */
    public final static CacheModelo getCacheModelo() {
        return (CacheModelo)getCache(CacheModelo.class);
    }
}
