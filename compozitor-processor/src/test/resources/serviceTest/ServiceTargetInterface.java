package serviceTest;

import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.TargetService;

@Service(target = TargetService.INTERFACE)
class ServiceTargetInterface implements ServiceInterface {
}
