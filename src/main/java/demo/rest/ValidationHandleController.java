package demo.rest;

import org.springframework.context.MessageSource;
import demo.utils.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by KozuePC on 4/14/2017.
 */
@ControllerAdvice
public class ValidationHandleController {

        @Autowired
        private MessageSource messageSource;

        @ExceptionHandler({MethodArgumentNotValidException.class} )
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public MessageHandler processValidationError(MethodArgumentNotValidException ex) {
            BindingResult result = ex.getBindingResult();
            FieldError error = result.getFieldError();

            return processFieldError(error);
        }


    private MessageHandler processFieldError(FieldError error) {
                MessageHandler message = null;
                if (error != null) {
                    Locale currentLocale = LocaleContextHolder.getLocale();
                    String msg = messageSource.getMessage(error.getDefaultMessage(), null, currentLocale);
                    message = new MessageHandler(MessageHandler.MessageType.ERROR, msg);
                }
                return message;
            }


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<List<MessageHandler>> constraintViolation(ConstraintViolationException e) {

        List<MessageHandler> result = new ArrayList<MessageHandler>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            String key = "";
            if (violation.getPropertyPath() != null) {
                key = violation.getPropertyPath().toString();
            }
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage(violation.getMessage(), null, currentLocale);
            result.add(new MessageHandler(key, msg ,MessageHandler.MessageType.ERROR));
        }
        return new ResponseEntity<List<MessageHandler>>(result, HttpStatus.BAD_REQUEST);
    }
}
