package com.pml.sistema.bancario.projeto.entity.account.exceptions;

import java.io.Serial;

public class InvalidDocumentException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;


    public InvalidDocumentException(String message) {
        super(message);
    }


    public InvalidDocumentException() {
        super("Documento inválido.");
    }
}
