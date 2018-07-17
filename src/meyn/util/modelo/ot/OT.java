package meyn.util.modelo.ot;

import java.io.*;
import java.util.*;

import meyn.util.beans.*;

/**
 * Define os métodos comuns a todos os objetos de transferência (<i>pattern
 * Transfer Object</i>). O acesso às propriedades pode ser feito através 
 * dos métodos genéricos definidos na interface <tt>AcessoPropriedades</tt>. 
 * Esta forma de manipular o ot possui a vantagem de ser bastante flexível, porém 
 * abre mão da checagem dos tipos das propriedades em tempo de compilação.
 */
public interface OT extends Serializable, AcessoPropriedades {
    /**
     * Retorna os nomes das propriedades do ot.
     *
     * @return coleção com os nomes das propriedades
     */
    Collection<String> getNomesPropriedades();    

    /**
     * Retorna o tipo da interface de acesso às propriedades
     * do ot.
     *
     * @return tipo da interface de acesso às propriedades
     */
    Class<?> getTipoAcessoPropriedades();    
}
