package fsiscad.modelo.cadastro;

import fsiscad.modelo.ot.*;
import java.util.*;

/**
 * Define os m�todos b�sicos que um cadastro deve implementar. Os dados do
 * usu�rio logado s� precisam ser passados como par�metro quando forem
 * necess�rios para a execu��o das opera��es de cadastro. Um exemplo disso �
 * quando <b>os usu�rios da aplica��o s�o usu�rios de banco</b>, e portando os
 * dados de login/senha t�m de ser repassados para que as conex�es JDBC possam
 * ser abertas. Os m�todos de consulta trabalham com a no��o de <b>molde</b> para 
 * os objetos de transfer�ncia retornados. O molde nada mais � do que uma inst�ncia 
 * de ot que servir� como um <i>template</i> para a cria��o dos ots que conter�o
 * o resultado da consulta, os quais exibir�o o mesmo tipo e as mesmas propriedades 
 * definidas no molde, inclusive com a mesma defini��o de chave prim�ria.
 *
 * @see OT
 */
public interface Cadastro {
    /**
     * Retorna o nome l�gico do modelo ao qual o cadastro est� associado.
     *
     * @return modelo ao qual o cadastro est� associado
     */
    String getModelo();
    
    /**
     * Define o nome l�gico do modelo ao qual o cadastro est� associado, que deve
     * ser o mesmo que consta no mapeamento registrado na classe {@link
     * MapaCadastros MapaCadastros}. Este m�todo � necess�rio para a
     * implementa��o da classe {@link FabricaCadastro FabricaCadastro}
     *
     * @param modelo modelo ao qual o cadastro est� associado
     */
    void setModelo(String modelo);
    
    /**
     * Para este usu�rio, consulta todos os itens deste modelo. Os ots retornados 
     * devem ser criados a partir este molde.
     * 
     * @param usuario usuario logado
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de objetos de tranfer�ncia com o resultado da
     *         consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    Collection<OT> consultarTodos(OT usuario, OT molde)
    throws ErroCadastro;
    
    /**
     * Para este usu�rio, consulta os dados de um item a partir desta chave
     * prim�ria. Os nomes das propriedades que comp�em a chave prim�ria devem ser
     * obtidos atrav�s de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>. 
     * Os ots retornados devem ser criados a partir este molde.
     *
     * @param usuario usuario logado
     * @param chave ot contendo a chave de consulta do item
     * @param molde molde para os ots a serem retornados
     *
     * @return objeto de tranfer�ncia com o resultado da consulta
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    OT consultarPorChavePrimaria(OT usuario, OT chave, OT molde)
    throws ErroCadastro;
    
   /**
     * Para este usu�rio, inclui um item a partir dos dados deste objeto de
     * tranfer�ncia. Somente as propriedades obtidas atrav�s de uma chamada a
     * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para preencher
     * os campos da tabela. Estas devem estar definidas tamb�m no ot retornado,
     * que pode conter valores inseridos e valores default usados para
     * inicializar campos da tabela.
     *
     * @param usuario usuario logado
     * @param ot ot contendo os dados do item
     *
     * @return objeto de tranfer�ncia contendo os dados do item inclu�do
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    OT incluir(OT usuario, OT ot) 
    throws ErroCadastro;
    
    /**
     * Para este usu�rio, altera um item a partir dos dados deste objeto de
     * tranfer�ncia. Os nomes das propriedades que comp�em a chave prim�ria devem
     * ser obtidos atrav�s de uma chamada a
     * <tt>ot.getNomesPropriedadesChave(modelo)</tt>. Somente as propriedades
     * obtidas pela chamada a <tt>ot.getNomesPropriedades(modelo)</tt> devem ser
     * usadas para alterar os campos da tabela. Estas propriedades devem estar
     * definidas tamb�m no ot retornado.
     *
     * @param usuario usuario logado
     * @param ot ot contendo os dados do item
     *
     * @return objeto de tranfer�ncia contendo os dados do item alterado
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    OT alterar(OT usuario, OT ot) 
    throws ErroCadastro;
    
    /**
     * Para este usu�rio, exclui um item a partir dos dados deste objeto de
     * tranfer�ncia. Os nomes das propriedades que comp�em a chave prim�ria devem
     * ser obtidos atrav�s de uma chamada a
     * <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
     *
     * @param usuario usuario logado
     * @param ot ot contendo os dados do item
     *
     * @throws ErroCadastro se algum erro ocorreu durante a opera��o
     */
    void excluir(OT usuario, OT ot) 
    throws ErroCadastro;
}
