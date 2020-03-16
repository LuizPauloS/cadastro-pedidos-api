package br.com.portfolio.lsilva.cadastropedidosapi.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
