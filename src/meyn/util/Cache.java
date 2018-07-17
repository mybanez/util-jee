package meyn.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meyn.util.contexto.Contexto;
import meyn.util.modelo.ErroModelo;

/**
 * Suporte para a implementação de caches guardados no <a href="Contexto.html">
 * contexto de execução</a> mantido pela <i>framework</i>. Os caches são
 * armazenados no contexto usando como chave o nome do tipo do cache (subclasses
 * de <tt>Cache</tt>).
 * 
 * 
 * @see Contexto
 */
@SuppressWarnings("serial")
public abstract class Cache<TipoChave, TipoValor> extends HashMap<TipoChave, TipoValor> {

	private Contexto contexto;
	private boolean atualizado = false;

	private final Logger logger = LogManager.getLogger(getClass());
	
	/**
	 * Retorna uma instância de cache deste tipo armazenado no contexto da
	 * aplicação. Caso ainda não exista, cria a instância e guarda no contexto.
	 *
	 * @param tipo
	 *            tipo do cache
	 *
	 * @return cache mantido no contexto da aplicação
	 */
	public final static <TipoChave, TipoValor> Cache<TipoChave, TipoValor> getCache(
			Class<? extends Cache<TipoChave, TipoValor>> tipo) {
		return getCache(Contexto.getContextoCarregador(), tipo);
	}

	/**
	 * Retorna uma instância de cache deste tipo armazenado neste contexto. Caso
	 * ainda não exista, cria a instância e guarda no contexto.
	 *
	 * @param ctx
	 *            contexto
	 * @param tipo
	 *            tipo do cache
	 *
	 * @return cache mantido no contexto da aplicação
	 */
	@SuppressWarnings("unchecked")
	public final static <TipoChave, TipoValor> Cache<TipoChave, TipoValor> getCache(Contexto ctx,
			Class<? extends Cache<TipoChave, TipoValor>> tipo) {
		String nomeCache = tipo.getName();
		if (!ctx.containsKey(nomeCache)) {
			try {
				Cache<?, ?> cache = (Cache<?, ?>) FabricaObjetoLocal.getInstancia(nomeCache);
				ctx.put(nomeCache, cache);
				cache.setContexto(ctx);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new ErroExecucao("Erro instanciando cache: " + nomeCache, e);
			}
		}
		return (Cache<TipoChave, TipoValor>) ctx.get(nomeCache);
	}

	protected Cache() {
		logger.debug("instanciado");
	}
	
	protected Contexto getContexto() {
		return contexto;
	}

	protected void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}

	public boolean isAtualizado() throws ErroModelo {
		return atualizado;
	}

	public void setAtualizado(boolean atualizado) throws ErroModelo {
		this.atualizado = atualizado;
	}

	protected Logger getLogger() {
		return logger;
	}

	@Override
	public TipoValor put(TipoChave chave, TipoValor valor) {
		TipoValor res = super.put(chave, valor);
		logger.trace("inserido: {}, {}", chave, valor.getClass().getName());
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
