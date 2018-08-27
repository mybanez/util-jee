package meyn.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Erro ocorrido durante o processamento da <i>framework</i>.
 */
@SuppressWarnings("serial")
public class Erro extends Exception {
	
	public static String toString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}	
	
    /**
     * Cria uma instancia de erro com esta mensagem.
     *
     * @param msg mensagem de erro
     */
    public Erro(String msg) {
        this(msg, null);
    }
    
    /**
     * Cria uma instancia de erro com esta mensagem e esta causa.
     *
     * @param msg mensagem de erro
     * @param causa erro original que causou o problema
     */
    public Erro(String msg, Exception causa) {
        super(msg, causa);
    }
}
