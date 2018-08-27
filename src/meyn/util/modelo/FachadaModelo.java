package meyn.util.modelo;

import java.rmi.*;
import java.util.*;

import meyn.util.modelo.entidade.*;

/**
 * Define os m�todos gen�ricos de acesso aos modelos que uma fachada deve
 * implementar. Todos os m�todos desta interface declaram
 * <tt>java.rmi.RemoteException</tt> na cl�usula <tt>throws</tt>, de modo a
 * suportar implementa��es remotas. Os m�todos que recebem os dados do usu�rio
 * logado como par�metro devem ser utilizados quando estes dados forem
 * necess�rios para a execu��o das opera��es. Um exemplo disso � quando <b>os
 * usu�rios da aplica��o s�o usu�rios de banco</b>, e portando os dados de
 * login/senha t�m de ser repassados para que as conex�es possam ser abertas.
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
	 * Para este usu�rio, consulta todos os itens deste modelo. As entidades retornadas
	 * devem adotar o molde padr�o.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo a ser consultado
	 *
	 * @return lista de entidades com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
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
	 * Consulta os dados de um item deste modelo a partir desta chave prim�ria. Os
	 * ents retornados devem adotar o molde padr�o.
	 *
	 * @param modelo
	 *            modelo a ser consultado
	 * @param chave
	 *            entidade contendo a chave de consulta do item
	 *
	 * @return ent com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
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
	 * Para este usu�rio, inclui um item neste modelo a partir dos dados desta entidade.
	 * Somente as propriedades obtidas atrav�s de uma chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para preencher os
	 * campos da tabela. Estas devem estar definidos tamb�m na entidade retornado, que
	 * pode conter valores inseridos e valores default usados para inicializar
	 * campos da tabela.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item ser� inclu�do
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @return entidade contendo os dados do item inclu�do
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
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
	 * Para este usu�rio, altera um item neste modelo a partir dos dados desta entidade.
	 * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
	 * atrav�s de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 * Somente as propriedades obtidas pela chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para alterar os
	 * campos da tabela. Estas propriedades devem estar definidas tamb�m na entidade
	 * retornado.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item ser� alterado
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @return entidade contendo os dados do item alterado
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
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
	 * Para este usu�rio, exclui um item neste modelo a partir dos dados desta entidade.
	 * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
	 * atrav�s de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item ser� exclu�do
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends Entidade, TipoEnt extends Entidade> void excluir(TipoUsuario usuario, String modelo, TipoEnt ent)
			throws RemoteException, ErroModelo;
}
