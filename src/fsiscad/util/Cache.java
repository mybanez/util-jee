package fsiscad.util;

import fsiscad.contexto.*;
import java.util.*;

/**
 * Suporte para a implementação de caches guardados no <a href="Contexto.html">
 * contexto de execução</a> mantido pela <i>framework</i>. Os caches são 
 * armazenados no contexto usando como chave o nome do tipo do cache (subclasses 
 * de <tt>Cache</tt>). 
 * 
 * 
 * @see Contexto
 */
public abstract class Cache<TipoChave, TipoValor> extends HashMap<TipoChave, TipoValor> {

    /**
     * Retorna uma instância de cache deste tipo armazenado no contexto da 
     * aplicação. Caso ainda não exista, cria a instância e guarda no contexto.
     *
     * @param tipo tipo do cache
     *
     * @return cache mantido no contexto da aplicação
     */
    public final static Cache getCache(String tipo) {
        Contexto ctx = Contexto.getContextoCarregador();
        if (!ctx.itemDefinido(tipo)) {
            try {
                ctx.definir(tipo, FabricaObjetoLocal.getInstancia(tipo));
            } catch(ErroItemContextoJaDefinido e) {
                //Pode ocorrer se o item for definido após o teste
            } catch(Exception e) {
                throw new ErroExecucao("Erro instanciando cache: "+tipo, e);
            }
        }
        return (Cache)ctx.buscar(tipo);
    }
}

