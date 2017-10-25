package fsiscad.modelo.chaveprimaria;

import java.net.*;

import java.security.*;

/**
 * Gerador de UUIDs (<i>Universally Unique IDentifier</i>). Implementa a
 * t�cnica descrita no livro <i>EJB Design Patterns</i> de Floyd Marinescu.
 *
 * @see GeradorChavePrimaria
 */
public final class GeradorUUID {
    private static GeradorUUID instancia;
    private SecureRandom gna; //Gerador de n�meros aleat�rios
    private String mioloUUID;
    
    private GeradorUUID() throws UnknownHostException {
        mioloUUID = formatarHexa(converterIpParaInt(InetAddress.getLocalHost().getAddress())) +
                formatarHexa(System.identityHashCode(this));
        gna = new SecureRandom();
    }
    
    private static String formatarHexa(int n) {
        String s = Integer.toHexString(n);
        StringBuffer sb = new StringBuffer();
        if (s.length() < 8) {
            for (int i = 0; i < (8 - s.length()); i++) {
                sb.append("0");
            }
        }
        return sb + s;
    }
    
    private static int converterIpParaInt(byte[] bytes) {
        int ip = 0;
        int desloc = 24;
        for (int i = 0; desloc >= 0; i++) {
            ip += ((bytes[i] & 0xff) << desloc);
            desloc -= 8;
        }
        return ip;
    }
    
    /**
     * Retorna o gerador de UUIDs.
     *
     * @return gerador de UUIDs
     *
     * @throws UnknownHostException se n�o for poss�vel obter o IP da m�quina
     *         onde o gerador est� executando. O IP � requerido pelo algoritmo
     *         de gera��o de UUIDs
     */
    public static GeradorUUID getGeradorUUID() throws UnknownHostException {
        if (instancia == null) {
            synchronized (GeradorUUID.class) {
                if (instancia == null) {
                    instancia = new GeradorUUID();
                }
            }
        }
        return instancia;
    }
    
    /**
     * Retorna um identificador universal �nico.
     *
     * @return identificador universal �nico
     *
     * @throws ErroUUID se ocorrer um erro durante a gera��o do UUID
     */
    public String getUUID() throws ErroGeradorUUID {
        try {
            return formatarHexa((int) System.currentTimeMillis() & 0xffffffff) +
                    mioloUUID + formatarHexa(gna.nextInt());
        } catch (Exception e) {
            throw new ErroGeradorUUID("Erro gerando UUID", e);
        }
    }
}
