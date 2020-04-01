package serviceTest;

import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.TargetService;
import compozitor.processor.core.interfaces.TraversalStrategy;

import java.util.ArrayList;

@Service(target = TargetService.SUPER_CLASS, strategy = TraversalStrategy.ANCESTORS)
class ServiceTargetSuperClassAncestor extends ServiceSuperClass {
}
