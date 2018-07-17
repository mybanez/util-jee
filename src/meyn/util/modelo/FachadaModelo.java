package meyn.util.modelo;

import java.rmi.*;
import java.util.*;

import meyn.util.modelo.ot.*;

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
 * @see OT
 */
public interface FachadaModelo {
	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarTodos(meyn.util.modelo.ot.OT,String)
	 * consultarTodos(null, modelo)}.
	 */
	<TipoOT extends OT> Collection<TipoOT> consultarTodos(String modelo) throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarTodos(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT)
	 * consultarTodos(null, modelo, molde)}.
	 */
	<TipoOT extends OT> Collection<TipoOT> consultarTodos(String modelo, Object molde)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarTodos(meyn.util.modelo.ot.OT,String,Class)
	 * consultarTodos(null, modelo, molde)}.
	 */
	<TipoOT extends OT> Collection<TipoOT> consultarTodos(String modelo, Class<?> molde)
			throws RemoteException, ErroModelo;

	/**
	 * Para este usu�rio, consulta todos os itens deste modelo. Os ots retornados
	 * devem adotar o molde padr�o.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo a ser consultado
	 *
	 * @return lista de ots com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> Collection<TipoOT> consultarTodos(TipoUsuario usuario, String modelo)
			throws RemoteException, ErroModelo;

	/**
	 * Para este usu�rio, consulta todos os itens deste modelo. Os ots retornados
	 * devem adotar este molde.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo a ser consultado
	 * @param molde
	 *            molde para os ots a serem retornados
	 *
	 * @return lista de ots com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> Collection<TipoOT> consultarTodos(TipoUsuario usuario, String modelo,
			Object molde) throws RemoteException, ErroModelo;

	/**
	 * Para este usu�rio, consulta todos os itens deste modelo. Os ots retornados
	 * devem adotar este tipo como molde. As propriedades de chave prim�ria n�o
	 * estar�o definidas nos ots retornados.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo a ser consultado
	 * @param molde
	 *            molde para os ots a serem retornados
	 *
	 * @return lista de ots com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> Collection<TipoOT> consultarTodos(TipoUsuario usuario, String modelo,
			Class<?> molde) throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarPorChavePrimaria(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT)
	 * consultarPorChavePrimaria(null, modelo, chave)}.
	 */
	<TipoOT extends OT> TipoOT consultarPorChavePrimaria(String modelo, TipoOT chave)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarPorChavePrimaria(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT,meyn.util.modelo.ot.OT)
	 * consultarPorChavePrimaria(null, modelo, chave, molde)}.
	 */
	<TipoOT extends OT> TipoOT consultarPorChavePrimaria(String modelo, TipoOT chave, Object molde)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link FachadaModelo#consultarPorChavePrimaria(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT,Class)
	 * consultarPorChavePrimaria(null, modelo, chave, molde)}.
	 */
	<TipoOT extends OT> TipoOT consultarPorChavePrimaria(String modelo, TipoOT chave, Class<?> molde)
			throws RemoteException, ErroModelo;

	/**
	 * Consulta os dados de um item deste modelo a partir desta chave prim�ria. Os
	 * ots retornados devem adotar o molde padr�o.
	 *
	 * @param modelo
	 *            modelo a ser consultado
	 * @param chave
	 *            ot contendo a chave de consulta do item
	 *
	 * @return ot com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> TipoOT consultarPorChavePrimaria(TipoUsuario usuario, String modelo,
			TipoOT chave) throws RemoteException, ErroModelo;

	/**
	 * Consulta os dados de um item deste modelo a partir desta chave prim�ria. Os
	 * ots retornados devem adotar este molde.
	 *
	 * @param modelo
	 *            modelo a ser consultado
	 * @param chave
	 *            ot contendo a chave de consulta do item
	 * @param molde
	 *            molde para o ot a ser retornado
	 *
	 * @return ot com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> TipoOT consultarPorChavePrimaria(TipoUsuario usuario, String modelo,
			TipoOT chave, Object molde) throws RemoteException, ErroModelo;

	/**
	 * Consulta os dados de um item deste modelo a partir desta chave prim�ria. Os
	 * ots retornados devem adotar este tipo como molde.
	 *
	 * @param modelo
	 *            modelo a ser consultado
	 * @param chave
	 *            ot contendo a chave de consulta do item
	 * @param molde
	 *            molde para o ot a ser retornado
	 *
	 * @return ot com o resultado da consulta
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> TipoOT consultarPorChavePrimaria(TipoUsuario usuario, String modelo,
			TipoOT chave, Class<?> molde) throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link AcessoModelo#incluir(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT)
	 * incluir(null, modelo, ot)}.
	 */
	<TipoOT extends OT> TipoOT incluir(String modelo, TipoOT ot) throws RemoteException, ErroModelo;

	/**
	 * Para este usu�rio, inclui um item neste modelo a partir dos dados deste ot.
	 * Somente as propriedades obtidas atrav�s de uma chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para preencher os
	 * campos da tabela. Estas devem estar definidos tamb�m no ot retornado, que
	 * pode conter valores inseridos e valores default usados para inicializar
	 * campos da tabela.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item ser� inclu�do
	 * @param ot
	 *            ot contendo os dados do item
	 *
	 * @return ot contendo os dados do item inclu�do
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> TipoOT incluir(TipoUsuario usuario, String modelo, TipoOT ot)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link AcessoModelo#alterar(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT)
	 * alterar(null, modelo, ot)}.
	 */
	<TipoOT extends OT> TipoOT alterar(String modelo, TipoOT ot) throws RemoteException, ErroModelo;

	/**
	 * Para este usu�rio, altera um item neste modelo a partir dos dados deste ot.
	 * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
	 * atrav�s de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 * Somente as propriedades obtidas pela chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para alterar os
	 * campos da tabela. Estas propriedades devem estar definidas tamb�m no ot
	 * retornado.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item ser� alterado
	 * @param ot
	 *            ot contendo os dados do item
	 *
	 * @return ot contendo os dados do item alterado
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> TipoOT alterar(TipoUsuario usuario, String modelo, TipoOT ot)
			throws RemoteException, ErroModelo;

	/**
	 * Equivalente a
	 * {@link AcessoModelo#excluir(meyn.util.modelo.ot.OT,String,meyn.util.modelo.ot.OT)
	 * excluir(null, modelo, ot)}.
	 */
	<TipoOT extends OT> void excluir(String modelo, TipoOT ot) throws RemoteException, ErroModelo;

	/**
	 * Para este usu�rio, exclui um item neste modelo a partir dos dados deste ot.
	 * Os nomes das propriedades que comp�em a chave prim�ria devem ser obtidos
	 * atrav�s de uma chamada a <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param modelo
	 *            modelo onde o item ser� exclu�do
	 * @param ot
	 *            ot contendo os dados do item
	 *
	 * @throws RemoteException
	 *             se ocorrer um erro na invoca��o remota do m�todo
	 * @throws ErroModelo
	 *             se ocorrer um erro durante o processamento da opera��o
	 */
	<TipoUsuario extends OT, TipoOT extends OT> void excluir(TipoUsuario usuario, String modelo, TipoOT ot)
			throws RemoteException, ErroModelo;
}
