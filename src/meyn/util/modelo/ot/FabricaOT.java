package meyn.util.modelo.ot;

import java.lang.reflect.*;

import java.util.*;

import meyn.util.beans.*;

/**
 * Fabrica de objetos de transferência. Os métodos que não passam uma interface
 * de acesso às propriedades do ot retornam instãncias de {@link OTMapeado
 * OTMapeado}. Já os que passam uma interface de acesso retornam instancias de
 * ot criadas através de chamadas a
 * {@link Proxy#newProxyInstance(ClassLoader,Class[],InvocationHandler)
 * Proxy.newProxyInstance(ClassLoader,Class[],InvocationHandler)}, usando uma
 * instância de {@link OTInvocationHandler OTInvocationHandler} como
 * <i>invocation handler</i>.
 *
 * @see OTMapeado
 * @see OTTipado
 */
public final class FabricaOT {

	private FabricaOT() {
	}

	/**
	 * Ver {@link OTMapeado#OTMapeado() OTMapeado()}.
	 */
	public static OT getInstancia() {
		return new OTMapeado();
	}

	/**
	 * Ver {@link OTMapeado#OTMapeado(Collection) OTMapeado(Collection)}.
	 */
	public static OT getInstancia(Collection<String> clNomesProps) {
		return new OTMapeado(clNomesProps);
	}

	/**
	 * Ver {@link OTMapeado#OTMapeado(Map) OTMapeado(Map)}.
	 */
	public static OT getInstancia(Map<String, Object> mpProps) {
		return new OTMapeado(mpProps);
	}

	/**
	 * Ver {@link OTTipado#OTTipado(Class) OTTipado(Class)}.
	 */
	public static <TipoOT extends OT>TipoOT getInstancia(Class<?> tipoAcessoProps) {
		return getInstancia(new ArrayList<String>(), tipoAcessoProps);
	}

	/**
	 * Ver {@link OTTipado#OTTipado(Collection,Class) OTTipado(Collection,Class)}.
	 */
	@SuppressWarnings("unchecked")
	public static <TipoOT extends OT>TipoOT getInstancia(Collection<String> clNomesProps, Class<?> tipoAcessoProps) {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		InvocationHandler ih;
		if (Proxy.isProxyClass(tipoAcessoProps)) {
			ih = Proxy.getInvocationHandler(tipoAcessoProps);
			if (ih instanceof OTInvocationHandler) {
				tipoAcessoProps = ((OTInvocationHandler) ih).getTipoAcessoPropriedades();
			}
		}
		Set<Class<?>> stTipos = new HashSet<Class<?>>();
		stTipos.add(OT.class);
		if (tipoAcessoProps.isInterface()) {
			stTipos.add(tipoAcessoProps);
		}
		stTipos.addAll(Arrays.asList(tipoAcessoProps.getInterfaces()));
		if (clNomesProps.isEmpty()) {
			clNomesProps.addAll(Componentes.getDescritoresPropriedades(tipoAcessoProps).keySet());
		}
		ih = new OTInvocationHandler(clNomesProps, tipoAcessoProps);
		return (TipoOT) Proxy.newProxyInstance(carregador, stTipos.toArray(new Class<?>[0]), ih);
	}

	/**
	 * Ver {@link OTTipado#OTTipado(Map,Class) OTTipado(Map,Class)}.
	 */
	@SuppressWarnings("unchecked")
	public static <TipoOT extends OT>TipoOT getInstancia(Map<String, Object> mpProps, Class<?> tipoAcessoProps) {
		ClassLoader carregador = Thread.currentThread().getContextClassLoader();
		InvocationHandler ih;
		if (Proxy.isProxyClass(tipoAcessoProps)) {
			ih = Proxy.getInvocationHandler(tipoAcessoProps);
			if (ih instanceof OTInvocationHandler) {
				tipoAcessoProps = ((OTInvocationHandler) ih).getTipoAcessoPropriedades();
			}
		}
		Set<Class<?>> stTipos = new HashSet<Class<?>>();
		stTipos.add(OT.class);
		if (tipoAcessoProps.isInterface()) {
			stTipos.add(tipoAcessoProps);
		}
		stTipos.addAll(Arrays.asList(tipoAcessoProps.getInterfaces()));
		if (mpProps.isEmpty()) {
			mpProps.putAll(Componentes.getDescritoresPropriedades(tipoAcessoProps));
		}
		ih = new OTInvocationHandler(mpProps, tipoAcessoProps);
		return (TipoOT) Proxy.newProxyInstance(carregador, stTipos.toArray(new Class<?>[0]), ih);
	}
}
