package fsiscad.modelo.ot;

import fsiscad.beans.*;
import java.io.*;
import java.util.*;

/**
 * Define os métodos comuns a todos os objetos de transferência (<i>pattern
 * Transfer Object</i>). O acesso às propriedades pode ser feito através 
 * dos métodos genéricos definidos na interface <tt>AcessoPropriedades</tt>. 
 * Esta forma de manipular o ot possui a vantagem de ser bastante flexível, porém 
 * abre mão da checagem dos tipos das propriedades em tempo de compilação.
 */
public interface OT extends Serializable, AcessoPropriedades {
    /**
     * Retorna os nomes das propriedades do ot que compõem a chave primária.
     *
     * @return coleção com os nomes das propriedades da chave primária
     */
    Collection<String> getNomesPropriedadesChave();
    
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
    Class getTipoAcessoPropriedades();    
    
    /**
     * Retorna o valor do
     * <a href="../cadastro/CadastroEntityLocalImpl.html#idVersao">id de versão</a>
     * dos dados.
     *
     * @return id de versão dos dados
     */
    long getIdVersao();

  /**
     * Define o valor do 
     * <a href="../cadastro/CadastroEntityLocalImpl.html#idVersao"> id de versão</a> 
     * dos dados.
     *
     * @param idVersao id de versão dos dados
     */
    void setIdVersao(long idVersao);
}
