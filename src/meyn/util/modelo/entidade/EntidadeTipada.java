package meyn.util.modelo.entidade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import meyn.util.beans.Componentes;

/**
 * Suporte para implementações de entidades de transferência tipadas. As
 * subclasses destes objetos devem necessariamente prover armazenamento e
 * implementar uma interface de acesso com métodos <tt>get</tt> e <tt>set</tt>
 * específicos para manipulaçao das suas propriedades. A implementação da
 * interface {@link meyn.util.beans.AcessoPropriedades AcessoPropriedades} feita
 * nesta classe procura estes métodos de acesso para a leitura e escrita das
 * propriedades. Os construtores recebem como parâmetro extra o tipo da
 * interface de acesso que a entidade implementa, passando os outros parâmetros
 * para os construtores correspondentes de {@link EntidadeImpl EntidadeImpl}.
 *
 * @see FabricaEntidade
 */
@SuppressWarnings("serial")
public class EntidadeTipada extends EntidadeMapeada implements InvocationHandler {

	/**
	 * Extrai os nomes das propriedades da entidade a partir da interface de acesso
	 * <tt>tipoAcessoProps</tt>. Ver {@link EntidadeImpl#EntidadeImpl(Collection)
	 * EntidadeImpl(Collection)}.
	 */
	EntidadeTipada(Class<?> tipoAcessoProps) {
		super(new ArrayList<String>(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet()));
		put("tipoAcessoPropriedades", tipoAcessoProps);
	}

	/**
	 * Extrai os nomes das propriedades da entidade definidos em
	 * <tt>clNomesProps</tt> a partir da interface de acesso
	 * <tt>tipoAcessoProps</tt>. Ver {@link EntidadeImpl#EntidadeImpl(Collection)
	 * EntidadeImpl(Collection)}.
	 */
	EntidadeTipada(Collection<String> clNomesProps, Class<?> tipoAcessoProps) {
		super(clNomesProps);
		put("tipoAcessoPropriedades", tipoAcessoProps);
	}

	/**
	 * Cria uma entidade com as propriedades e valores definidos neste mapa e que
	 * implementa esta interface de acesso.
	 *
	 * @param mpProps         nomes e valores das propriedades
	 * @param tipoAcessoProps interface de acesso às propriedades
	 */
	EntidadeTipada(Map<String, Object> mpProps, Class<?> tipoAcessoProps) {
		super(mpProps);
		put("tipoAcessoPropriedades", tipoAcessoProps);
	}

	@Override
	public final Class<?> getTipoAcessoPropriedades() {
		return (Class<?>) get("tipoAcessoPropriedades");
	}

	//// Implementação Invocation Handler ////

	private String getNomePropriedade(String nomeMetodo) {
		return Character.isLowerCase(nomeMetodo.charAt(1)) ? Character.toLowerCase(nomeMetodo.charAt(0)) + nomeMetodo.substring(1)
		        : nomeMetodo;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) {
		String nomeMetodo = method.getName();
		Class<?>[] tiposParams = method.getParameterTypes();
		Class<?> tipoRetorno = method.getReturnType();
		if (tiposParams.length == 0) {
			if (nomeMetodo.equals("hashCode")) {
				return hashCode();
			}
			if (nomeMetodo.equals("toString")) { // toString()
				StringBuffer sb = new StringBuffer();
				String desc = proxy.getClass().toGenericString();
				sb.append("--- INICIO ENTIDADE: " + desc + " ---\n");
				sb.append("Interfaces:\n");
				Class<?> tipos[] = proxy.getClass().getInterfaces();
				for (Class<?> tipo : tipos) {
					sb.append('-').append(tipo.getTypeName()).append('\n');
				}
				sb.append("Propriedades:\n");
				List<String> lsProps = new ArrayList<String>(getNomesPropriedades());
				ListIterator<String> iter = lsProps.listIterator();
				props : while (iter.hasNext()) {
					String prop = iter.next();
					Object valor = get(prop);
					if (valor != null && !(valor instanceof Entidade)) {
						if (valor instanceof Collection<?>) {
							Collection<?> cl = (Collection<?>)valor;
							if (cl.isEmpty()) {
								continue;
							}
							for (Object item : cl) {
								if (item instanceof Entidade) {
									continue props;
								}
								break;
							}
						}
						sb.append('-').append(prop).append(": ").append(valor).append('\n');
						iter.remove();
					}
				}
				sb.append("Relacionamentos:\n");
				iter = lsProps.listIterator();
				while (iter.hasNext()) {
					String prop = iter.next();
					Object valor = get(prop);
					if (valor != null) {
						if (valor instanceof Collection<?> && ((Collection<?>)valor).isEmpty()) {
							continue;
						}
						sb.append('-').append(prop).append('\n');
						iter.remove();
					}
				}
				sb.append("Não definido:\n");
				for (String prop : lsProps) {
					sb.append('-').append(prop).append('\n');
				}
				sb.append("------ FIM ENTIDADE: " + desc + " ---\n");
				return sb.toString();
			}
			if (nomeMetodo.startsWith("is") && tipoRetorno.equals(boolean.class)) { // isXXX()
				return get(getNomePropriedade(nomeMetodo.substring(2)));
			}
		}
		if (tiposParams.length == 1) {
			if (nomeMetodo.equals("equals")) {
				return proxy == args[0];
			}
		}
		if (nomeMetodo.startsWith("get")) {
			if (nomeMetodo.length() > 3) {
				return get(getNomePropriedade(nomeMetodo.substring(3))); // getXXX()
			}
			if (tiposParams.length == 1) {
				if (String.class.isAssignableFrom(args[0].getClass())) {
					return get((String) args[0]); // get(String)
				}
				return get((Collection<String>) args[0]); // get(Collection)
			}
		}
		if (nomeMetodo.startsWith("set")) {
			if (nomeMetodo.length() > 3) {
				set(getNomePropriedade(nomeMetodo.substring(3)), args[0]); // setXXX(...)
				return null;
			}
			if (tiposParams.length == 2) {
				set((String) args[0], args[1]); // set(String,Object)
				return null;
			}
			set((Map<String, Object>) args[0]); // set(Map)
			return null;
		}
		throw new UnsupportedOperationException(proxy.getClass() + ": " + nomeMetodo);
	}
}
