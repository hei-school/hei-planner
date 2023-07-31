package school.hei.linearE.exception;

import java.util.Set;

public class DuplicateVariableNameException extends RuntimeException {
  public DuplicateVariableNameException(Set<String> duplicateNames) {
    super(duplicateNames.toString());
  }
}