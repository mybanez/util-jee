package fsiscad.modelo.cadastro;

import fsiscad.modelo.*;

/**
 * Guarda informações sobre uma implementação de cadastro do tipo
 * {@link CadastroLocal CadastroLocal}.
 */
public final class InfoCadastroLocal extends InfoCadastro {

    /**
     * Cria um mapeamento entre este modelo e o cadastro local deste tipo.
     *
     * @param modelo nome lógico do modelo
     * @param tipo tipo do cadastro local
     */
    public InfoCadastroLocal(String modelo, String tipo) {
        super(modelo, tipo);
    }
    
    protected FabricaCadastro getFabricaCadastro() throws ErroModelo {
        return FabricaCadastro.getFabricaCadastro(FabricaCadastroLocal.class.getName());
    }
}
