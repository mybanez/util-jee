package meyn.util.modelo.cadastro;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meyn.util.modelo.entidade.Entidade;

/**
 * Suporte para implementações de cadastro.
 */
public abstract class CadastroImpl<TipoUsuario extends Entidade, TipoEnt extends Entidade> implements Cadastro<TipoUsuario, TipoEnt> {
	
	private String modelo;
	private Class<?> tipoEntidade;

	private final Logger logger = LogManager.getLogger(getClass());

	public CadastroImpl() throws ErroCadastro {
		Type[] tipos = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		String nomeTipo = tipos[tipos.length-1].getTypeName();
		try {
			tipoEntidade = (Class<?>) Class.forName(nomeTipo);
		} catch (ClassNotFoundException | SecurityException e) {
			throw new ErroCadastro("Erro carregando tipo da entidade: " + nomeTipo, e);
		}
	}

	/**
	 * Retorna o cadastro associado a este modelo.
	 *
	 * @param modelo
	 *            nome lógico do modelo
	 *
	 * @return cadastro associado a este modelo
	 *
	 * @throws ErroCadastro
	 *             se ocorrer um erro na obtenção do cadastro
	 */
	@SuppressWarnings("unchecked")
	protected static final <TipoCadastro extends CadastroImpl<?,?>> TipoCadastro getCadastro(String modelo) throws ErroCadastro {
		return (TipoCadastro) FabricaCadastro.getCadastro(modelo);
	}

	@Override
	public final String getModelo() {
		return modelo;
	}

	@Override
	public final void setModelo(String modelo) {
		this.modelo = modelo;
		logger.debug("iniciado");
	}

	protected Class<?> getTipoEntidade() {
		return tipoEntidade;
	}

	protected Logger getLogger() {
		return logger;
	}

	@Override
	public Collection<TipoEnt> consultarTodos(TipoUsuario usuario) throws ErroCadastro {
		throw new UnsupportedOperationException("consultarTodos");
	}

	@Override
	public TipoEnt consultarPorChavePrimaria(TipoUsuario usuario, TipoEnt chave) throws ErroCadastro {
		throw new UnsupportedOperationException("consultarPorChavePrimaria");
	}

	@Override
	public TipoEnt incluir(TipoUsuario usuario, TipoEnt ent) throws ErroCadastro {
		throw new UnsupportedOperationException("incluir");
	}

	@Override
	public TipoEnt alterar(TipoUsuario usuario, TipoEnt ent) throws ErroCadastro {
		throw new UnsupportedOperationException("alterar");
	}

	@Override
	public void excluirTodos(TipoUsuario usuario) throws ErroCadastro {
		for(TipoEnt ent: consultarTodos(usuario)) {
			excluir(usuario, ent);
		}
	}

	@Override
	public void excluir(TipoUsuario usuario, TipoEnt ent) throws ErroCadastro {
		throw new UnsupportedOperationException("excluir");
	}
}
