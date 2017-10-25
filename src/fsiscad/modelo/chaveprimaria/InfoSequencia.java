package fsiscad.modelo.chaveprimaria;

/**
 * Guarda informações sobre uma instância de seqüência em uso na camada de modelo.
 *
 * @see GeradorSequenciaBean
 */
public final class InfoSequencia  {
    private Sequencia sequencia;
    private long valorAtual;

    /**
     * Retorna a instância de {@link Sequencia Sequencia}.
     *
     * @return instância de {@link Sequencia Sequencia}
     */
    public Sequencia getSequencia() {
        return sequencia;
    }

    /**
     * Define a instância de {@link Sequencia Sequencia}.
     *
     * @param seq instância de {@link Sequencia Sequencia}
     */
    public void setSequencia(Sequencia seq) {
        this.sequencia = seq;
    }

    /**
     * Retorna o valor atual da seqüência.
     *
     * @return valor atual da seqüência
     */
    public long getValorAtual() {
        return valorAtual;
    }

    /**
     * Define o valor atual da seqüência.
     *
     * @param valorAtual valor atual da seqüência
     */
    public void setValorAtual(long valorAtual) {
        this.valorAtual = valorAtual;
    }
    
    /**
     * Incrementa de 1 o valor atual da seqüência.
     *
     * @return valor atual da seqüência incrementado de 1
     */
    public long incValorAtual() {
        return ++valorAtual;
    }
}