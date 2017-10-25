package fsiscad.modelo;

import fsiscad.modelo.ot.*;
import java.rmi.*;
import java.util.*;

/**
 * Define os métodos genéricos adicionais de acesso aos modelos que um delegador 
 * ou uma fachada deve implementar quando os modelos são implementados como 
 * cadastros do tipo <i>entity bean</i>. 
 *
 * @see fsiscad.modelo.cadastro.CadastroEntityLocal
 */
public interface AcessoModeloEntity extends AcessoModelo {
    /**
     * Equivalente a {@link
     * AcessoModeloEntity#consultarTodos(fsiscad.modelo.ot.OT,String,Map)
     * consultarTodos(null, modelo, mpMoldes)}.
     */
    Collection<OT> consultarTodos(String modelo, Map<String, OT> mpMoldes)
    throws RemoteException, ErroModelo;

    /**
     * Para este usuário, consulta todos os itens deste modelo. A malha de ots 
     * retornada deve seguir este mapa de moldes.
     *
     * @param usuario usuario logado
     * @param modelo modelo a ser consultado
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return lista de ots com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
     */
    Collection<OT> consultarTodos(OT usuario, String modelo, Map<String, OT> mpMoldes)
    throws RemoteException, ErroModelo;
    
    /**
     * Equivalente a {@link
     * AcessoModeloEntity#consultarPorChavePrimaria(fsiscad.modelo.ot.OT,String,fsiscad.modelo.ot.OT,Map)
     * consultarPorChavePrimaria(null, modelo, chave, mpMoldes)}.
     */
    OT consultarPorChavePrimaria(String modelo, OT chave, Map<String, OT> mpMoldes)
    throws RemoteException, ErroModelo;
    
    /**
     * Consulta os dados de um item deste modelo a partir desta chave primária.
     * Os nomes das propriedades que compõem a chave primária devem ser obtidos
     * através de uma chamada a <tt>chave.getNomesPropriedadesChave(modelo)</tt>.
     * A malha de ots retornada deve seguir este mapa de moldes.
     *
     * @param modelo modelo a ser consultado
     * @param chave ot contendo a chave de consulta do item
     * @param mpMoldes moldes para os ots a serem retornados
     *
     * @return ot com o resultado da consulta
     *
     * @throws RemoteException se ocorrer um erro na invocação remota do método
     * @throws ErroModelo se ocorrer um erro durante o processamento da operação
     */
    OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Map<String, OT> mpMoldes)
    throws RemoteException, ErroModelo;
}
