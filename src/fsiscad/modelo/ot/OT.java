package fsiscad.modelo.ot;

import fsiscad.beans.*;
import java.io.*;
import java.util.*;

/**
 * Define os m�todos comuns a todos os objetos de transfer�ncia (<i>pattern
 * Transfer Object</i>). O acesso �s propriedades pode ser feito atrav�s 
 * dos m�todos gen�ricos definidos na interface <tt>AcessoPropriedades</tt>. 
 * Esta forma de manipular o ot possui a vantagem de ser bastante flex�vel, por�m 
 * abre m�o da checagem dos tipos das propriedades em tempo de compila��o.
 */
public interface OT extends Serializable, AcessoPropriedades {
    /**
     * Retorna os nomes das propriedades do ot que comp�em a chave prim�ria.
     *
     * @return cole��o com os nomes das propriedades da chave prim�ria
     */
    Collection<String> getNomesPropriedadesChave();
    
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
    Class getTipoAcessoPropriedades();    
    
    /**
     * Retorna o valor do
     * <a href="../cadastro/CadastroEntityLocalImpl.html#idVersao">id de vers�o</a>
     * dos dados.
     *
     * @return id de vers�o dos dados
     */
    long getIdVersao();

  /**
     * Define o valor do 
     * <a href="../cadastro/CadastroEntityLocalImpl.html#idVersao"> id de vers�o</a> 
     * dos dados.
     *
     * @param idVersao id de vers�o dos dados
     */
    void setIdVersao(long idVersao);
}
