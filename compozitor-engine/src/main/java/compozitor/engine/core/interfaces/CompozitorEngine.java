package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.CodeGenerator;
import compozitor.generator.core.interfaces.GeneratorContext;

public class CompozitorEngine<T> {
	
	private final CodeGenerator<T> generator;
	
	public CompozitorEngine() {
		this.generator = new CodeGenerator<>();
	}
	
	public void generate(EngineContext<T> context){
		EngineListener listener = context.getListener();
		
		context.apply((repository, template) ->{
			repository.list().forEach(data ->{
				try {
					this.generator.execute(GeneratorContext.create(template), repository).forEach(code ->{
						listener.accept(code);
					});
				} catch(RuntimeException ex) {
					listener.notify(ex);
				}
			});
		});
	}
}
