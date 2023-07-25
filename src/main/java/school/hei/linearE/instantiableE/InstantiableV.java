package school.hei.linearE.instantiableE;

import java.util.List;

public abstract sealed class InstantiableV
    extends Variable implements InstantiableE permits SigmaZ {
  public InstantiableV(String name, List<Bounder> boundedTo) {
    super(name, boundedTo);
  }
}