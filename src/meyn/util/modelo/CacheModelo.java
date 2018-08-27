package meyn.util.modelo;

import meyn.util.*;

/**
 * Cache de objetos da camada dos modelos. O cache � mantido no contexto 
 * da aplica��o JEE, vinculado ao nome desta classe. � garantida a exist�ncia 
 * de <b>um �nico cache por <i>class loader</i></b>.
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
