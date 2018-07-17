package meyn.util.modelo.cadastro;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meyn.util.modelo.ot.OT;

/**
 * Suporte para implementações de cadastro.
 */
public abstract class CadastroImpl<TipoUsuario extends OT, TipoOT extends OT> implements Cadastro<TipoUsuario, TipoOT> {
	
	private String modelo;
	private Class<?> moldeOTPadrao;

	private final Logger logger = LogManager.getLogger(getClass());

	public CadastroImpl() throws ErroCadastro {
		Type[] tipos = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
		String nomeTipo = tipos[tipos.length-1].getTypeName();
		try {
			moldeOTPadrao = (Class<?>) Class.forName(nomeTipo);
		} catch (ClassNotFoundException | SecurityException e) {
			throw new ErroCadastro("Erro carregando molde OT padrão: " + nomeTipo, e);
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
		logger.debug("instanciado");
	}

	public Class<?> getMoldeOTPadrao() {
		return moldeOTPadrao;
	}

	public Logger getLogger() {
		return logger;
	}

	@Override
	public Collection<TipoOT> consultarTodos(TipoUsuario usuario) throws ErroCadastro {
		return consultarTodos(usuario, getMoldeOTPadrao());
	}

	@Override
	public Collection<TipoOT> consultarTodos(TipoUsuario usuario, Class<?> moldeOT) throws ErroCadastro {
		Set<Class<?>> clMoldeOT = new HashSet<Class<?>>();
		clMoldeOT.add(getMoldeOTPadrao());
		clMoldeOT.add(moldeOT);
		return consultarTodos(usuario, clMoldeOT);
	}

	@Override
	public Collection<TipoOT> consultarTodos(TipoUsuario usuario, Collection<Class<?>> clMoldeOT) throws ErroCadastro {
		throw new UnsupportedOperationException("consultarTodos");
	}

	@Override
	public TipoOT consultarPorChavePrimaria(TipoUsuario usuario, TipoOT chave) throws ErroCadastro {
		return consultarPorChavePrimaria(usuario, chave, getMoldeOTPadrao());
	}

	@Override
	public TipoOT consultarPorChavePrimaria(TipoUsuario usuario, TipoOT chave, Class<?> moldeOT) throws ErroCadastro {
		Set<Class<?>> clMoldeOT = new HashSet<Class<?>>();
		clMoldeOT.add(getMoldeOTPadrao());
		clMoldeOT.add(moldeOT);
		return consultarPorChavePrimaria(usuario, chave, clMoldeOT);
	}

	@Override
	public TipoOT consultarPorChavePrimaria(TipoUsuario usuario, TipoOT chave, Collection<Class<?>> clMoldeOT)
			throws ErroCadastro {
		throw new UnsupportedOperationException("consultarPorChavePrimaria");
	}

	@Override
	public TipoOT incluir(TipoUsuario usuario, TipoOT ot) throws ErroCadastro {
		throw new UnsupportedOperationException("incluir");
	}

	@Override
	public TipoOT alterar(TipoUsuario usuario, TipoOT ot) throws ErroCadastro {
		throw new UnsupportedOperationException("alterar");
	}

	@Override
	public void excluir(TipoUsuario usuario, TipoOT ot) throws ErroCadastro {
		throw new UnsupportedOperationException("excluir");
	}
}
