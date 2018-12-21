package meyn.util.modelo.entidade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import meyn.util.beans.AcessoPropriedades;

/**
 * Suporte para implementações das entidades de transferência. Esta classe
 * assume que a interface de acesso padrão às propriedades é
 * {@link meyn.util.beans.AcessoPropriedades AcessoPropriedades}.
 *
 * @see FabricaEntidade
 */
@SuppressWarnings("serial")
public class EntidadeMapeada extends HashMap<String, Object> implements Entidade {

	/**
	 * Cria uma entidade com as propriedades definidas nesta coleção.
	 *
	 * @param clNomesProps nomes das propriedades
	 */
	public EntidadeMapeada(Collection<String> clNomesProps) {
		for (String nome : clNomesProps) {
			this.put(nome, null);
		}
		put("nomesPropriedades", new ArrayList<Object>(keySet()));
	}

	public EntidadeMapeada(Map<String, Object> mpProps) {
		putAll(mpProps);
		put("nomesPropriedades", new ArrayList<Object>(keySet()));
	}

	/**
	 * Retorna os nomes das propriedades da entidade.
	 */
	public final Collection<String> getNomesPropriedades() {
		return keySet();
	}

	/**
	 * Retorna {@link meyn.util.beans.AcessoPropriedades AcessoPropriedades} como o
	 * tipo da interface de acesso às propriedades da entidade.
	 *
	 * @return tipo {@link meyn.util.beans.AcessoPropriedades AcessoPropriedades}
	 */
	public Class<?> getTipoAcessoPropriedades() {
		return AcessoPropriedades.class;
	}

	/* INTERFACE AcessoPropriedades */

	protected final void validarPropriedade(String nome) {
		if (!containsKey(nome)) {
			throw new ErroPropriedadeEntidadeNaoDefinida(nome);
		}
	}

	public final Map<String, Object> get(Collection<String> clNomesProps) {
		Map<String, Object> res = new HashMap<String, Object>();
		for (String nome : clNomesProps) {
			res.put(nome, get(nome));
		}
		return res;
	}

	public final void set(Map<String, Object> mpProps) {
		Collection<Map.Entry<String, Object>> itens = mpProps.entrySet();
		for (Map.Entry<String, Object> item : itens) {
			set(item.getKey(), item.getValue());
		}
	}

	@Override
	public final Object get(String nome) {
		validarPropriedade(nome);
		return super.get(nome);
	}

	public final void set(String nome, Object valor) {
		validarPropriedade(nome);
		put(nome, valor);
	}
}
