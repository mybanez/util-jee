package meyn.util.modelo.cadastro;

import java.util.*;

import meyn.util.modelo.ot.*;

/**
 * Define os métodos básicos que um cadastro deve implementar. Os dados do
 * usuário logado só precisam ser passados como parâmetro quando forem
 * necessários para a execução das operações de cadastro. Um exemplo disso é
 * quando <b>os usuários da aplicação são usuários de banco</b>, e portando os
 * dados de login/senha têm de ser repassados para que as conexões JDBC possam
 * ser abertas. Os métodos de consulta trabalham com a noção de <b>molde</b>
 * para os objetos de transferência retornados. O molde nada mais é do que uma
 * instância de ot que servirá como um <i>template</i> para a criação dos ots
 * que conterão o resultado da consulta, os quais exibirão o mesmo tipo e as
 * mesmas propriedades definidas no molde, inclusive com a mesma definição de
 * chave primária.
 *
 * @see OT
 */
public interface Cadastro<TipoUsuario extends OT, TipoOT extends OT> {
	/**
	 * Retorna o nome lógico do modelo ao qual o cadastro está associado.
	 *
	 * @return modelo ao qual o cadastro está associado
	 */
	String getModelo();

	/**
	 * Define o nome lógico do modelo ao qual o cadastro está associado, que deve
	 * ser o mesmo que consta no mapeamento registrado na classe
	 * {@link MapaCadastros MapaCadastros}. Este método é necessário para a
	 * implementação da classe {@link FabricaCadastro FabricaCadastro}
	 *
	 * @param modelo
	 *            modelo ao qual o cadastro está associado
	 */
	void setModelo(String modelo);

	/*
	 * 
	 */
	Collection<TipoOT> consultarTodos(TipoUsuario usuario) throws ErroCadastro;

	/*
	 * 
	 */
	Collection<TipoOT> consultarTodos(TipoUsuario usuario, Class<?> moldeOT) throws ErroCadastro;

	/**
	 * Para este usuário, consulta todos os itens deste modelo. Os ots retornados
	 * devem ser criados a partir deste molde.
	 * 
	 * @param usuario
	 *            usuario logado
	 * @param clMoldeOT
	 *            molde para os ots a serem retornados
	 *
	 * @return lista de objetos de tranferência com o resultado da consulta
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	Collection<TipoOT> consultarTodos(TipoUsuario usuario, Collection<Class<?>> clMoldeOT) throws ErroCadastro;

	/*
	 * 
	 */
	TipoOT consultarPorChavePrimaria(TipoUsuario usuario, TipoOT chave) throws ErroCadastro;

	/*
	 * 
	 */
	TipoOT consultarPorChavePrimaria(TipoUsuario usuario, TipoOT chave, Class<?> moldeOT) throws ErroCadastro;

	/**
	 * Para este usuário, consulta os dados de um item a partir desta chave
	 * primária. Os nomes das propriedades que compõem a chave primária devem ser
	 * obtidos através de uma chamada a <tt>chave.getNomesPropriedadesChave()</tt>.
	 * Os ots retornados devem ser criados a partir destes moldes.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param chave
	 *            ot contendo a chave de consulta do item
	 * @param clMoldeOT
	 *            moldes para os ots a serem retornados
	 *
	 * @return objeto de tranferência com o resultado da consulta
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	TipoOT consultarPorChavePrimaria(TipoUsuario usuario, TipoOT chave, Collection<Class<?>> clMoldeOT)
			throws ErroCadastro;

	/**
	 * Para este usuário, inclui um item a partir dos dados deste objeto de
	 * tranferência. Somente as propriedades obtidas através de uma chamada a
	 * <tt>ot.getNomesPropriedades(modelo)</tt> devem ser usadas para preencher os
	 * campos da tabela. Estas devem estar definidas também no ot retornado, que
	 * pode conter valores inseridos e valores default usados para inicializar
	 * campos da tabela.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param ot
	 *            ot contendo os dados do item
	 *
	 * @return objeto de tranferência contendo os dados do item incluído
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	TipoOT incluir(TipoUsuario usuario, TipoOT ot) throws ErroCadastro;

	/**
	 * Para este usuário, altera um item a partir dos dados deste objeto de
	 * tranferência. Os nomes das propriedades que compõem a chave primária devem
	 * ser obtidos através de uma chamada a
	 * <tt>ot.getNomesPropriedadesChave(modelo)</tt>. Somente as propriedades
	 * obtidas pela chamada a <tt>ot.getNomesPropriedades(modelo)</tt> devem ser
	 * usadas para alterar os campos da tabela. Estas propriedades devem estar
	 * definidas também no ot retornado.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param ot
	 *            ot contendo os dados do item
	 *
	 * @return objeto de tranferência contendo os dados do item alterado
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	TipoOT alterar(TipoUsuario usuario, TipoOT ot) throws ErroCadastro;

	/**
	 * Para este usuário, exclui um item a partir dos dados deste objeto de
	 * tranferência. Os nomes das propriedades que compõem a chave primária devem
	 * ser obtidos através de uma chamada a
	 * <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param ot
	 *            ot contendo os dados do item
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	void excluir(TipoUsuario usuario, TipoOT ot) throws ErroCadastro;
}
