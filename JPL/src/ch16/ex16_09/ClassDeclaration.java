/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_09;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ken Miura
 *
 */
public final class ClassDeclaration {

	private static String declaratedPackage = null;
	private static Class<?> declaratedClass = null;
	
	public static void main(String[] args) {
		try {
			// Class<?> c = Class.forName(args[0]);
			// 表示確認テスト
			Class<?> c = Class.forName(java.util.HashMap.class.getName());
			declaratedPackage = trimClassName(c.getName());
			declaratedClass = c;
			TypeDesc.display(c.getName());

			System.out.println();
			Field[] declaredFields = c.getDeclaredFields();
			for (Field f: declaredFields) {
				printField(f);
			}

			System.out.println();
			Method[] declaredMethods = c.getDeclaredMethods();
			for (Method m: declaredMethods) {
				printMethod(m);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("unknown class: " + args[0]);
		}
	}
	
	private static void printField(Field f) {
		String decl = f.toGenericString();
		printAnnotationsIfLabaled(f);
		System.out.print(" ");
		System.out.println(removePackageName(decl));
	}

	private static void printMethod(Method m) {
		String decl = m.toGenericString();
		printAnnotationsIfLabaled(m);
		System.out.print(" ");
		System.out.println(removePackageName(decl));
	}

	private static String trimClassName (String binaryName) {
		int periodIndex = binaryName.lastIndexOf('.');
		return binaryName.substring(0, periodIndex + 1);
	}

	private static void printAnnotationsIfLabaled(Member m) {
		if (m instanceof AnnotatedElement) {
			Annotation[] annotations = ((AnnotatedElement) m).getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.print(" ");
				System.out.println(removePackageName(annotation.toString()));
			}
		}
	}

	private static String strip(String str, String deletedStr) {
		Pattern pat = Pattern.compile(deletedStr);
		Matcher matcher = pat.matcher(str);
		return matcher.replaceAll("");
	}

	private static String removePackageName (String str) {
		String tempString = strip(strip(str, declaratedClass.getSimpleName() + "\\."), declaratedPackage);
		return strip(tempString, "java.lang.");
	}
	
	public static class TypeDesc {

		public static void display (String name) {

			TypeDesc desc = new TypeDesc();
			try {
				Class <?> startClass = Class.forName(name);
				desc.printType(startClass, 0, basic);
			} catch (ClassNotFoundException e) {
				System.err.println(e);
			}
			
		}
		
		private static String[] basic = {"class", "interface", "enum", "annotation"},
								supercl = {"extends", "implements"},
								iFace = {null, "extends"};
		
		private void printType(Type type, int depth, String[] labels) {
			if (type == null || type == Object.class)
				return;
			
			Class<?> cls = null;
			if (type instanceof Class<?>)
				cls = (Class<?>) type;
			else if (type instanceof ParameterizedType)
				cls = (Class<?>) ((ParameterizedType) type).getRawType();
			else
				throw new Error("Unexpected non-class type");
			
			for (int i=0; i<depth; i++)
				System.out.print(" ");
			
			int kind = cls.isAnnotation() ? 3 : cls.isEnum() ? 2 : cls.isInterface() ? 1 : 0;
			System.out.print(labels[kind] + " ");
			System.out.print(cls.getSimpleName());
			
			TypeVariable<?>[] params = cls.getTypeParameters();
			if (params.length > 0) {
				//bug Eclipse Console window does not handle \b, \f, and \r (https://bugs.eclipse.org/bugs/show_bug.cgi?id=76936)			
				//out.print('<');
				//for (TypeVariable<?> param: params) {
				//	out.print(param.getName());
				//	out.print(", ");
				//}
				//out.println("\b\b>");
				StringBuilder sb = new StringBuilder("<");
				for (TypeVariable<?> param: params) {
					sb.append(param.getName());
					sb.append(", ");
				}
				int lastIndex = sb.lastIndexOf(", ");
				System.out.println(sb.substring(0, lastIndex) + ">");
			}
			else
				System.out.println();
			
			Type[] interfaces = cls.getGenericInterfaces();
			for (Type iface: interfaces) {
				printType(iface, depth+1, cls.isInterface() ? iFace : supercl);
			}
			
			printType(cls.getGenericSuperclass(), depth+1, supercl);
		}
	}

}
