package fsiscad.modelo.chaveprimaria;

/**
 * Define os métodos de uma seqüência.
 */
public interface Sequencia {
    /**
     * Retorna o próximo valor da seqüência, que deve ser o último valor
     * retornado somado a este incremento.
     *
     * @return próximo valor da seqüência
     */
    long getProximoValor(int incremento);
}
