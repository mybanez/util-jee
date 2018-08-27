package meyn.util.modelo;

import meyn.util.*;

/**
 * Levantado para indicar a ocorrência de um erro durante algum processamento na
 * camada dos modelos.
 */
@SuppressWarnings("serial")
public class ErroModelo extends Erro {
	public ErroModelo(String msg) {
		super(msg);
	}

	public ErroModelo(String msg, Exception e) {
		super(msg, e);
	}
}
