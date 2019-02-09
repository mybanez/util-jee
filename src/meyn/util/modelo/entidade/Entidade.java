package meyn.util.modelo.entidade;

import java.io.*;
import java.util.*;

import meyn.util.beans.*;

/**
 * Define os m�todos comuns a todas as entidades de transfer�ncia (<i>pattern
 * Transfer Object</i>). O acesso �s propriedades pode ser feito atrav�s dos
 * m�todos gen�ricos definidos na interface <tt>AcessoPropriedades</tt>. Esta
 * forma de manipular a entidade possui a vantagem de ser bastante flex�vel,
 * por�m abre m�o da checagem dos tipos das propriedades em tempo de compila��o.
 */
public interface Entidade extends Serializable, AcessoPropriedades {
	/**
	 * Retorna os nomes das propriedades da entidade.
	 *
	 * @return cole��o com os nomes das propriedades
	 */
	Collection<String> getNomesPropriedades();

	/**
	 * Retorna o tipo da interface de acesso �s propriedades da entidade.
	 *
	 * @return tipo da interface de acesso �s propriedades
	 */
	Class<?> getTipoAcessoPropriedades();
}
