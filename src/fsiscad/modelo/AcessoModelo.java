package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.rmi.*;
import java.util.*;

/**
 * Define os m�todos gen�ricos de acesso aos modelos que um delegador ou uma 
 * fachada deve implementar. Todos os m�todos desta interface declaram
 * <tt>java.rmi.RemoteException</tt> na cl�usula <tt>throws</tt>, de modo a
 * suportar implementa��es remotas. Os m�todos que recebem os dados do
 * usu�rio logado como par�metro devem ser utilizados quando estes dados
 * forem necess�rios para a execu��o das opera��es. Um exemplo disso � quando
 * <b>os usu�rios da aplica��o s�o usu�rios de banco</b>, e portando os dados
 * de login/senha t�m de ser repassados para que as conex�es JDBC possam ser
 * abertas.
 *
 * @see OT
 */
public interface AcessoModelo {
    /**
     * Equivalente a {@link
     * AcessoModelo#consultarTodos(fsiscad.modelo.ot.OT,String,Class)
     * consultarTodos(null, modelo, molde)}.
     */
    Collection<OT> consultarTodos(String modelo, Class molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#consultarTodos(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT)
     * consultarTodos(null, modelo, molde)}.
     */
    Collection<OT> consultarTodos(String modelo, OT molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Para este usu�rio, consulta todos os itens deste modelo. Os ots 
     * retornados devem adotar este tipo como molde. As propriedades de chave 
     * prim�ria n�o estar�o definidas nos ots retornados.
     *
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de ots com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    Collection<OT> consultarTodos(OT usuario, String modelo, Class molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Para este usu�rio, consulta todos os itens deste modelo. Os ots 
     * retornados devem adotar este molde. 
     *
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de ots com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    Collection<OT> consultarTodos(OT usuario, String modelo, OT molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT)
     * consultarPorChavePrimaria(null, modelo, chave)}.
     */
    OT consultarPorChavePrimaria(String modelo, OT chave)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT,Class)
     * consultarPorChavePrimaria(null, modelo, chave, molde)}.
     */
    OT consultarPorChavePrimaria(String modelo, OT chave, Class molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT,fsiscad.modelo.ot.OT)
     * consultarPorChavePrimaria(null, modelo, chave, molde)}.
     */
    OT consultarPorChavePrimaria(String modelo, OT chave, OT molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Consulta os dados de um item deste modelo a partir desta chave prim�ria.
     * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
     * atrav�s de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * Os ots retornados devem adotar esta chave como molde.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave)
    throws RemoteException, ErroModelo;
    
    /**
     * Consulta os dados de um item deste modelo a partir desta chave prim�ria.
     * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
     * atrav�s de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * Os ots retornados devem adotar este tipo como molde. As propriedades de chave 
     * prim�ria n�o estar�o definidas no ot retornado.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     * @param molde molde para o ot a ser retornado
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Class molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Consulta os dados de um item deste modelo a partir desta chave prim�ria.
     * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
     * atrav�s de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * Os ots retornados devem adotar este molde.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     * @param molde molde para o ot a ser retornado
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, OT molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#incluir(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT)
     * incluir(null, modelo, ot)}.
     */
    OT incluir(String modelo, OT ot)
    throws RemoteException, ErroModelo;
    
    /**
     * Para este usu�rio, inclui um item neste modelo a partir dos dados deste
     * ot. Somente as propriedades obtidas atrav�s de uma
     * chamada a <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para
     * preencher os campos da tabela. Estas devem estar definidos tamb�m no ot
     * retornado, que pode conter valores inseridos e valores default usados
     * para inicializar campos da tabela.
     *
     * @param usuario usuario logado
     * @param modelo modelo onde o item ser� inclu�do
     * @param ot ot contendo os dados do item
     *
     * @return ot contendo os dados do item inclu�do
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    OT incluir(OT usuario, String modelo, OT ot)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#alterar(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT)
     * alterar(null, modelo, ot)}.
     */
    OT alterar(String modelo, OT ot)
    throws RemoteException, ErroModelo;
    
    /**
     * Para este usu�rio, altera um item neste modelo a partir dos dados deste
     * ot. Os nomes das propriedades que comp�em a chave prim�ria devem ser
     * obtidos atrav�s de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
     * Somente as propriedades obtidas pela chamada a <tt>ot.getNomesPropriedades(modelo)</tt>
     * devem ser usadas para alterar os campos da tabela. Estas propriedades devem
     * estar definidas tamb�m no ot retornado.
     *
     * @param usuario usuario logado
     * @param modelo modelo onde o item ser� alterado
     * @param ot ot contendo os dados do item
     *
     * @return ot contendo os dados do item alterado
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    OT alterar(OT usuario, String modelo, OT ot)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModelo#excluir(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT)
     * excluir(null, modelo, ot)}.
     */
    void excluir(String modelo, OT ot)
    throws RemoteException, ErroModelo;
    
    /**
     * Para este usu�rio, exclui um item neste modelo a partir dos dados deste
     * ot. Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
     * atrav�s de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
     *
     * @param usuario usuario logado
     * @param modelo modelo onde o item ser� exclu�do
     * @param ot ot contendo os dados do item
     *
     * @throws RemoteException se ocorrer um erro na invoca��o remota do m�todo
     * @throws ErroModelo se ocorrer um erro durante o processamento da opera��o
     */
    void excluir(OT usuario, String modelo, OT ot)
    throws RemoteException, ErroModelo;
}
