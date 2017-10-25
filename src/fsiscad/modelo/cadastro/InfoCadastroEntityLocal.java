package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;

/**
 * Guarda informações sobre uma implementação de cadastro do tipo
 * {@link CadastroEntityLocal CadastroEntityLocal}.
 */
public final class InfoCadastroEntityLocal extends InfoCadastro {
    private String nomeJNDI;
    
    /**
     * Cria um mapeamento entre este modelo e o cadastro <i>entity bean</i> com 
     * este tipo de interface <i>home</i> e este registro JNDI.
     * 
     * @param modelo nome lógico do modelo
     * @param home interface <i>home</i> do cadastro <i>entity bean</i>
     * @param nomeJNDI registro JNDI do cadastro <i>entity bean</i>
     */
    public InfoCadastroEntityLocal(String modelo, String home, String nomeJNDI) {
        super(modelo, home);
        this.nomeJNDI = nomeJNDI;
    }
    
    /**
     * Retorna o registro JNDI do cadastro <i>entity bean</i>.
     *
     * @return registro JNDI do cadastro <i>entity bean</i>
     */
    public String getNomeJNDI() {
        return nomeJNDI;
    }
    
    protected FabricaCadastro getFabricaCadastro() throws ErroModelo {
        return FabricaCadastro.getFabricaCadastro(FabricaCadastroEntityLocal.class.getName());
    }
}
