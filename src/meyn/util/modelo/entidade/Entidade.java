package meyn.util.modelo.entidade;

import java.io.*;
import java.util.*;

import meyn.util.beans.*;

/**
 * Define os métodos comuns a todas as entidades de transferência (<i>pattern
 * Transfer Object</i>). O acesso às propriedades pode ser feito através dos
 * métodos genéricos definidos na interface <tt>AcessoPropriedades</tt>. Esta
 * forma de manipular a entidade possui a vantagem de ser bastante flexível,
 * porém abre mão da checagem dos tipos das propriedades em tempo de compilação.
 */
public interface Entidade extends Serializable, AcessoPropriedades {
	/**
	 * Retorna os nomes das propriedades da entidade.
	 *
	 * @return coleção com os nomes das propriedades
	 */
	Collection<String> getNomesPropriedades();

	/**
	 * Retorna o tipo da interface de acesso às propriedades da entidade.
	 *
	 * @return tipo da interface de acesso às propriedades
	 */
	Class<?> getTipoAcessoPropriedades();
}
