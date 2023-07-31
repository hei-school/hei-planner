package school.hei.linearE;

import school.hei.linearE.instantiableE.Bound;
import school.hei.linearE.instantiableE.Constant;
import school.hei.linearE.instantiableE.InstantiableE;
import school.hei.linearE.instantiableE.Variable;

import java.util.Arrays;

public class LEFactory {

  public static Mult mult(double c, LinearE le) {
    return new Mult(new Constant(c), le);
  }

  public static Mult mult(InstantiableE e, Variable v) {
    return new Mult(e, new Mono(v));
  }

  public static Add sub(LinearE le1, LinearE le2) {
    return new Add(le1, new Mult(new Constant(-1), le2));
  }

  public static Add sub(double c, LinearE le) {
    return sub(new Mono(c), le);
  }

  public static Add sub(LinearE le, double c) {
    return sub(le, new Mono(c));
  }

  public static Add sub(LinearE le, Variable v) {
    return sub(le, new Mono(v));
  }

  public static LinearE vadd(LinearE... leList) {
    return Arrays.stream(leList)
        .reduce(Add::new)
        .orElse(new Mono(0.));
  }

  public static LinearE vadd(Variable... vList) {
    return vadd(Arrays.stream(vList)
        .map(Mono::new)
        .toArray(Mono[]::new));
  }

  public static LinearE vsigma(LinearE le, Bound... bounds) {
    Sigma compoundSigma = new Sigma(le, bounds[0]);
    for (int i = 1; i < bounds.length; i++) {
      compoundSigma = new Sigma(compoundSigma, bounds[i]);
    }
    return compoundSigma;
  }

  public static LinearE vsigma(Variable le, Bound... bounds) {
    return vsigma(new Mono(le), bounds);
  }
}
