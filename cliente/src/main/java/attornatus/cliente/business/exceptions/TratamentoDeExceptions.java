package attornatus.cliente.business.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public final class TratamentoDeExceptions {

    @Autowired
    private MessageSource mensagemInternacionalizada;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<RetornoPadraoDeException>> capturarExceptionDeValidacao(MethodArgumentNotValidException notValid) {

        List<RetornoPadraoDeException> errors = new ArrayList<>();
        notValid.getBindingResult().getFieldErrors().forEach(err -> {
            String mensagem = mensagemInternacionalizada.getMessage(err, LocaleContextHolder.getLocale());
            errors.add(new RetornoPadraoDeException(HttpStatus.BAD_REQUEST.toString(), err.getCode(), err.getField(), mensagem));
        });

        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(ExceptionEntidadeNaoEncontrada.class)
    public ResponseEntity<RetornoPadraoDeException> capturarExceptionDeEntidadeNaoEncontrada(ExceptionEntidadeNaoEncontrada naoEncontrada) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RetornoPadraoDeException(HttpStatus.NOT_FOUND.toString(), null, null, naoEncontrada.getMessage()));
    }

    @ExceptionHandler(ExceptionRequisicaoMalFeita.class)
    public ResponseEntity<RetornoPadraoDeException> capturarExceptionDeRequisicaoMalFeita(ExceptionRequisicaoMalFeita malFeita) {

        return ResponseEntity
                .badRequest()
                .body(new RetornoPadraoDeException(HttpStatus.BAD_REQUEST.toString(), null, null, malFeita.getMessage()));
    }
}

