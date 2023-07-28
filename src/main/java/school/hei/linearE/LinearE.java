package school.hei.linearE;

import school.hei.linearE.instantiableE.Variable;

import java.util.Set;

public sealed interface LinearE permits Add, Mono, Mult, NormalizedLE, Sigma {
  NormalizedLE normalize();

  Set<Variable> variables();
}
