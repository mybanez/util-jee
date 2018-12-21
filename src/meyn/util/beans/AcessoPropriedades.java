package meyn.util.beans;

import java.io.*;
import java.util.*;

/**
 * Define métodos genéricos para acesso às propriedades de um componente
 * JavaBean, tratado aqui como um simples repositório de propriedades. A chave
 * de acesso é sempre uma String contendo o nome da propriedade, que deve
 * começar com letra minúscula.
 */
public interface AcessoPropriedades extends Serializable {
	/**
	 * Retorna os valores destas propriedades.
	 * 
	 * @param clNomes nomes das propriedades selecionadas
	 *
	 * @return mapa com os valores das propriedades selecionadas
	 */
	Map<String, Object> get(Collection<String> clNomes);

	/**
	 * Define os valores destas propriedades.
	 * 
	 * @param mpVals mapa com os valores das propriedades selecionadas
	 */
	void set(Map<String, Object> mpValores);

	/**
	 * Retorna o valor desta propriedade.
	 *
	 * @param nome nome da propriedade
	 *
	 * @return valor da propriedade
	 */
	Object get(String nome);

	/**
	 * Define o valor desta propriedade.
	 *
	 * @param nome  nome da propriedade
	 * @param valor valor da propriedade
	 */
	void set(String nome, Object valor);
}
