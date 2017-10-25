package fsiscad.util;

/**
 * Levantado para indicar a ocorr�ncia de um erro durante alguma opera��o do
 * {@link LocalizadorEJB LocalizadorEJB}.
 */
public class ErroLocalizadorEJB extends Erro {
    public ErroLocalizadorEJB(String msg) {
        super(msg);
    }
    
    public ErroLocalizadorEJB(String msg, Throwable causa) {
        super(msg, causa);
    }
}
