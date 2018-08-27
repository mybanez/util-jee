package meyn.util.modelo.cadastro;

import java.util.*;

import meyn.util.modelo.entidade.*;

/**
 * Define os métodos básicos que um cadastro deve implementar. Os dados do
 * usuário logado só precisam ser passados como parâmetro quando forem
 * necessários para a execução das operações de cadastro. Um exemplo disso é
 * quando <b>os usuários da aplicação são usuários de banco</b>, e portando os
 * dados de login/senha têm de ser repassados para que as conexões JDBC possam
 * ser abertas. 
 *
 * @see Entidade
 */
public interface Cadastro<TipoUsuario extends Entidade, TipoEnt extends Entidade> {
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

	/**
	 * Para este usuário, consulta todos os itens deste modelo. 
	 * 
	 * @param usuario
	 *            usuario logado
	 *
	 * @return lista de objetos de tranferência com o resultado da consulta
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	Collection<TipoEnt> consultarTodos(TipoUsuario usuario) throws ErroCadastro;

	/**
	 * Para este usuário, consulta os dados de um item a partir desta chave
	 * primária. Os nomes das propriedades que compõem a chave primária devem ser
	 * obtidos através de uma chamada a <tt>chave.getNomesPropriedadesChave()</tt>.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param chave
	 *            entidade contendo a chave de consulta do item
	 *
	 * @return objeto de tranferência com o resultado da consulta
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	TipoEnt consultarPorChavePrimaria(TipoUsuario usuario, TipoEnt chave) throws ErroCadastro;

	/**
	 * Para este usuário, inclui um item a partir dos dados deste objeto de
	 * tranferência. Somente as propriedades obtidas através de uma chamada a
	 * <tt>ent.getNomesPropriedades(modelo)</tt> devem ser usadas para preencher os
	 * campos da tabela. Estas devem estar definidas também na entidade retornado, que
	 * pode conter valores inseridos e valores default usados para inicializar
	 * campos da tabela.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @return objeto de tranferência contendo os dados do item incluído
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	TipoEnt incluir(TipoUsuario usuario, TipoEnt ent) throws ErroCadastro;

	/**
	 * Para este usuário, altera um item a partir dos dados deste objeto de
	 * tranferência. Os nomes das propriedades que compõem a chave primária devem
	 * ser obtidos através de uma chamada a
	 * <tt>ot.getNomesPropriedadesChave(modelo)</tt>. Somente as propriedades
	 * obtidas pela chamada a <tt>ot.getNomesPropriedades(modelo)</tt> devem ser
	 * usadas para alterar os campos da tabela. Estas propriedades devem estar
	 * definidas também na entidade retornada.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @return objeto de tranferência contendo os dados do item alterado
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	TipoEnt alterar(TipoUsuario usuario, TipoEnt ent) throws ErroCadastro;

	void excluirTodos(TipoUsuario usuario) throws ErroCadastro;
	
	/**
	 * Para este usuário, exclui um item a partir dos dados deste objeto de
	 * tranferência. Os nomes das propriedades que compõem a chave primária devem
	 * ser obtidos através de uma chamada a
	 * <tt>ot.getNomesPropriedadesChave(modelo)</tt>.
	 *
	 * @param usuario
	 *            usuario logado
	 * @param ent
	 *            entidade contendo os dados do item
	 *
	 * @throws ErroCadastro
	 *             se algum erro ocorreu durante a operação
	 */
	void excluir(TipoUsuario usuario, TipoEnt ent) throws ErroCadastro;
}
