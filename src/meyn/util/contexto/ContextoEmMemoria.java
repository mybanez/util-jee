package meyn.util.contexto;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contexto de execução de uma aplicação. Múltiplos contextos podem ser criados
 * e associados a chaves genéricas. A chave identifica unicamente uma instância
 * de contexto <b>no escopo do <i>class loader</i> desta classe</b>. O contexto
 * em si é um conjunto arbitrário de vínculos entre chaves <tt>String</tt> e
 * objetos.
 * <p>
 * Esta classe é utilizada pela <i>framework</i> para armazenamento e
 * recuperação de objetos de controle internos sem incorrer nos problemas de
 * performance inerentes ao uso do contexto JNDI. Se a <i>framework</i> for
 * carregada como biblioteca pelo <i>class loader</i> de sistema, o espaço de
 * chaves de contexto é o mesmo para todas as aplicações. Ainda assim, serão
 * mantidos contextos (espaços de nomes) distintos para os diferentes módulos
 * das aplicações JEE disponibilizadas em uma mesma máquina virtual,
 * independentemente da organização hierárquica dos <i>class loaders</i> do
 * container JEE. Isto é possível porque as classes da <i>framework</i> fazem
 * chamadas ao método {@link ContextoEmMemoria#getContextoCarregador()
 * getContextoCarregador()}. <b>Este método adota o <i>context class loader</i>
 * como chave de recuperação do contexto</b>, o que garante a existência de
 * contextos de execução separados por módulo de aplicação. Este artifício não
 * faz diferença quando as classes da <i>framework</i> são carregadas junto com
 * os módulos das aplicações.
 */
@SuppressWarnings("serial")
public class ContextoEmMemoria extends ConcurrentHashMap<String, Object> {

	private static final Map<String, ContextoEmMemoria> MP_CONTEXTOS = new ConcurrentHashMap<String, ContextoEmMemoria>();

	private static Logger LOGGER = null;

	protected static Logger getStaticLogger() {
		if (LOGGER == null) {
			LOGGER = LogManager.getLogger(ContextoEmMemoria.class);
		}
		return LOGGER;
	}

	/**
	 * Retorna o contexto da aplicação usado pela <i>framework</i>, associado ao
	 * <i>context class loader</i>.
	 *
	 * @return contexto da aplicação associado ao <i>context class loader</i>
	 */
	public static ContextoEmMemoria getContextoCarregador() {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		if (!isDefinido(carregador.toString())) {
			try {
				definir(carregador.toString(), new ContextoEmMemoria());
			} catch(ErroContextoJaDefinido e) {
				/* Erro pode acontecer por concorrência. Estratégia é não sincronizar para ganhar performance,
				   assumindo que este contexto seja definido uma única vez para a aplicação. */
			}
		}
		return buscar(carregador.toString());
	}

	/**
	 * Testa se um contexto de execução já foi definido para esta chave.
	 *
	 * @param chave chave do contexto
	 *
	 * @return <tt>true</tt> se o contexto já foi definido
	 */
	public static boolean isDefinido(String chave) {
		return MP_CONTEXTOS.containsKey(chave);
	}

	/**
	 * Recupera o contexto de execução definido para esta chave.
	 *
	 * @param chave chave do contexto
	 *
	 * @return contexto de execução
	 */
	public static ContextoEmMemoria buscar(String chave) {
		if (!isDefinido(chave)) {
			throw new ErroContextoNaoDefinido(chave.toString());
		}
		return MP_CONTEXTOS.get(chave);
	}

	/**
	 * Define este contexto de execução para esta chave.
	 *
	 * @param chave    chave do contexto
	 * @param contexto contexto de execução
	 */
	public static void definir(String chave, ContextoEmMemoria contexto) {
		if (isDefinido(chave)) {
			throw new ErroContextoJaDefinido(chave.toString());
		}
		MP_CONTEXTOS.put(chave, contexto);
		getStaticLogger().debug("inserido: {}, {}", chave, contexto.getClass().getName());
	}

	/**
	 * Remove o contexto de execução definido para esta chave.
	 *
	 * @param chave chave do contexto
	 */
	public static void remover(String chave) {
		if (!isDefinido(chave)) {
			throw new ErroContextoNaoDefinido(chave.toString());
		}
		MP_CONTEXTOS.remove(chave);
		getStaticLogger().debug("removido: {}", chave);
	}

	/**
	 * Redefine o contexto de execução para esta chave. Se um contexto já havia sido
	 * definido, o mesmo será substituído pelo novo contexto.
	 *
	 * @param chave    chave do contexto
	 * @param contexto contexto de execução
	 */
	public static void redefinir(String chave, ContextoEmMemoria contexto) {
		MP_CONTEXTOS.put(chave, contexto);
		getStaticLogger().debug("redefinido: {}", chave);
	}

	private static String toString(Map<String, Object> map, String desc) {
		StringBuffer sb = new StringBuffer();
		Collection<Entry<String, Object>> itens = map.entrySet();
		int cont = 1;
		sb.append("--- INICIO CONTEXTO: ").append(desc).append(" ---\n");
		for (Entry<?, ?> item : itens) {
			sb.append("Chave ").append(cont).append(":\n").append(item.getKey()).append("\nValor ").append(cont)
					.append(":\n").append(item.getValue()).append("\n");
			cont++;
		}
		sb.append("------ FIM CONTEXTO: ").append(desc).append(" ---");
		return sb.toString();
	}

	private final Logger logger = LogManager.getLogger(getClass().getName());

	protected ContextoEmMemoria() {
		logger.debug("iniciado");
	}

	@Override
	public String toString() {
		return toString(this, getClass().getName() + '@' + Integer.toHexString(hashCode()));
	}
}
