package serviceTest;

import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.TargetService;
import compozitor.processor.core.interfaces.TraversalStrategy;

@Service(target = TargetService.SUPER_CLASS, strategy = TraversalStrategy.ANCESTORS)
class ServiceTargetSuperClassAncestor extends ServiceSuperClass {
}
