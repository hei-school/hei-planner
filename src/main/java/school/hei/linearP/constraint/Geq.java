package school.hei.linearP.constraint;

import school.hei.linearE.LinearE;
import school.hei.linearE.Mono;
import school.hei.linearE.Sub;
import school.hei.linearE.instantiableE.Variable;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public final class Geq extends Constraint {

  private final LinearE le1;
  private final LinearE le2;

  public Geq(String name, LinearE le1, LinearE le2) {
    super(name);
    this.le1 = le1;
    this.le2 = le2;
  }

  public Geq(String name, LinearE le1, double le2) {
    this(name, le1, new Mono(le2));
  }

  public Geq(LinearE le1, LinearE le2) {
    this(null, le1, le2);
  }

  public Geq(LinearE le1, double le2) {
    this(null, le1, le2);
  }

  @Override
  public Set<NormalizedConstraint> normalize() {
    return Set.of(new NormalizedConstraint(name, new Sub(le2, le1).normalize()));
  }

  @Override
  public Set<Variable> variables() {
    return Stream.concat(
            le1.variables().stream(),
            le1.variables().stream())
        .collect(toSet());
  }
}
