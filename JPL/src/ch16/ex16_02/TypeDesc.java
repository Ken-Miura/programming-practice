/* Copyright (C) 2015 Ken Miura */
package ch16.ex16_02;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author Ken Miura
 *
 */
public class TypeDesc {

	public static void main (String[] args) {
		// 表示確認テスト
		args = new String [] {"java.lang.ref.Reference$ReferenceHandler"};
		TypeDesc desc = new TypeDesc();
		for (String name: args) {
			try {
				Class <?> startClass = Class.forName(name);
				desc.printType(startClass, 0, basic);
			} catch (ClassNotFoundException e) {
				System.err.println(e);
			}
		}
		
	}
	
	private java.io.PrintStream out = System.out;
	
	private static String[] basic = {"class", "interface", "enum", "annotation"},
							supercl = {"extends", "implements"},
							iFace = {null, "extends"};
	
	/**
	 * @param type
	 * @param depth
	 * @param labels
	 */
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
			out.print(" ");
		
		int kind = cls.isAnnotation() ? 3 : cls.isEnum() ? 2 : cls.isInterface() ? 1 : 0;
		out.print(labels[kind] + " ");
		out.print(cls.getCanonicalName());
		
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
			out.print(sb.substring(0, lastIndex) + ">");
		}
		
		Class<?> outerClass = cls.getDeclaringClass();
		if (outerClass == null) {
			System.out.println(" (not nested)");
		}
		else {
			System.out.println(" nested in " + outerClass.getCanonicalName());
		}
		
		Type[] interfaces = cls.getGenericInterfaces();
		for (Type iface: interfaces) {
			printType(iface, depth+1, cls.isInterface() ? iFace : supercl);
		}
		
		printType(cls.getGenericSuperclass(), depth+1, supercl);
	}
}
