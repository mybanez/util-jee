package fsiscad.modelo.chaveprimaria;

/**
 * Define os m�todos de uma seq��ncia.
 */
public interface Sequencia {
    /**
     * Retorna o pr�ximo valor da seq��ncia, que deve ser o �ltimo valor
     * retornado somado a este incremento.
     *
     * @return pr�ximo valor da seq��ncia
     */
    long getProximoValor(int incremento);
}
