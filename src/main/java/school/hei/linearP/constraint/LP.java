package school.hei.linearP.constraint;

import school.hei.linearE.LinearE;
import school.hei.linearP.NormalizedLP;
import school.hei.linearP.OptimizationType;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public record LP(
    String name,
    OptimizationType optimizationType,
    LinearE objective,
    Set<Constraint> constraints) {

  public NormalizedLP normalize() {
    return new NormalizedLP(
        name,
        optimizationType,
        objective.normalize(),
        constraints.stream()
            .flatMap(constraint -> constraint.normalize().stream())
            .collect(toSet()));
  }
}