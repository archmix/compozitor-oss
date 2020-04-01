package serviceTest;

import compozitor.processor.core.interfaces.Service;
import compozitor.processor.core.interfaces.TargetService;

import java.io.Serializable;

@Service(target = TargetService.INTERFACE)
class ServiceTargetInterface implements ServiceInterface {
}
