package meyn.util.modelo;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import meyn.util.modelo.cadastro.Cadastro;
import meyn.util.modelo.cadastro.FabricaCadastro;
import meyn.util.modelo.entidade.Entidade;

/**
 * Fachada que repassa operações em cima dos modelos para componentes do tipo
 * {@link Cadastro Cadastro} que implementam estas operações (<i>pattern
 * Facade</i>). Esta classe usa {@link FabricaCadastro FabricaCadastro} para
 * obter acesso aos cadastros.
 */
public abstract class FachadaModeloImpl implements FachadaModelo {

	private final static Entidade USUARIO_NULO = null;

	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Retorna o cadastro associado a este nome lógico de modelo.
	 *
	 * @throws ErroModelo se ocorrer um erro na obtenção do cadastro
	 */
	protected static final <TipoUsuario extends Entidade, TipoEnt extends Entidade> Cadastro<TipoUsuario, TipoEnt> getCadastro(
	        String modelo) throws ErroModelo {
		return FabricaCadastro.getCadastro(modelo);
	}

	@Override
	public <TipoEnt extends Entidade> Collection<TipoEnt> consultarTodos(String modelo) throws ErroModelo {
		return consultarTodos(USUARIO_NULO, modelo);
	}

	@Override
	public <TipoUsuario extends Entidade, TipoEnt extends Entidade> Collection<TipoEnt> consultarTodos(TipoUsuario usuario, String modelo)
	        throws ErroModelo {
		return FachadaModeloImpl.<TipoUsuario, TipoEnt>getCadastro(modelo).consultarTodos(usuario);
	}

	@Override
	public <TipoEnt extends Entidade> TipoEnt consultarPorChavePrimaria(String modelo, TipoEnt chave) throws ErroModelo {
		return consultarPorChavePrimaria(USUARIO_NULO, modelo, chave);
	}

	@Override
	public <TipoUsuario extends Entidade, TipoEnt extends Entidade> TipoEnt consultarPorChavePrimaria(TipoUsuario usuario, String modelo,
	        TipoEnt chave) throws ErroModelo {
		return FachadaModeloImpl.<TipoUsuario, TipoEnt>getCadastro(modelo).consultarPorChavePrimaria(usuario, chave);
	}

	@Override
	public <TipoEnt extends Entidade> TipoEnt incluir(String modelo, TipoEnt ent) throws ErroModelo {
		return incluir(USUARIO_NULO, modelo, ent);
	}

	@Override
	public <TipoUsuario extends Entidade, TipoEnt extends Entidade> TipoEnt incluir(TipoUsuario usuario, String modelo, TipoEnt ent)
	        throws ErroModelo {
		return FachadaModeloImpl.<TipoUsuario, TipoEnt>getCadastro(modelo).incluir(usuario, ent);
	}

	@Override
	public <TipoEnt extends Entidade> TipoEnt alterar(String modelo, TipoEnt ent) throws ErroModelo {
		return alterar(USUARIO_NULO, modelo, ent);
	}

	@Override
	public <TipoUsuario extends Entidade, TipoEnt extends Entidade> TipoEnt alterar(TipoUsuario usuario, String modelo, TipoEnt ent)
	        throws ErroModelo {
		return FachadaModeloImpl.<TipoUsuario, TipoEnt>getCadastro(modelo).alterar(usuario, ent);
	}

	@Override
	public void excluirTodos(String modelo) throws ErroModelo {
		excluirTodos(USUARIO_NULO, modelo);
	}

	@Override
	public <TipoEnt extends Entidade> void excluir(String modelo, TipoEnt ent) throws ErroModelo {
		excluir(USUARIO_NULO, modelo, ent);
	}

	@Override
	public <TipoUsuario extends Entidade> void excluirTodos(TipoUsuario usuario, String modelo) throws ErroModelo {
		FachadaModeloImpl.<TipoUsuario, Entidade>getCadastro(modelo).excluirTodos(usuario);
	}

	@Override
	public <TipoUsuario extends Entidade, TipoEnt extends Entidade> void excluir(TipoUsuario usuario, String modelo, TipoEnt ent)
	        throws ErroModelo {
		FachadaModeloImpl.<TipoUsuario, TipoEnt>getCadastro(modelo).excluir(usuario, ent);
	}
}
