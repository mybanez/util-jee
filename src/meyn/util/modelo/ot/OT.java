package meyn.util.modelo.ot;

import java.io.*;
import java.util.*;

import meyn.util.beans.*;

/**
 * Define os m�todos comuns a todos os objetos de transfer�ncia (<i>pattern
 * Transfer Object</i>). O acesso �s propriedades pode ser feito atrav�s 
 * dos m�todos gen�ricos definidos na interface <tt>AcessoPropriedades</tt>. 
 * Esta forma de manipular o ot possui a vantagem de ser bastante flex�vel, por�m 
 * abre m�o da checagem dos tipos das propriedades em tempo de compila��o.
 */
public interface OT extends Serializable, AcessoPropriedades {
    /**
     * Retorna os nomes das propriedades do ot.
     *
     * @return cole��o com os nomes das propriedades
     */
    Collection<String> getNomesPropriedades();    

    /**
     * Retorna o tipo da interface de acesso �s propriedades
     * do ot.
     *
     * @return tipo da interface de acesso �s propriedades
     */
    Class<?> getTipoAcessoPropriedades();    
}
