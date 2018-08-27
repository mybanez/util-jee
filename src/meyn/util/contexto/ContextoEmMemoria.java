package meyn.util.contexto;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contexto de execu��o de uma aplica��o. M�ltiplos contextos podem ser criados
 * e associados a chaves gen�ricas. A chave identifica unicamente uma inst�ncia
 * de contexto <b>no escopo do <i>class loader</i> desta classe</b>. O contexto
 * em si � um conjunto arbitr�rio de v�nculos entre chaves <tt>String</tt> e
 * objetos.
 * <p>
 * Esta classe � utilizada pela <i>framework</i> para armazenamento e
 * recupera��o de objetos de controle internos sem incorrer nos problemas de
 * performance inerentes ao uso do contexto JNDI. Se a <i>framework</i> for
 * carregada como biblioteca pelo <i>class loader</i> de sistema, o espa�o de
 * chaves de contexto � o mesmo para todas as aplica��es. Ainda assim, ser�o
 * mantidos contextos (espa�os de nomes) distintos para os diferentes m�dulos
 * das aplica��es JEE disponibilizadas em uma mesma m�quina virtual,
 * independentemente da organiza��o hier�rquica dos <i>class loaders</i> do
 * container JEE. Isto � poss�vel porque as classes da <i>framework</i> fazem
 * chamadas ao m�todo {@link ContextoEmMemoria#getContextoCarregador()
 * getContextoCarregador()}. <b>Este m�todo adota o <i>context class loader</i>
 * como chave de recupera��o do contexto</b>, o que garante a exist�ncia de
 * contextos de execu��o separados por m�dulo de aplica��o. Este artif�cio n�o
 * faz diferen�a quando as classes da <i>framework</i> s�o carregadas junto com
 * os m�dulos das aplica��es.
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
	 * Retorna o contexto da aplica��o usado pela <i>framework</i>, associado ao
	 * <i>context class loader</i>.
	 *
	 * @return contexto da aplica��o associado ao <i>context class loader</i>
	 */
	public static ContextoEmMemoria getContextoCarregador() {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		if (!isDefinido(carregador.toString())) {
			try {
				definir(carregador.toString(), new ContextoEmMemoria());
			} catch(ErroContextoJaDefinido e) {
				/* Erro pode acontecer por concorr�ncia. Estrat�gia � n�o sincronizar para ganhar performance,
				   assumindo que este contexto seja definido uma �nica vez para a aplica��o. */
			}
		}
		return buscar(carregador.toString());
	}

	/**
	 * Testa se um contexto de execu��o j� foi definido para esta chave.
	 *
	 * @param chave chave do contexto
	 *
	 * @return <tt>true</tt> se o contexto j� foi definido
	 */
	public static boolean isDefinido(String chave) {
		return MP_CONTEXTOS.containsKey(chave);
	}

	/**
	 * Recupera o contexto de execu��o definido para esta chave.
	 *
	 * @param chave chave do contexto
	 *
	 * @return contexto de execu��o
	 */
	public static ContextoEmMemoria buscar(String chave) {
		if (!isDefinido(chave)) {
			throw new ErroContextoNaoDefinido(chave.toString());
		}
		return MP_CONTEXTOS.get(chave);
	}

	/**
	 * Define este contexto de execu��o para esta chave.
	 *
	 * @param chave    chave do contexto
	 * @param contexto contexto de execu��o
	 */
	public static void definir(String chave, ContextoEmMemoria contexto) {
		if (isDefinido(chave)) {
			throw new ErroContextoJaDefinido(chave.toString());
		}
		MP_CONTEXTOS.put(chave, contexto);
		getStaticLogger().debug("inserido: {}, {}", chave, contexto.getClass().getName());
	}

	/**
	 * Remove o contexto de execu��o definido para esta chave.
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
	 * Redefine o contexto de execu��o para esta chave. Se um contexto j� havia sido
	 * definido, o mesmo ser� substitu�do pelo novo contexto.
	 *
	 * @param chave    chave do contexto
	 * @param contexto contexto de execu��o
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
