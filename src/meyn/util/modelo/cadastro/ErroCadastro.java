package meyn.util.modelo.cadastro;

import meyn.util.modelo.*;

/**
 * Levantado para indicar a ocorrência de um erro durante uma operação de
 * cadastro.
 */
@SuppressWarnings("serial")
public class ErroCadastro extends ErroModelo {
    public ErroCadastro(String msg) {
        super(msg);
    }
    
    public ErroCadastro(String msg, Throwable t) {
        super(msg, t);
    }
}
