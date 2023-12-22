package com.app.clubnautico.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Order(Ordered.HIGHEST_PRECEDENCE)  //provide hierarchy for handling exceptions from the controller
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> hanldeBadCredentialsException(HttpServletRequest request, BadCredentialsException ex) {
        String errorMsg = "Acceso invalido, usuario y contraseña";
        String path = request.getRequestURI();
        return buildResponseEntity(new ApiException(HttpStatus.UNAUTHORIZED,
                errorMsg,
                path));

    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> hanldeAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        String errorMsg = "Acceso invalido, usuario no autorizado";
        String path = request.getRequestURI();
        return buildResponseEntity(new ApiException(HttpStatus.FORBIDDEN,
                errorMsg,
                path));
    }

    @ExceptionHandler({SignatureException.class})
    public ResponseEntity<Object> hanldeSignatureException(HttpServletRequest request, SignatureException ex) {
        String errorMsg = "Token invalido";
        String path = request.getRequestURI();
        return buildResponseEntity(new ApiException(HttpStatus.FORBIDDEN,
                errorMsg,
                path));
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Object> hanldeExpiredJwtException(HttpServletRequest request, ExpiredJwtException ex) {
        String errorMsg = "Token ha expirado";
        String path = request.getRequestURI();
        return buildResponseEntity(new ApiException(HttpStatus.FORBIDDEN,
                errorMsg,
                path));
    }


    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> hanldeNoSuchElementException(HttpServletRequest request, NoSuchElementException ex) {
        String errorMsg = "No es posible realizar la consulta: " + ex.getMessage();

        String path = request.getRequestURI();

        return buildResponseEntity(new ApiException(HttpStatus.NOT_FOUND,
                errorMsg,
                path));

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> hanldeConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {

        ApiException apiException = new ApiException();
        //almcanear el msg del constraintViolation
        String msg = " ";

        for (ConstraintViolation c : ex.getConstraintViolations()) {
            msg += c.getMessage() + ", ";
        }

        apiException.setPath(request.getRequestURI());
        apiException.setMessage(msg);
        apiException.setHttpStatus(HttpStatus.BAD_REQUEST);

        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(HttpServletRequest request, SQLIntegrityConstraintViolationException ex) {
        ApiException apiException = new ApiException();

        String errorMsg = ex.getLocalizedMessage();
        // Expresión regular para encontrar 'documento_identidad' y 'Y1234567D'
        Pattern pattern = Pattern.compile("Duplicate entry '([^']*)' for key '([^']*)'");
        Matcher matcher = pattern.matcher(errorMsg);

        // Variables para guardar los resultados
        String valor = "";
        String clave = "";

        // Buscar coincidencias y extraer los valores
        if (matcher.find()) {
            valor = matcher.group(1);
            clave = matcher.group(2);
        }

        apiException.setMessage(clave + ": " + valor + " se encuentra registrado en el sistema.");
        apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiException.setPath(request.getRequestURI());
        return buildResponseEntity(apiException);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) {
        ApiException apiException = new ApiException();

        String errorMsg = ex.getMessage();
        // Buscar la posición de la primera apertura y cierre de corchetes
        int inicio = errorMsg.indexOf("[")+1;
        int fin = errorMsg.indexOf("]")-1;

        // Extraer la parte del texto entre los corchetes
        String parteCorchetes = errorMsg.substring(inicio, fin + 1);

        apiException.setMessage(parteCorchetes);
        apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiException.setPath(request.getRequestURI());
        return buildResponseEntity(apiException);

    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParseException(HttpServletRequest request, DateTimeParseException ex) {
        ApiException apiException = new ApiException();

        apiException.setMessage(ex.getMessage());
        apiException.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiException.setPath(request.getRequestURI());
        return buildResponseEntity(apiException);

    }


    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<Object>(apiException, apiException.getHttpStatus());
    }

}
