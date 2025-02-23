package cl.tenpo.api_challenge_tenpo.exception;

public class CustomNotFoundException extends RuntimeException {
  public CustomNotFoundException(String message) {
    super(message);
  }
}