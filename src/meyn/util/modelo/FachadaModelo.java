package meyn.util.modelo;

import java.rmi.*;
import java.util.*;

import meyn.util.modelo.entidade.*;

/**
 * Define os métodos genéricos de acesso aos modelos que uma fachada deve
 * implementar. Todos os métodos desta interface declaram
 * <tt>java.rmi.RemoteException</tt> na cláusula <tt>throws</tt>, de modo a
 * suportar implementações remotas. Os métodos que recebem os dados do usuário
 * logado como parâmetro devem ser utilizados quando estes dados forem
 * necessários para a execução das operações. Um exemplo disso é quando <b>os
 * usuários da aplicação são usuários de banco</b>, e portando os dados de
 * login/senha têm de ser repassados para que as conexões possam ser abertas.
 *
 * @see Entidade
 */
public interface FachadaModelo {
	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarTodos(meyn.util.modelo.entidade.Entidade,String)
	 * consultarTodos(null, modelo)}.
	 */
	<TipoEnt extends Entidade> Collection<TipoEnt> consultarTodos(String modelo) throws RemoteException, ErroModelo;

	/**
	 * Para este usuário, consulta todos os itens deste modelo. As entidades retornadas
	 * devem adotar o molde padrão.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo a ser consultado
	 *
	 * @return lista de entidades com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invocação remota do método
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da operação
	 */
	<TipoUsuario extends Entidade, TipoEnt extends Entidade> Collection<TipoEnt> consultarTodos(TipoUsuario usuario, String modelo)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarPorChavePrimaria(meyn.util.modelo.entidade.Entidade,String,meyn.util.modelo.entidade.Entidade)
	 * consultarPorChavePrimaria(null, modelo, chave)}.
	 */
	<TipoEnt extends Entidade> TipoEnt consultarPorChavePrimaria(String modelo, TipoEnt chave)
			throws RemoteException, ErroModelo;

	/**
	 * Consulta os dados de um item deste modelo a partir desta chave primária. Os
	 * ents retornados devem adotar o molde padrão.
	 *
	 * @param modelo
	 *            modelo a ser consultado
	 * @param chave
	 *            entidade contendo a chave de consulta do item
	 *
	 * @return ent com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invocação remota do método
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da operação
	 */
	<TipoUsuario extends Entidade, TipoEnt extends Entidade> TipoEnt consultarPorChavePrimaria(TipoUsuario usuario, String modelo,
			TipoEnt chave) throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link AcessoModelo#incluir(meyn.util.modelo.entidade.Entidade,String,meyn.util.modelo.entidade.Entidade)
	 * incluir(null, modelo, ent)}.
	 */
	<TipoEnt extends Entidade> TipoEnt incluir(String modelo, TipoEnt ent) throws RemoteException, ErroModelo;

	/**
	 * Para este usuário, inclui um item neste modelo a partir dos dados desta entidade.
	 * Somente as propriedades obtidas através de uma chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para preencher os
	 * campos da tabela. Estas devem estar definidos também na entidade retornado, que
	 * pode conter valores inseridos e valores default usados para inicializar
	 * campos da tabela.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item será incluído
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @return entidade contendo os dados do item incluído
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invocação remota do método
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da operação
	 */
	<TipoUsuario extends Entidade, TipoEnt extends Entidade> TipoEnt incluir(TipoUsuario usuario, String modelo, TipoEnt ent)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link AcessoModelo#alterar(meyn.util.modelo.entidade.Entidade,String,meyn.util.modelo.entidade.Entidade)
	 * alterar(null, modelo, ent)}.
	 */
	<TipoEnt extends Entidade> TipoEnt alterar(String modelo, TipoEnt ent) throws RemoteException, ErroModelo;

	/**
	 * Para este usuário, altera um item neste modelo a partir dos dados desta entidade.
	 * Os nomes das propriedades que compõem a chave primária devem ser obtidos
	 * através de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 * Somente as propriedades obtidas pela chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para alterar os
	 * campos da tabela. Estas propriedades devem estar definidas também na entidade
	 * retornado.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item será alterado
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @return entidade contendo os dados do item alterado
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invocação remota do método
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da operação
	 */
	<TipoUsuario extends Entidade, TipoEnt extends Entidade> TipoEnt alterar(TipoUsuario usuario, String modelo, TipoEnt ent)
			throws RemoteException, ErroModelo;

	void excluirTodos(String modelo) throws ErroModelo;

	/**
	 * Equivalente a
	 * {@link AcessoModelo#excluir(meyn.util.modelo.entidade.Entidade,String,meyn.util.modelo.entidade.Entidade)
	 * excluir(null, modelo, ent)}.
	 */
	<TipoEnt extends Entidade> void excluir(String modelo, TipoEnt ent) throws RemoteException, ErroModelo;

	<TipoUsuario extends Entidade> void excluirTodos(TipoUsuario usuario, String modelo) throws ErroModelo;
	
	/**
	 * Para este usuário, exclui um item neste modelo a partir dos dados desta entidade.
	 * Os nomes das propriedades que compõem a chave primária devem ser obtidos
	 * através de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item será excluído
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invocação remota do método
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da operação
	 */
	<TipoUsuario extends Entidade, TipoEnt extends Entidade> void excluir(TipoUsuario usuario, String modelo, TipoEnt ent)
			throws RemoteException, ErroModelo;
}
