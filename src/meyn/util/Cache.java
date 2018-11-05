package meyn.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meyn.util.contexto.ContextoEmMemoria;

/**
 * Suporte para a implementação de caches guardados no <a href="Contexto.html">
 * contexto de execução</a> mantido pela <i>framework</i>. Os caches são
 * armazenados no contexto usando como chave o nome do tipo do cache (subclasses
 * de <tt>Cache</tt>).
 * 
 * 
 * @see ContextoEmMemoria
 */
@SuppressWarnings("serial")
public abstract class Cache<TipoChave, TipoValor> extends ConcurrentHashMap<TipoChave, TipoValor> {

	/**
	 * Retorna uma instância de cache deste tipo armazenado no contexto da
	 * aplicação. Caso ainda não exista, cria a instância e guarda no contexto.
	 *
	 * @param tipo tipo do cache
	 *
	 * @return cache mantido no contexto da aplicação
	 */
	public final static <TipoChave, TipoValor> Cache<TipoChave, TipoValor> getCache(
			Class<? extends Cache<TipoChave, TipoValor>> tipo) {
		return getCache(ContextoEmMemoria.getContextoCarregador(), tipo);
	}

	/**
	 * Retorna uma instância de cache deste tipo armazenado neste contexto. Caso
	 * ainda não exista, cria a instância e guarda no contexto.
	 *
	 * @param contexto contexto
	 * @param tipo     tipo do cache
	 *
	 * @return cache mantido no contexto da aplicação
	 */
	@SuppressWarnings("unchecked")
	public final static <TipoChave, TipoValor> Cache<TipoChave, TipoValor> getCache(ContextoEmMemoria contexto,
			Class<? extends Cache<TipoChave, TipoValor>> tipo) {
		String nomeCache = tipo.getName();
		if (!contexto.containsKey(nomeCache)) {
			try {
				Cache<?, ?> cache = (Cache<?, ?>) FabricaObjetoLocal.getInstancia(nomeCache);
				contexto.put(nomeCache, cache);
				cache.setContexto(contexto);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new ErroExecucao("Erro instanciando cache: " + nomeCache, e);
			}
		}
		return (Cache<TipoChave, TipoValor>) contexto.get(nomeCache);
	}

	private ContextoEmMemoria contexto;
	public boolean atualizado = false;

	private Logger logger = LogManager.getLogger(getClass());

	protected ContextoEmMemoria getContexto() {
		return contexto;
	}

	protected void setContexto(ContextoEmMemoria contexto) {
		this.contexto = contexto;
	}

	public boolean isAtualizado() {
		return atualizado;
	}

	public void setAtualizado(boolean atualizado) {
		this.atualizado = atualizado;
		if (!atualizado) {
			logger.debug("cache invalidado");
		}
	}
	
	public void invalidar() {
		setAtualizado(false);
	}

	protected Logger getLogger() {
		return logger;
	}
	
	protected void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public TipoValor put(TipoChave chave, TipoValor valor) {
		TipoValor res = super.put(chave, valor);
		logger.trace("carregado (cache): {}, {}", chave, valor.getClass().getName());
		return res;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Collection<Entry<TipoChave, TipoValor>> itens = entrySet();
		int cont = 1;
		String desc = getClass().getName() + '@' + Integer.toHexString(hashCode());
		sb.append("--- INICIO CACHE: ").append(desc).append(" ---\n");
		for (Entry<TipoChave, TipoValor> item : itens) {
			sb.append("Chave ").append(cont).append(":\n").append(item.getKey()).append("\nValor ").append(cont)
					.append(":\n").append(item.getValue()).append("\n");
			cont++;
		}
		sb.append("------ FIM CACHE: ").append(desc).append(" ---");
		return sb.toString();
	}
}
