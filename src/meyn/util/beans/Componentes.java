package meyn.util.beans;

import java.util.*;

import meyn.util.*;

import java.beans.*;
import java.lang.reflect.*;

/**
 * Classe com rotinas para a manipulação de componentes JavaBean via
 * introspecção.
 */
public final class Componentes {
	private Componentes() {
	}

	private static void getDescritoresPropriedades(Class<?> tipo, Map<String, PropertyDescriptor> mpInfoProps)
			throws IntrospectionException {
		PropertyDescriptor clInfoProp[] = Introspector.getBeanInfo(tipo).getPropertyDescriptors();
		for (PropertyDescriptor infoProp : clInfoProp) {
			mpInfoProps.put(infoProp.getName(), infoProp);
		}
		if (tipo.isInterface()) {
			Class<?> clItfs[] = tipo.getInterfaces();
			for (Class<?> itf : clItfs) {
				getDescritoresPropriedades(itf, mpInfoProps);
			}
		}
	}

	/**
	 * Para as propriedades definidas neste tipo, mapeia os descritores das
	 * propriedades deste <i>bean</i>. Cada entrada no mapa retornado associa o nome
	 * da propriedade a uma instancia de {@link java.beans.PropertyDescriptor
	 * PropertyDescriptor}.
	 *
	 * @param tipo tipo que define as propriedades a serem mapeadas
	 *
	 * @return mapa com os nomes e descritores das propriedades do JavaBean
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, PropertyDescriptor> getDescritoresPropriedades(Class<?> tipo) {
		CacheMetaDados cache = CacheMetaDados.getCacheMetaDados();
		Map<String, PropertyDescriptor> mpInfoProps = (Map<String, PropertyDescriptor>) cache.get(tipo.getName());
		if (mpInfoProps == null) {
			try {
				mpInfoProps = new HashMap<String, PropertyDescriptor>();
				getDescritoresPropriedades(tipo, mpInfoProps);
				cache.put(tipo.getName(), mpInfoProps);
			} catch (IntrospectionException ie) {
				throw new ErroExecucao("Erro obtendo metadados", ie);
			}
		}
		return mpInfoProps;
	}

	/**
	 * Mapeia os valores armazenados nas propriedades deste <i>bean</i>. Equivale a
	 * uma chamada a <tt>getValoresPropriedades(bean.getClass(), bean, false)</tt>.
	 *
	 *
	 * @param tipo tipo que define as propriedades a serem mapeadas
	 * @param bean JavaBean cujas propriedades serão mapeadas
	 * @return mapa com os nomes e valores das propriedades do JavaBean
	 */
	public static Map<String, Object> getValoresPropriedades(Object bean) {
		return getValoresPropriedades(bean.getClass(), bean, false);
	}

	/**
	 * Para as propriedades definidas neste tipo, mapeia os valores armazenados
	 * neste <i>bean</i>. Equivale a uma chamada a
	 * <tt>getValoresPropriedades(tipo, bean, true)</tt>.
	 *
	 *
	 * @param tipo tipo que define as propriedades a serem mapeadas
	 * @param bean JavaBean cujas propriedades serão mapeadas
	 * @return mapa com os nomes e valores das propriedades do JavaBean
	 */
	public static Map<String, Object> getValoresPropriedades(Class<?> tipo, Object bean) {
		return getValoresPropriedades(tipo, bean, true);
	}

	/**
	 * Para as propriedades definidas neste tipo, mapeia os valores armazenados
	 * neste <i>bean</i>, checando a compatibilidade do <i>bean</i> com o tipo. Cada
	 * entrada no mapa retornado associa o nome da propriedade ao seu valor. Se
	 * <tt>tipoCompativel</tt> for <tt>true</tt>, o JavaBean deve ser compatível com
	 * o tipo que define as propriedades a serem extraídas, senão um erro de
	 * execução será gerado. O seguinte teste garante a execução segura do método:
	 * 
	 * <pre>
	 *   Map&lt;String, Object&gt; mpValores = null;
	 *   Bean2 bean2 = new Bean2(...);
	 *   if (Bean1.class.isAssignableFrom(Bean2.class)) {
	 *      mpValores = Componentes.getValoresPropriedades(Bean1.class, bean2, true);
	 *   }
	 * </pre>
	 * 
	 * Se o parâmetro <tt>tipoCompativel</tt> for <tt>false</tt>, nenhuma checagem
	 * será feita e, para cada propriedade definida no tipo,
	 * <tt>getValoresPropriedades</tt> vai procurar no <i>bean</i> um método
	 * <tt>get</tt> correspondente para tentar extrair o valor da propriedade,
	 * <b>baseando-se apenas no nome da mesma</b>. Caso não encontre um método
	 * <tt>get</tt>, será retornado o valor <tt>null</tt> para a propriedade.
	 * 
	 * 
	 * @param tipo           tipo que define as propriedades a serem mapeadas
	 * @param bean           JavaBean cujas propriedades serão mapeadas
	 * @param tipoCompativel define se o tipo passado deve ser compatível com o tipo
	 *                       do JavaBean
	 * @return mapa com os nomes e valores das propriedades do JavaBean
	 */
	public static Map<String, Object> getValoresPropriedades(Class<?> tipo, Object bean, boolean tipoCompativel) {
		Class<? extends Object> tipoBean = bean.getClass();
		if (tipoCompativel && !tipo.isAssignableFrom(tipoBean)) {
			throw new ErroExecucao("Bean não possui tipo compatível com '" + tipo + "': " + tipoBean);
		}
		Map<String, Object> mpValores = new HashMap<String, Object>();
		String nome = null;
		Object valor = null;
		try {
			PropertyDescriptor clInfoPropTipo[] = Introspector.getBeanInfo(tipo).getPropertyDescriptors();
			if (tipoCompativel) {
				for (PropertyDescriptor infoPropTipo : clInfoPropTipo) {
					if (infoPropTipo.getReadMethod() != null) {
						nome = infoPropTipo.getName();
						valor = infoPropTipo.getReadMethod().invoke(bean, new Object[] {});
						mpValores.put(nome, valor);
					}
				}
			} else {
				PropertyDescriptor clInfoPropBean[] = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
				Map<String, PropertyDescriptor> mpInfoPropBean = new HashMap<String, PropertyDescriptor>();
				for (PropertyDescriptor infoProp : clInfoPropBean) {
					mpInfoPropBean.put(infoProp.getName(), infoProp);
				}
				for (PropertyDescriptor infoPropTipo : clInfoPropTipo) {
					PropertyDescriptor infoPropBean = mpInfoPropBean.get(infoPropTipo.getName());
					if (infoPropBean != null && infoPropBean.getReadMethod() != null) {
						nome = infoPropBean.getName();
						valor = infoPropBean.getReadMethod().invoke(bean, new Object[] {});
						mpValores.put(nome, valor);
					}
				}
			}
		} catch (IntrospectionException ie) {
			throw new ErroExecucao("Erro obtendo metadados", ie);
		} catch (InvocationTargetException ite) {
			throw new ErroExecucao("Erro obtendo valor da propriedade '" + nome + "': " + tipoBean.getName(), ite.getTargetException());
		} catch (IllegalAccessException e) {
			throw new ErroExecucao("Erro obtendo valor da propriedade '" + nome + "': " + tipoBean.getName(), e);
		}
		return mpValores;
	}
}
