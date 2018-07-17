package meyn.util.modelo;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meyn.util.modelo.cadastro.Cadastro;
import meyn.util.modelo.cadastro.FabricaCadastro;
import meyn.util.modelo.ot.OT;

/**
 * Fachada que repassa operações em cima dos modelos para componentes do tipo
 * {@link Cadastro Cadastro} que implementam estas operações (<i>pattern
 * Facade</i>). Esta classe usa {@link FabricaCadastro FabricaCadastro} para
 * obter acesso aos cadastros.
 */
public abstract class FachadaModeloImpl implements FachadaModelo {

	private final static OT USUARIO_NULO = null;

	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Retorna o cadastro associado a este nome lógico de modelo.
	 *
	 * @throws ErroModelo
	 *             se ocorrer um erro na obtenção do cadastro
	 */
	protected final <TipoUsuario extends OT, TipoOT extends OT> Cadastro<TipoUsuario, TipoOT> getCadastro(
			String modelo) throws ErroModelo {
		return FabricaCadastro.getCadastro(modelo);
	}

	@Override
	public <TipoOT extends OT> Collection<TipoOT> consultarTodos(String modelo) throws ErroModelo {
		return consultarTodos(USUARIO_NULO, modelo);
	}

	@Override
	public <TipoOT extends OT> Collection<TipoOT> consultarTodos(String modelo, Object molde)
			throws ErroModelo {
		return consultarTodos(USUARIO_NULO, modelo, molde.getClass());
	}

	@Override
	public <TipoOT extends OT> Collection<TipoOT> consultarTodos(String modelo, Class<?> molde)
			throws ErroModelo {
		return consultarTodos(USUARIO_NULO, modelo, molde);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> Collection<TipoOT> consultarTodos(TipoUsuario usuario,
			String modelo) throws ErroModelo {
		return this.<TipoUsuario, TipoOT>getCadastro(modelo).consultarTodos(usuario);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> Collection<TipoOT> consultarTodos(TipoUsuario usuario,
			String modelo, Object molde) throws ErroModelo {
		return consultarTodos(usuario, modelo, molde.getClass());
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> Collection<TipoOT> consultarTodos(TipoUsuario usuario,
			String modelo, Class<?> molde) throws ErroModelo {
		return this.<TipoUsuario, TipoOT>getCadastro(modelo).consultarTodos(usuario, molde);
	}

	@Override
	public <TipoOT extends OT> TipoOT consultarPorChavePrimaria(String modelo, TipoOT chave)
			throws ErroModelo {
		return consultarPorChavePrimaria(USUARIO_NULO, modelo, chave);
	}

	@Override
	public <TipoOT extends OT> TipoOT consultarPorChavePrimaria(String modelo, TipoOT chave, Object molde)
			throws ErroModelo {
		return consultarPorChavePrimaria(USUARIO_NULO, modelo, chave, molde);
	}

	@Override
	public <TipoOT extends OT> TipoOT consultarPorChavePrimaria(String modelo, TipoOT chave, Class<?> molde)
			throws ErroModelo {
		return consultarPorChavePrimaria(USUARIO_NULO, modelo, chave, molde);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> TipoOT consultarPorChavePrimaria(TipoUsuario usuario,
			String modelo, TipoOT chave) throws ErroModelo {
		return this.<TipoUsuario, TipoOT>getCadastro(modelo).consultarPorChavePrimaria(usuario, chave);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> TipoOT consultarPorChavePrimaria(TipoUsuario usuario,
			String modelo, TipoOT chave, Object molde) throws ErroModelo {
		return consultarPorChavePrimaria(usuario, modelo, chave, molde.getClass());
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> TipoOT consultarPorChavePrimaria(TipoUsuario usuario,
			String modelo, TipoOT chave, Class<?> molde) throws ErroModelo {
		return this.<TipoUsuario, TipoOT>getCadastro(modelo).consultarPorChavePrimaria(usuario, chave, molde);
	}

	@Override
	public <TipoOT extends OT> TipoOT incluir(String modelo, TipoOT ot) throws ErroModelo {
		return incluir(USUARIO_NULO, modelo, ot);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> TipoOT incluir(TipoUsuario usuario, String modelo, TipoOT ot)
			throws ErroModelo {
		return this.<TipoUsuario, TipoOT>getCadastro(modelo).incluir(usuario, ot);
	}

	@Override
	public <TipoOT extends OT> TipoOT alterar(String modelo, TipoOT ot) throws ErroModelo {
		return alterar(USUARIO_NULO, modelo, ot);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> TipoOT alterar(TipoUsuario usuario, String modelo, TipoOT ot)
			throws ErroModelo {
		return this.<TipoUsuario, TipoOT>getCadastro(modelo).alterar(usuario, ot);
	}


	@Override
	public <TipoOT extends OT> void excluir(String modelo, TipoOT ot) throws ErroModelo {
		excluir(USUARIO_NULO, modelo, ot);
	}

	@Override
	public <TipoUsuario extends OT, TipoOT extends OT> void excluir(TipoUsuario usuario, String modelo, TipoOT ot)
			throws ErroModelo {
		this.<TipoUsuario, TipoOT>getCadastro(modelo).excluir(usuario, ot);
	}
}
