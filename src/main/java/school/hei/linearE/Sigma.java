package school.hei.linearE;

import school.hei.linearE.instantiableE.AddE;
import school.hei.linearE.instantiableE.ArithmeticConversion;
import school.hei.linearE.instantiableE.Bounder;
import school.hei.linearE.instantiableE.BounderValue;
import school.hei.linearE.instantiableE.Constant;
import school.hei.linearE.instantiableE.MultE;
import school.hei.linearE.instantiableE.SigmaZ;
import school.hei.linearE.instantiableE.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public record Sigma(LinearE le, SigmaBound sigmaBound) implements LinearE {

  public record SigmaBound(Bounder bounder, BounderValue[] values) {
    public SigmaBound(SigmaZ k, int kMin, int kMax) {
      this(k, IntStream.range(kMin, kMax + 1).mapToObj(Constant::new).toArray(Constant[]::new));
    }
  }

  @Override
  public Normalized normalize() {
    var normalizedLeToSigma = le.normalize();
    LinearE summed = new Mono(0);

    var bounderValues = sigmaBound.values();
    for (BounderValue bounderValue : bounderValues) {
      summed = new Add(
          summed,
          substitute(sigmaBound.bounder().variable(), bounderValue, normalizedLeToSigma));
    }
    return summed.normalize();
  }

  private Normalized substitute(Variable k, BounderValue kValue, Normalized normalized) {
    var weightedV = normalized.weightedV();
    var substitutedWeightedV = new HashMap<>(weightedV);
    weightedV.forEach((v, c) -> {
      if (v.getBoundedTo().contains(k)) {
        var boundedToWithoutK = new ArrayList<>(v.getBoundedTo());
        boundedToWithoutK.remove(k);
        substitutedWeightedV.put(v.toNew(v.getName() + "_" + kValue, boundedToWithoutK), c);
        substitutedWeightedV.remove(v);
      }
    });

    var newE = normalized.e();
    if (weightedV.containsKey(k)) {
      try {
        newE = new AddE(newE, new MultE(kValue.toArithmeticValue(), weightedV.get(k)));
      } catch (ArithmeticConversion e) {
        throw new RuntimeException(e);
      }
      substitutedWeightedV.remove(k);
    }
    return new Normalized(substitutedWeightedV, newE);
  }
}
