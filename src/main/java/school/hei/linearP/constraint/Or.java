package school.hei.linearP.constraint;

import school.hei.linearE.instantiableE.Variable;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public final class Or extends Constraint {

  private final Constraint constraint1;
  private final Constraint constraint2;

  public Or(String name, Constraint constraint1, Constraint constraint2) {
    super(name);
    this.constraint1 = constraint1;
    this.constraint2 = constraint2;
  }

  @Override
  public Set<Set<NormalizedConstraint>> normalize() {
    return Stream.concat(
            constraint1.normalize().stream(),
            constraint2.normalize().stream())
        .collect(toSet());
  }

  @Override
  public Set<Variable> variables() {
    return Stream.concat(
            constraint1.variables().stream(),
            constraint2.variables().stream())
        .collect(toSet());
  }
}