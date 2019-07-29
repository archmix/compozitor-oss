package compozitor.processor.core.interfaces;

import java.text.MessageFormat;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public class ProcessorStateBuilder {
	private final String resume;
	
	private ProcessorStateBuilder(TypeModel target) {
		this.resume = message("State report for {0}\r\n", target.getQualifiedName());
	}
	
	public static ProcessorStateBuilder create(TypeModel target) {
		return new ProcessorStateBuilder(target);
	}
	
	public ThrowingException illegalArgument(String message, Object... args) {
		String report = report(message, args);
		return new ThrowingException(new IllegalArgumentException(report));
	}
	
	public ThrowingException illegalState(String message, Object... args) {
		String report = report(message, args);
		return new ThrowingException(new IllegalStateException(report));
	}

	private String report(String message, Object... args) {
		StringBuilder report = new StringBuilder(this.resume);
		report.append(message(message, args));
		return report.toString();
	}
	
	private String message(String message, Object...args) {
		return MessageFormat.format(message, args);
	}
	
	@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
	public class ThrowingException {
		private final RuntimeException ex;
		
		public void doThrow() {
			throw ex;
		}
	}
}
