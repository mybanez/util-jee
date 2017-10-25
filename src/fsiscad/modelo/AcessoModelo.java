package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.rmi.*;
import java.util.*;

/**
 * Define os métodos genéricos de acesso aos modelos que um delegador ou uma 
 * fachada deve implementar. Todos os métodos desta interface declaram
 * <tt>java.rmi.RemoteException</tt> na cláusula <tt>throws</tt>, de modo a
 * suportar implementações remotas. Os métodos que recebem os dados do
 * usuário logado como parâmetro devem ser utilizados quando estes dados
 * forem necessários para a execução das operações. Um exemplo disso é quando
 * <b>os usuários da aplicação são usuários de banco</b>, e portando os dados
 * de login/senha têm de ser repassados para que as conexões JDBC possam ser
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
     * Para este usuário, consulta todos os itens deste modelo. Os ots 
     * retornados devem adotar este tipo como molde. As propriedades de chave 
     * primária não estarão definidas nos ots retornados.
     *
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de ots com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
     */
    Collection<OT> consultarTodos(OT usuario, String modelo, Class molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Para este usuário, consulta todos os itens deste modelo. Os ots 
     * retornados devem adotar este molde. 
     *
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param molde molde para os ots a serem retornados
     *
     * @return lista de ots com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
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
     * Consulta os dados de um item deste modelo a partir desta chave primária.
     * Os nomes das propriedades que compõem a chave primária devem ser obtidos
     * através de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * Os ots retornados devem adotar esta chave como molde.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
     */
    OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave)
    throws RemoteException, ErroModelo;
    
    /**
     * Consulta os dados de um item deste modelo a partir desta chave primária.
     * Os nomes das propriedades que compõem a chave primária devem ser obtidos
     * através de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * Os ots retornados devem adotar este tipo como molde. As propriedades de chave 
     * primária não estarão definidas no ot retornado.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     * @param molde molde para o ot a ser retornado
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
     */
    OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Class molde)
    throws RemoteException, ErroModelo;
    
    /**
     * Consulta os dados de um item deste modelo a partir desta chave primária.
     * Os nomes das propriedades que compõem a chave primária devem ser obtidos
     * através de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * Os ots retornados devem adotar este molde.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     * @param molde molde para o ot a ser retornado
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
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
     * Para este usuário, inclui um item neste modelo a partir dos dados deste
     * ot. Somente as propriedades obtidas através de uma
     * chamada a <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para
     * preencher os campos da tabela. Estas devem estar definidos também no ot
     * retornado, que pode conter valores inseridos e valores default usados
     * para inicializar campos da tabela.
     *
     * @param usuario usuario logado
     * @param modelo modelo onde o item será incluído
     * @param ot ot contendo os dados do item
     *
     * @return ot contendo os dados do item incluído
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
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
     * Para este usuário, altera um item neste modelo a partir dos dados deste
     * ot. Os nomes das propriedades que compõem a chave primária devem ser
     * obtidos através de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
     * Somente as propriedades obtidas pela chamada a <tt>ot.getNomesPropriedades(modelo)</tt>
     * devem ser usadas para alterar os campos da tabela. Estas propriedades devem
     * estar definidas também no ot retornado.
     *
     * @param usuario usuario logado
     * @param modelo modelo onde o item será alterado
     * @param ot ot contendo os dados do item
     *
     * @return ot contendo os dados do item alterado
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
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
     * Para este usuário, exclui um item neste modelo a partir dos dados deste
     * ot. Os nomes das propriedades que compõem a chave primária devem ser obtidos
     * através de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
     *
     * @param usuario usuario logado
     * @param modelo modelo onde o item será excluído
     * @param ot ot contendo os dados do item
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
     */
    void excluir(OT usuario, String modelo, OT ot)
    throws RemoteException, ErroModelo;
}
