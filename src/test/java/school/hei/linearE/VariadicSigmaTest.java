package school.hei.linearE;

import org.junit.jupiter.api.Test;
import school.hei.linearE.instantiableE.Bound;
import school.hei.linearE.instantiableE.BounderZ;
import school.hei.linearE.instantiableE.Constant;
import school.hei.linearE.instantiableE.exception.NoDuplicateBounderException;
import school.hei.linearE.instantiableE.Q;
import school.hei.linearE.instantiableE.Z;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static school.hei.linearE.LEFactory.vadd;
import static school.hei.linearE.LEFactory.vsigma;

class VariadicSigmaTest {
  @Test
  public void nested_sigma_as_variadic() {
    var i = new BounderZ("i");
    var j = new BounderZ("j");
    var le = new Add(new Mono(2, i), new Mono(3, j));

    var boundI = new Bound(i, 4, 6);
    var boundJ = new Bound(j, 10, 11);
    assertEquals(
        new Sigma(new Sigma(le, boundI), boundJ).normalize(),
        vsigma(le, boundI, boundJ).normalize());
  }

  @Test
  public void bounded_vars() {
    var i = new BounderZ("i");
    var j = new BounderZ("j");
    var x_i_j = new Q("x", j, i);
    var le_i_j = vadd(new Mono(i), new Mono(j), new Mono(3, x_i_j));

    var boundI = new Bound(i, 4, 6);
    var boundJ = new Bound(j, 10, 11);
    assertEquals(
        new NormalizedLE(
            Map.of(
                new Q("x[i:4][j:10]"), new Constant(3),
                new Q("x[i:5][j:10]"), new Constant(3),
                new Q("x[i:6][j:10]"), new Constant(3),
                new Q("x[i:4][j:11]"), new Constant(3),
                new Q("x[i:5][j:11]"), new Constant(3),
                new Q("x[i:6][j:11]"), new Constant(3)),
            new Constant(93)),
        vsigma(le_i_j, boundJ, boundI).normalize().simplify());
  }

  @Test
  public void duplicate_bounders_prohibited() {
    var i = new BounderZ("i");
    assertThrows(NoDuplicateBounderException.class, () -> new Q("x", i, i));
    assertThrows(NoDuplicateBounderException.class, () -> new Z("x", i, i));
  }
}