package school.hei.linearE.instantiableE;

public sealed interface InstantiableE permits AddE, Constant, InstantiableV, MultE {
  InstantiableE simplify();
}