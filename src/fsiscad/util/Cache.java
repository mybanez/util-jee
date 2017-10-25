package fsiscad.util;

import fsiscad.contexto.*;
import java.util.*;

/**
 * Suporte para a implementa��o de caches guardados no <a href="Contexto.html">
 * contexto de execu��o</a> mantido pela <i>framework</i>. Os caches s�o 
 * armazenados no contexto usando como chave o nome do tipo do cache (subclasses 
 * de <tt>Cache</tt>). 
 * 
 * 
 * @see Contexto
 */
public abstract class Cache<TipoChave, TipoValor> extends HashMap<TipoChave, TipoValor> {

    /**
     * Retorna uma inst�ncia de cache deste tipo armazenado no contexto da 
     * aplica��o. Caso ainda n�o exista, cria a inst�ncia e guarda no contexto.
     *
     * @param tipo tipo do cache
     *
     * @return cache mantido no contexto da aplica��o
     */
    public final static Cache getCache(String tipo) {
        Contexto ctx = Contexto.getContextoCarregador();
        if (!ctx.itemDefinido(tipo)) {
            try {
                ctx.definir(tipo, FabricaObjetoLocal.getInstancia(tipo));
            } catch(ErroItemContextoJaDefinido e) {
                //Pode ocorrer se o item for definido ap�s o teste
            } catch(Exception e) {
                throw new ErroExecucao("Erro instanciando cache: "+tipo, e);
            }
        }
        return (Cache)ctx.buscar(tipo);
    }
}

