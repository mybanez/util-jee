package fsiscad.modelo.chaveprimaria;

/**
 * Guarda informa��es sobre uma inst�ncia de seq��ncia em uso na camada de modelo.
 *
 * @see GeradorSequenciaBean
 */
public final class InfoSequencia  {
    private Sequencia sequencia;
    private long valorAtual;

    /**
     * Retorna a inst�ncia de {@link Sequencia Sequencia}.
     *
     * @return inst�ncia de {@link Sequencia Sequencia}
     */
    public Sequencia getSequencia() {
        return sequencia;
    }

    /**
     * Define a inst�ncia de {@link Sequencia Sequencia}.
     *
     * @param seq inst�ncia de {@link Sequencia Sequencia}
     */
    public void setSequencia(Sequencia seq) {
        this.sequencia = seq;
    }

    /**
     * Retorna o valor atual da seq��ncia.
     *
     * @return valor atual da seq��ncia
     */
    public long getValorAtual() {
        return valorAtual;
    }

    /**
     * Define o valor atual da seq��ncia.
     *
     * @param valorAtual valor atual da seq��ncia
     */
    public void setValorAtual(long valorAtual) {
        this.valorAtual = valorAtual;
    }
    
    /**
     * Incrementa de 1 o valor atual da seq��ncia.
     *
     * @return valor atual da seq��ncia incrementado de 1
     */
    public long incValorAtual() {
        return ++valorAtual;
    }
}