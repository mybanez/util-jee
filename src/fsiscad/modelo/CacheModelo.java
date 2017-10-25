package fsiscad.modelo;

import fsiscad.util.*;

/**
 * Cache de objetos da camada dos modelos. O cache � mantido no contexto 
 * da aplica��o J2EE, vinculado ao nome desta classe. � garantida  a exist�ncia 
 * de <b>um �nico cache por <i>class loader</i></b>.
 * 
 * @see fsiscad.contexto.Contexto
 */
public class CacheModelo extends Cache<String, Object> {
    /**
     * Retorna o cache de objetos da camada dos modelos.
     *
     * @return cache da camada dos modelos
     */
    public final static CacheModelo getCacheModelo() {
        return (CacheModelo)getCache(CacheModelo.class.getName());
    }
}
