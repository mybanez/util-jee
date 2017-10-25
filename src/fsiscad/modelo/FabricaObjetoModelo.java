package fsiscad.modelo;

import fsiscad.util.*;

/**
 * F�brica de objetos da camada dos modelos. Esta classe instancia objetos
 * dinamicamente e os mant�m no cache da camada dos modelos, implementado em
 * {@link CacheModelo CacheModelo}, de onde podem ser recuperados
 * a partir de uma chave fornecida. � garantida a exist�ncia de <b>um �nico
 * objeto por chave por <i>class loader</i></b>.
 */
public class FabricaObjetoModelo extends FabricaObjetoLocal {
    protected FabricaObjetoModelo() {}
    
    /**
     * Retorna um objeto do cache do modelo a partir desta chave. O objeto j�
     * deve ter sido previamente instanciado e inserido no cache. Se o objeto
     * n�o existir, o m�todo retorna <tt>null</tt>.
     *
     * @param chave chave do objeto guardado no cache
     *
     * @return objeto guardado no cache
     */
    public final static Object getInstanciaEmCache(Object chave) {
        return getInstanciaEmCache(CacheModelo.getCacheModelo(), chave);
    }
    
    /**
     * Retorna um objeto do cache do modelo a partir desta chave. Se o objeto n�o
     * existir, cria um objeto desta classe e o insere no cache usando a chave
     * informada. 
     *
     * @param chave chave do objeto
     * @param classe classe usada para criar o objeto, se necess�rio
     *
     * @return objeto guardado no cache
     *
     * @throws ErroModelo se ocorrer um erro durante a instancia��o do objeto
     */
    public final static Object getInstanciaEmCache(Object chave, String classe)
    throws ErroModelo {
        try {
            return getInstanciaEmCache(CacheModelo.getCacheModelo(), chave, classe);
        } catch (Exception e) {
            throw new ErroModelo("Erro instanciando objeto do modelo", e);
        }
    }
}
