package school.hei.planner.constraint.sub;

import school.hei.planner.Timetable;
import school.hei.planner.constraint.TimetableConstraint;
import school.hei.sigmalex.linearP.constraint.Constraint;

import static school.hei.sigmalex.linearP.constraint.Constraint.eq;
import static school.hei.sigmalex.linearP.constraint.Constraint.pic;

public class exclude_days_off extends TimetableConstraint {
  public exclude_days_off(Timetable timetable) {
    super(timetable);
  }

  @Override
  public Constraint constraint() {
    return pic(eq(o_ac_d_s_r, 0), acBound, doffBound, sBound, rBound);
  }
}
