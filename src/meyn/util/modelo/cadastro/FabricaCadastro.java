package meyn.util.modelo.cadastro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

import meyn.util.Erro;
import meyn.util.ErroExecucao;
import meyn.util.modelo.ChavesModelo;
import meyn.util.modelo.FabricaObjetoModelo;
import meyn.util.modelo.Modelo;
import meyn.util.modelo.entidade.Entidade;

/**
 * Suporte para a implementação de fábricas de cadastro. Esta classe é usada por
 * {@link meyn.util.modelo.FachadaModelo FachadaModelo} para obter acesso aos
 * cadastros e, por sua vez, usa {@link MapaCadastros MapaCadastros} para
 * recuperar os mapeamentos entre os nomes lógicos dos modelos e os componentes
 * cadastro.
 */
public class FabricaCadastro extends FabricaObjetoModelo {

	private static class ExtendedClassLoader extends ClassLoader {
		ExtendedClassLoader(ClassLoader cl) {
			super(cl);
		}

		protected Package[] getPackages() {
			return super.getPackages();
		}
	}

	@SuppressWarnings("serial")
	public static class MapaCadastros extends HashMap<String, String> {

		public MapaCadastros() {
			Package[] pacotes = new ExtendedClassLoader(getClass().getClassLoader()).getPackages();
			Set<Class<?>> tiposCadastro = new HashSet<Class<?>>();
			for (Package pct : pacotes) {
				Reflections reflections = new Reflections(pct.getName());
				tiposCadastro.addAll(reflections.getTypesAnnotatedWith(Modelo.class));
			}
			for (Class<?> tipo : tiposCadastro) {
				put(tipo.getAnnotationsByType(Modelo.class)[0].value(), tipo.getName());
			}
		}
	}

	private static MapaCadastros getMapaCadastros() throws ErroCadastro {
		try {
			return (MapaCadastros) FabricaObjetoModelo.getInstanciaEmCache(ChavesModelo.MAPA_CADASTROS, MapaCadastros.class.getName());
		} catch (Exception e) {
			throw new ErroCadastro("Erro carregando mapa dos cadastros", e);
		}
	}

	/**
	 * Retorna o cadastro associado a este nome lógico de modelo.
	 *
	 * @param modelo nome lógico do modelo
	 *
	 * @return cadastro associado a este modelo
	 *
	 * @throws ErroCadastro se ocorrer um erro na obtenção do cadastro
	 */
	@SuppressWarnings("unchecked")
	public static <TipoUsuario extends Entidade, TipoEnt extends Entidade> Cadastro<TipoUsuario, TipoEnt> getCadastro(String modelo)
	        throws ErroCadastro {
		String tipo = getMapaCadastros().get(modelo);
		if (tipo == null) {
			throw new ErroExecucao("Não existe cadastro definido para o modelo '" + modelo + "'");
		}
		try {
			return (Cadastro<TipoUsuario, TipoEnt>) getInstanciaEmCache(modelo, tipo);
		} catch (Erro e) {
			throw new ErroCadastro("Erro obtendo cadastro para o modelo '" + modelo + "'", e);
		}
	}
}
