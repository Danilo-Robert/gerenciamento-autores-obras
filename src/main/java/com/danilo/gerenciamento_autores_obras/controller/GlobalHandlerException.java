package com.danilo.gerenciamento_autores_obras.controller;

import com.danilo.gerenciamento_autores_obras.infrastructure.exceptions.BusinessException;
import com.danilo.gerenciamento_autores_obras.infrastructure.exceptions.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex,
                                                                 HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Bad Request"
        ));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex,
                                                                                                                            HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Internal Server Error"
        ));
    }

    private ErrorResponse buildError(int status, String mensagem, String path, String error){
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(mensagem)
                .error(error)
                .status(status)
                .path(path)
                .build();
    }
}
