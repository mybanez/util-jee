package meyn.util;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Fábrica de objetos genérica. Esta classe cria objetos dinamicamente a partir
 * do nome de uma classe e de um <i>class loader</i>, podendo também mantê-los
 * em um cache de objetos.
 */
public class FabricaObjetoLocal {
	protected FabricaObjetoLocal() {
	}

	/**
	 * Retorna um objeto guardado neste cache a partir desta chave. Se o objeto não
	 * existir, cria um objeto desta classe e o insere no cache usando a chave
	 * informada. O novo objeto é então retornado. Este método usa o <i>context
	 * class loader</i> para carregar a classe.
	 *
	 * @param cache  cache de objetos
	 * @param chave  chave do objeto guardado no cache
	 * @param classe classe do objeto
	 *
	 * @return objeto guardado no cache
	 */
	public final static Object getInstanciaEmCache(Map<Object, Object> cache, Object chave, String classe) throws ClassNotFoundException,
	        InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return getInstanciaEmCache(cache, chave, Thread.currentThread().getContextClassLoader(), classe);
	}

	/**
	 * Retorna um objeto guardado neste cache a partir desta chave. Se o objeto não
	 * existir, carrega esta classe usando este <i>class loader</i>, cria um objeto
	 * da classe carregada e o insere no cache usando a chave informada.
	 *
	 * @param cache      cache de objetos
	 * @param chave      chave do objeto guardado no cache
	 * @param carregador <i>class loader</i>
	 * @param classe     classe do objeto
	 *
	 * @return objeto guardado no cache
	 */
	public final static Object getInstanciaEmCache(Map<Object, Object> cache, Object chave, ClassLoader carregador, String classe)
	        throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
	        SecurityException {

		Object obj = cache.get(chave);
		if (obj == null) {
			if (!cache.containsKey(chave)) {
				obj = getInstancia(carregador, classe);
				cache.put(chave, obj);
			}
		}
		return obj;
	}

	/**
	 * Cria um objeto desta classe. Este método usa o <i>context class loader</i>
	 * para carregar a classe.
	 *
	 * @param classe classe do objeto
	 *
	 * @return objeto instanciado
	 */
	public final static Object getInstancia(String classe) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
	        InvocationTargetException, NoSuchMethodException, SecurityException {
		return getInstancia(Thread.currentThread().getContextClassLoader(), classe);
	}

	/**
	 * Cria um objeto desta classe usando este <i>class loader</i> .
	 *
	 * @param classe     classe do objeto
	 * @param carregador <i>class loader</i>
	 *
	 * @return objeto instanciado
	 */
	public final static Object getInstancia(ClassLoader carregador, String classe) throws ClassNotFoundException, InstantiationException,
	        IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return carregador.loadClass(classe).getDeclaredConstructor().newInstance();
	}
}
