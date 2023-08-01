package school.hei.linearP;

import school.hei.linearE.LinearE;
import school.hei.linearE.instantiableE.Variable;
import school.hei.linearP.constraint.Constraint;
import school.hei.linearP.constraint.NormalizedConstraint;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static school.hei.linearE.LEFactory.mono;
import static school.hei.linearP.constraint.Constraint.and;

public record LP(
    String name,
    OptimizationType optimizationType,
    LinearE objective,
    Set<Constraint> constraints) {

  public LP(OptimizationType optimizationType,
            LinearE objective,
            Set<Constraint> constraints) {
    this(null, optimizationType, objective, constraints);
  }

  public LP(String name,
            OptimizationType optimizationType,
            LinearE objective,
            Constraint... constraints) {
    this(name, optimizationType, objective, Set.of(constraints));
  }

  public LP(OptimizationType optimizationType,
            LinearE objective,
            Constraint... constraints) {
    this(null, optimizationType, objective, Set.of(constraints));
  }

  public LP(OptimizationType optimizationType,
            Variable v,
            Constraint... constraints) {
    this(null, optimizationType, mono(v), Set.of(constraints));
  }

  public Set<NormalizedLP> normify() {
    var disjunctivePolytopes = and(constraints.toArray(new Constraint[0]))
        .normalize();
    return disjunctivePolytopes.polytopes().stream()
        .map(polytope -> new NormalizedLP(
            name,
            optimizationType,
            objective.normalize().simplify(),
            polytope.constraints().stream()
                .map(NormalizedConstraint::simplify)
                .collect(toSet())))
        .collect(toSet());
  }
}
