package meyn.util.modelo.ot;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Implementação da interface {@link java.lang.reflect.InvocationHandler
 * java.lang.reflect.InvocationHandler} para objetos de transferência.
 *
 * @see java.lang.reflect.Proxy
 **/
@SuppressWarnings("serial")
public class OTInvocationHandler extends OTMapeado implements InvocationHandler {
	private static final Collection<OT> COLECAO_OT = new ArrayList<OT>();

	private Class<?> tipoAcessoProps;

	public OTInvocationHandler(Collection<String> clNomesProps, Class<?> tipoAcessoProps) {
		super(clNomesProps);
		this.tipoAcessoProps = tipoAcessoProps;
	}

	public OTInvocationHandler(Map<String, Object> mpProps, Class<?> tipoAcessoProps) {
		super(mpProps);
		this.tipoAcessoProps = tipoAcessoProps;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String nomeMetodo = method.getName();
		Class<?>[] tiposParams = method.getParameterTypes();
		Class<?> tipoRetorno = method.getReturnType();
		if (tiposParams.length == 0) {
			if (nomeMetodo.equals("hashCode")) {
				return hashCode();
			}
			if (nomeMetodo.equals("toString")) {
				StringBuffer sb = new StringBuffer();
				String desc = proxy.getClass().toGenericString();
				sb.append("--- INICIO ENTIDADE: " + desc + " ---\n");
				sb.append("Interfaces:\n");
				Class<?> tipos[] = proxy.getClass().getInterfaces();
				for (Class<?> tipo : tipos) {
					sb.append('-');
					sb.append(tipo.getTypeName());
					sb.append('\n');
				}
				sb.append("Propriedades:\n");
				List<String> lsProps = new ArrayList<String>(getNomesPropriedades());
				ListIterator<String> iter = lsProps.listIterator();
				while (iter.hasNext()) {
					String prop = iter.next();
					Object valor = get(prop);
					if (valor != null && !(valor instanceof OT) && !COLECAO_OT.getClass().isInstance(valor)) {
						sb.append('-');
						sb.append(prop);
						sb.append(": ");
						sb.append(valor);
						sb.append('\n');
						iter.remove();
					}
				}
				sb.append("Relacionamentos:\n");
				iter = lsProps.listIterator();
				while (iter.hasNext()) {
					String prop = iter.next();
					if (get(prop) != null) {
						sb.append('-');
						sb.append(prop);
						sb.append('\n');
						iter.remove();
					}
				}
				sb.append("Não definido:\n");
				for (String prop : lsProps) {
					sb.append('-');
					sb.append(prop);
					sb.append('\n');
				}
				sb.append("------ FIM ENTIDADE: " + desc + " ---\n");
				return sb.toString();
			}
			if (nomeMetodo.equals("getTipoAcessoPropriedades")) {
				return tipoAcessoProps;
			}
			if (nomeMetodo.equals("getNomesPropriedades")) {
				return getNomesPropriedades();
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
			return get(); // get()
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

	private String getNomePropriedade(String nomeMetodo) {
		return Character.isLowerCase(nomeMetodo.charAt(1))
				? Character.toLowerCase(nomeMetodo.charAt(0)) + nomeMetodo.substring(1)
				: nomeMetodo;
	}
}