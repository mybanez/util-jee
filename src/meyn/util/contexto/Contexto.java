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
 * chamadas ao método {@link Contexto#getContextoCarregador()
 * getContextoCarregador()}. <b>Este método adota o <i>context class loader</i>
 * como chave de recuperação do contexto</b>, o que garante a existência de
 * contextos de execução separados por módulo de aplicação. Este artifício não
 * faz diferença quando as classes da <i>framework</i> são carregadas junto com
 * os módulos das aplicações.
 */
@SuppressWarnings("serial")
public class Contexto extends ConcurrentHashMap<String, Object> {

	private static final Map<String, Contexto> MP_CONTEXTOS = new ConcurrentHashMap<String, Contexto>();
	
	private static final Logger LOGGER = LogManager.getLogger(Contexto.class);

	private final Logger logger = LogManager.getLogger(getClass().getName());
	
	/**
	 * Retorna o contexto da aplicação usado pela <i>framework</i>, associado ao
	 * <i>context class loader</i>.
	 *
	 * @return contexto da aplicação associado ao <i>context class loader</i>
	 */
	public static Contexto getContextoCarregador() {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		if (!contextoGlobalDefinido(carregador.toString())) {
			try {
				definirContextoGlobal(carregador.toString(), new Contexto());
			} catch (ErroContextoJaDefinido e) {
				// Pode ocorrer se o contexto for definido após o teste
			}
		}
		return buscarContextoGlobal(carregador.toString());
	}

	/**
	 * Testa se um contexto de execução já foi definido para esta chave.
	 *
	 * @param chave
	 *            chave do contexto
	 *
	 * @return <tt>true</tt> se o contexto já foi definido
	 */
	public static boolean contextoGlobalDefinido(String chave) {
		return MP_CONTEXTOS.containsKey(chave);
	}

	/**
	 * Recupera o contexto de execução definido para esta chave.
	 *
	 * @param chave
	 *            chave do contexto
	 *
	 * @return contexto de execução
	 */
	public static Contexto buscarContextoGlobal(String chave) {
		if (!contextoGlobalDefinido(chave)) {
			throw new ErroContextoNaoDefinido(chave.toString());
		}
		return MP_CONTEXTOS.get(chave);
	}

	/**
	 * Define este contexto de execução para esta chave.
	 *
	 * @param chave
	 *            chave do contexto
	 * @param contexto
	 *            contexto de execução
	 */
	public static void definirContextoGlobal(String chave, Contexto contexto) {
		if (contextoGlobalDefinido(chave)) {
			throw new ErroContextoJaDefinido(chave.toString());
		}
		MP_CONTEXTOS.put(chave, contexto);
		//Não disponível durante inicialização do LOG4J
		if (LOGGER != null) {
			LOGGER.debug("inserido: {}, {}", chave, contexto.getClass().getName());
		}	
	}

	/**
	 * Remove o contexto de execução definido para esta chave.
	 *
	 * @param chave
	 *            chave do contexto
	 */
	public static void removerContextoGlobal(String chave) {
		if (!contextoGlobalDefinido(chave)) {
			throw new ErroContextoNaoDefinido(chave.toString());
		}
		MP_CONTEXTOS.remove(chave);
		LOGGER.debug("removido: {}", chave);
	}

	/**
	 * Redefine o contexto de execução para esta chave. Se um contexto já havia sido
	 * definido, o mesmo será substituído pelo novo contexto.
	 *
	 * @param chave
	 *            chave do contexto
	 * @param contexto
	 *            contexto de execução
	 */
	public static void redefinirContextoGlobal(String chave, Contexto contexto) {
		MP_CONTEXTOS.put(chave, contexto);
	}

	private static String toString(Map<String, Object> map, String desc) {
		StringBuffer sb = new StringBuffer();
		Collection<Entry<String, Object>> itens = map.entrySet();
		int cont = 1;
		sb.append("--- INICIO CONTEXTO: "+desc+" ---\n");
		for (Entry<?, ?> item : itens) {
			sb.append("Chave "+cont+":\n" + item.getKey() + "\nValor "+cont+":\n" + item.getValue() + "\n");
			cont++;
		}
		sb.append("------ FIM CONTEXTO: "+desc+" ---");
		return sb.toString();
	}

	protected Contexto() {
		logger.debug("iniciado");
	}
	
	@Override
	public String toString() {
		return toString(this, getClass().getName() + '@' + Integer.toHexString(hashCode()));
	}
}
