package serviceTest;

import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.ServiceProcessor;
import compozitor.processor.core.interfaces.TargetService;

import java.util.Arrays;

@Service(target = TargetService.SUPER_CLASS)
class ServiceTargetSuperClass extends ServiceProcessor {
  @Override
  protected Iterable<Class<?>> serviceClasses() {
    return Arrays.asList();
  }
}
