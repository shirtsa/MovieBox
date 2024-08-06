package bg.moviebox.web.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
  // Pointcut matches all methods who has annotation WarnIfExecutionExceeds
  @Pointcut("@annotation(WarnIfExecutionExceeds)")
  void onWarnIfExecutionTimeExceeds(){}
}
