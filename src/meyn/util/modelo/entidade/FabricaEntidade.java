package meyn.util.modelo.entidade;

import java.lang.reflect.*;

import java.util.*;

import meyn.util.beans.*;

/**
 * Fabrica de objetos de transferência. Os métodos que não passam uma interface
 * de acesso às propriedades da entidade retornam instãncias de {@link EntidadeMapeada
 * EntidadeMapeada}. Já os que passam uma interface de acesso retornam instâncias de
 * entidade criadas através de chamadas a
 * {@link Proxy#newProxyInstance(ClassLoader,Class[],InvocationHandler)
 * Proxy.newProxyInstance(ClassLoader,Class[],InvocationHandler)}, usando uma
 * instância de {@link EntidadeInvocationHandler EntidadeInvocationHandler} como
 * <i>invocation handler</i>.
 *
 * @see EntidadeMapeada
 * @see EntidadeTipada
 */
public final class FabricaEntidade {

	private FabricaEntidade() {
	}

	/**
	 * Ver {@link EntidadeMapeada#EntidadeMapeada(Collection) EntidadeMapeada(Collection)}.
	 */
	public static Entidade getInstancia(Collection<String> clNomesProps) {
		return new EntidadeMapeada(clNomesProps);
	}

	/**
	 * Ver {@link EntidadeMapeada#EntidadeMapeada(Map) EntidadeMapeada(Map)}.
	 */
	public static Entidade getInstancia(Map<String, Object> mpProps) {
		return new EntidadeMapeada(mpProps);
	}

	/**
	 * Ver {@link EntidadeTipada#EntidadeTipada(Class) EntidadeTipada(Class)}.
	 */
	public static <TipoEnt extends Entidade>TipoEnt getInstancia(Class<?> tipoAcessoProps) {
		return getInstancia(new ArrayList<String>(), tipoAcessoProps);
	}

	/**
	 * Ver {@link EntidadeTipada#EntidadeTipada(Collection,Class) EntidadeTipada(Collection,Class)}.
	 */
	@SuppressWarnings("unchecked")
	public static <TipoEnt extends Entidade>TipoEnt getInstancia(Collection<String> clNomesProps, Class<?> tipoAcessoProps) {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		InvocationHandler ih;
		if (Proxy.isProxyClass(tipoAcessoProps)) {
			ih = Proxy.getInvocationHandler(tipoAcessoProps);
			if (ih instanceof EntidadeTipada) {
				tipoAcessoProps = ((EntidadeTipada) ih).getTipoAcessoPropriedades();
			}
		}
		Set<Class<?>> stTipos = new HashSet<Class<?>>();
		stTipos.add(Entidade.class);
		if (tipoAcessoProps.isInterface()) {
			stTipos.add(tipoAcessoProps);
		}
		stTipos.addAll(Arrays.asList(tipoAcessoProps.getInterfaces()));
		if (clNomesProps.isEmpty()) {
			clNomesProps.addAll(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet());
		}
		ih = new EntidadeTipada(clNomesProps, tipoAcessoProps);
		return (TipoEnt) Proxy.newProxyInstance(carregador, stTipos.toArray(new Class<?>[0]), ih);
	}

	/**
	 * Ver {@link EntidadeTipada#EntidadeTipada(Map,Class) EntidadeTipada(Map,Class)}.
	 */
	@SuppressWarnings("unchecked")
	public static <TipoEnt extends Entidade>TipoEnt getInstancia(Map<String, Object> mpProps, Class<?> tipoAcessoProps) {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		InvocationHandler ih;
		if (Proxy.isProxyClass(tipoAcessoProps)) {
			ih = Proxy.getInvocationHandler(tipoAcessoProps);
			if (ih instanceof EntidadeTipada) {
				tipoAcessoProps = ((EntidadeTipada) ih).getTipoAcessoPropriedades();
			}
		}
		Set<Class<?>> stTipos = new HashSet<Class<?>>();
		stTipos.add(Entidade.class);
		if (tipoAcessoProps.isInterface()) {
			stTipos.add(tipoAcessoProps);
		}
		stTipos.addAll(Arrays.asList(tipoAcessoProps.getInterfaces()));
		if (mpProps.isEmpty()) {
			mpProps.putAll(Componentes.getDescritoresPropriedades(tipoAcessoProps));
		}
		ih = new EntidadeTipada(mpProps, tipoAcessoProps);
		return (TipoEnt) Proxy.newProxyInstance(carregador, stTipos.toArray(new Class<?>[0]), ih);
	}
}
