package bg.moviebox.web;

import bg.moviebox.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ObjectNotFoundException.class)
  public ModelAndView handleObjectNotFound(ObjectNotFoundException onfe) {
    ModelAndView modelAndView = new ModelAndView("object-not-found");
    modelAndView.addObject("objectId", onfe.getId());

    return modelAndView;
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
    ModelAndView modelAndView = new ModelAndView("invalid-user");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Invalid username or password");
  }
}
