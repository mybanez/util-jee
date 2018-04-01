package meyn.util.modelo;

import java.util.*;

import meyn.util.modelo.ot.*;

/**
 * Suporte para a implementação das classes de acesso ao modelo.
 */
public abstract class AcessoModeloImpl implements AcessoModelo {
	public Collection<? extends OT> consultarTodos(String modelo, OT molde) throws ErroModelo {
		return consultarTodos(null, modelo, molde);
	}

	public Collection<? extends OT> consultarTodos(String modelo, Class<?> molde) throws ErroModelo {
		return consultarTodos(null, modelo, molde);
	}

	public Collection<? extends OT> consultarTodos(OT usuario, String modelo, OT molde) throws ErroModelo {
		return consultarTodos(usuario, modelo, molde.getClass());
	}

	public abstract Collection<? extends OT> consultarTodos(OT usuario, String modelo, Class<?> molde) throws ErroModelo;

	public OT consultarPorChavePrimaria(String modelo, OT chave) throws ErroModelo {
		return consultarPorChavePrimaria(null, modelo, chave);
	}

	public OT consultarPorChavePrimaria(String modelo, OT chave, Class<?> molde) throws ErroModelo {
		return consultarPorChavePrimaria(null, modelo, chave, molde);
	}

	public OT consultarPorChavePrimaria(String modelo, OT chave, OT molde) throws ErroModelo {
		return consultarPorChavePrimaria(null, modelo, chave, molde);
	}

	public OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave) throws ErroModelo {
		return consultarPorChavePrimaria(usuario, modelo, chave, chave);
	}

	public OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, OT molde) throws ErroModelo {
		return consultarPorChavePrimaria(usuario, modelo, chave, molde.getClass());
	}

	public abstract OT consultarPorChavePrimaria(OT usuario, String modelo, OT chave, Class<?> molde) throws ErroModelo;

	public OT incluir(String modelo, OT ot) throws ErroModelo {
		return incluir(null, modelo, ot);
	}

	public abstract OT incluir(OT usuario, String modelo, OT ot) throws ErroModelo;

	public OT alterar(String modelo, OT ot) throws ErroModelo {
		return alterar(null, modelo, ot);
	}

	public abstract OT alterar(OT usuario, String modelo, OT ot) throws ErroModelo;

	public void excluir(String modelo, OT ot) throws ErroModelo {
		excluir(null, modelo, ot);
	}

	public abstract void excluir(OT usuario, String modelo, OT ot) throws ErroModelo;
}
