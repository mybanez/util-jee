package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;

/**
 * Levantado para indicar a ocorr�ncia de um erro durante uma opera��o de
 * cadastro.
 */
public class ErroCadastro extends ErroModelo {
    public ErroCadastro(String msg) {
        super(msg);
    }
    
    public ErroCadastro(String msg, Throwable t) {
        super(msg, t);
    }
}
