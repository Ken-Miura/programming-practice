/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_05;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ken Miura
 *
 */
public class ClassContents {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Class<?> c = Class.forName(args[0]);
			// 表示確認テスト
			Class<?> c = Class.forName(java.lang.String.class.getName());
			System.out.println(c);

			Field[] fields = c.getFields();
			Field[] declaredFields = c.getDeclaredFields();
			printMembers(mergeMembers(fields, declaredFields));

			Constructor<?>[] constructors = c.getConstructors();
			Constructor<?>[] declaraedConstructors = c.getDeclaredConstructors();
			printMembers(mergeMembers(constructors, declaraedConstructors));

			Method[] methods = c.getMethods();
			Method[] declaredMethods = c.getDeclaredMethods();
			printMembers(mergeMembers(methods, declaredMethods));
		} catch (ClassNotFoundException e) {
			System.out.println("unknown class: " + args[0]);
		}
	}

	private static Member[] mergeMembers(Member[] members1, Member[] members2) {
		List<Member> tempMembers = new ArrayList<>();
		found: 
		for (Member mem1 : members1) {
			for (Member mem2 : members2) {
				if (mem1.equals(mem2)) {
					break found;
				}
			}
			tempMembers.add(mem1);
		}
		List<Member> mergedMembers = new ArrayList<>(tempMembers);
		for (Member m: members2) {
			mergedMembers.add(m);
		}
		return mergedMembers.toArray(new Member[0]);
	}

	private static void printMembers(Member[] mems) {
		for (Member m : mems) {
			if (m.getDeclaringClass() == Object.class)
				continue;
			String decl = m.toString();
			System.out.print(" ");
			System.out.println(strip(decl, "java.lang."));
			printAnnotationsIfLabaled(m);
		}
	}

	private static void printAnnotationsIfLabaled(Member m) {
		if (m instanceof AnnotatedElement) {
			Annotation[] annotations = ((AnnotatedElement) m).getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.print("  ");
				System.out.println(annotation.toString());
			}
		}
	}

	private static String strip(String str, String deletedStr) {
		Pattern pat = Pattern.compile(deletedStr);
		Matcher matcher = pat.matcher(str);
		return matcher.replaceAll("");
	}

}
