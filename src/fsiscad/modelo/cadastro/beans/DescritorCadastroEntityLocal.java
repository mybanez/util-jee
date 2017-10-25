package fsiscad.modelo.cadastro.beans;

import java.lang.reflect.*;

/**
 * Metadados relativos à interface <i>home</i> local de um cadastro 
 * <i>entity bean</i>.
 */
public final class DescritorCadastroEntityLocal {
    private Class tipo;
    private Method metodoCriacao;
    private Class tipoChavePrimaria;
    private boolean tipoChavePrimariaNativo;
    private Method metodoConsultaPorChavePrimaria;
    private Method metodoConsultaTodos;
    
    /**
     * Constrói uma instância contendo os metadados relativos à interface
     * <i>home</i> de um cadastro <i>entity bean</i>.
     */
    public DescritorCadastroEntityLocal(Class tipo, Method metodoCriacao, Class tipoChavePrimaria, Method metodoConsultaPorChavePrimaria, Method metodoConsultaTodos) {
        this.tipo = tipo;
        this.metodoCriacao = metodoCriacao;
        this.tipoChavePrimaria = tipoChavePrimaria;
        this.tipoChavePrimariaNativo = tipoChavePrimaria.getPackage().getName().startsWith("java.lang") ? true : false;
        this.metodoConsultaPorChavePrimaria = metodoConsultaPorChavePrimaria;
        this.metodoConsultaTodos = metodoConsultaTodos;
    }
    
    public Class getTipo() {
        return tipo;
    }
    
    public Method getMetodoCriacao() {
        return metodoCriacao;
    }
    
    public Class getTipoChavePrimaria() {
        return tipoChavePrimaria;
    }
    
    public boolean isTipoChavePrimariaNativo() {
        return tipoChavePrimariaNativo;
    }
    
    public Method getMetodoConsultaPorChavePrimaria() {
        return metodoConsultaPorChavePrimaria;
    }
    
    public Method getMetodoConsultaTodos() {
        return metodoConsultaTodos;
    }
}