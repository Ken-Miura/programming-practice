/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_04;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import ch16.ex16_04.AnnotationsLabeledType.TestAnnotation1;
import ch16.ex16_04.AnnotationsLabeledType.TestAnnotation2;
import ch16.ex16_04.AnnotationsLabeledType.TestAnnotation3;

/**
 * @author Ken Miura
 *
 */
@TestAnnotation1
@TestAnnotation2
@TestAnnotation3
public class AnnotationsLabeledType {

	public static void main(String[] args) {
		try {
			//Class<?> clazz = Class.forName(args[0]);
			Class<?> clazz = Class.forName(AnnotationsLabeledType.class.getName());
			Annotation[] annotations = clazz.getDeclaredAnnotations();
			for (Annotation annotation: annotations) {
				System.out.println(annotation.toString());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@interface TestAnnotation1 {
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@interface TestAnnotation2 {
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@interface TestAnnotation3 {
		
	}

}
