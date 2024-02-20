/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import net.sf.picard.cmdline.CommandLineParseException;
import net.sf.picard.cmdline.CommandLineParserDefinitionException;

/**
 *
 * @author Huabin Hou
 */
public class RefelectUtil {

    public static Object constructFromString(final Class clazz, final String s) {
        try {
            if (clazz.isEnum()) {
                try {
                    return Enum.valueOf(clazz, s);
                } catch (IllegalArgumentException e) {
                   // throw new CommandLineParseException("'" + s + "' is not a valid value for " +
                           // clazz.getSimpleName() + ".", e);
                }
            }
            final Constructor ctor = clazz.getConstructor(String.class);
            return ctor.newInstance(s);
        } catch (NoSuchMethodException e) {
            // Shouldn't happen because we've checked for presence of ctor
           // throw new CommandLineParseException(e.getMessage());
        } catch (InstantiationException e) {
          //  throw new CommandLineParseException("Abstract class '" + clazz.getSimpleName() +
                 //   "'cannot be used for an option value type.", e);
        } catch (IllegalAccessException e) {
           // throw new CommandLineParseException("String constructor for option value type '" + clazz.getSimpleName() +
                 //   "' must be public.", e);
        } catch (InvocationTargetException e) {
           // throw new CommandLineParseException("Problem constructing " + clazz.getSimpleName() + " from the string '" + s + "'.",
                 //   e.getCause());
        }
        return null;
    }

    public static boolean isCollectionField(final Field field) {
        try {
            field.getType().asSubclass(Collection.class);
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public static Class getUnderlyingType(final Field field) {
        if (isCollectionField(field)) {
            final ParameterizedType clazz = (ParameterizedType) (field.getGenericType());
            final Type[] genericTypes = clazz.getActualTypeArguments();
            if (genericTypes.length != 1) {
              //  throw new CommandLineParserDefinitionException("Strange collection type for field " + field.getName());
            }
            return (Class) genericTypes[0];

        } else {
            final Class type = field.getType();
            if (type == Byte.TYPE) {
                return Byte.class;
            }
            if (type == Short.TYPE) {
                return Short.class;
            }
            if (type == Integer.TYPE) {
                return Integer.class;
            }
            if (type == Long.TYPE) {
                return Long.class;
            }
            if (type == Float.TYPE) {
                return Float.class;
            }
            if (type == Double.TYPE) {
                return Double.class;
            }
            if (type == Boolean.TYPE) {
                return Boolean.class;
            }

            return type;
        }
    }

  

    public static boolean isNumField(final Field field) {
        final Class type = field.getType();
        if (type == Byte.TYPE || type == Byte.class) {
            return true;
        }
        if (type == Short.TYPE || type == Short.class) {
            return true;
        }
        if (type == Integer.TYPE || type == Integer.class) {
            return true;
        }
        if (type == Long.TYPE || type == Long.class) {
            return true;
        }
        if (type == Float.TYPE || type == Float.class) {
            return true;
        }
        if (type == Double.TYPE || type == Double.class) {
            return true;
        }
   
        return false;
    }

    public static boolean isTextField(final Field field) {
        final Class type = field.getType();
        if (type == javax.swing.JTextField.class) {
            return true;
        }
        
       if (type == javax.swing.JTextArea.class) {
            return true;
        }
        return false;
    }

    public static boolean isBooleanField(final Field field) {
        final Class type = field.getType();

        if (type == Boolean.TYPE || type == Boolean.class) {
            return true;
        }
        return false;
    }

      public static boolean isFileField(final Field field) { 
       return isThisField(field,File.class);
    }
         public static boolean isThisField(final Field field,Class clazz) {
        final Class type = field.getType();
        if (type == clazz) {
            return true;
        }
        return false;
    }




    public static ArrayList<String> getFildsNameArray(Class objClass) {
        ArrayList<String> al = null;
        try {
            //得到所有的属性
            Field[] fields = objClass.getFields();
            al = new ArrayList();
            for (int i = 0; i < fields.length; i++) {
                al.add(fields[i].getName());
            }
        } catch (Exception e) {
            al = null;
            //System.out.println(e);
        }
        return al;
    }

    public static ArrayList<String> getDeclareFildsNameArray(Class objClass) {
        ArrayList<String> al = null;
        try {
            //得到所有的属性
            Field[] fields = objClass.getDeclaredFields();
            al = new ArrayList();
            for (int i = 0; i < fields.length; i++) {
               // System.out.println(fields[i].getName()+"---------------fields[i].getName()-----------");
                al.add(fields[i].getName());
            }
        } catch (Exception e) {
            al = null;
            //System.out.println(e);
        }
        return al;
    }



    public static boolean containField(String fieldName, Class objClass) {
        ArrayList<String> fields = getFildsNameArray(objClass);
        if (fields.contains(fieldName)) {
            return true;
        } else {
            return false;
        }

    }

     public static boolean containDeclareField(String fieldName, Class objClass) {
        ArrayList<String> fields = getDeclareFildsNameArray(objClass);

        if (fields.contains(fieldName)) {
            return true;
        } else {
            return false;
        }

    }


    public static ArrayList<String> getMethodsNameArray(Class objClass) {
        ArrayList<String> al = null;
        try {
            //得到所有的属性
            Method[] methods = objClass.getMethods();
            al = new ArrayList();
            for (int i = 0; i < methods.length; i++) {
                al.add(methods[i].getName());
            }
        } catch (Exception e) {
            al = null;
            //System.out.println(e);
        }
        return al;
    }

    public static boolean containMethod(String method, Class objClass) {
        ArrayList<String> methods = getMethodsNameArray(objClass);
        if (methods.contains(method)) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] argv) throws NoSuchFieldException
    {
        RefelectUtil refu = new RefelectUtil();
      //System.out.println(  isBooleanField(org.broadinstitute.sting.gatk.GATKArgumentCollection.class.getField("filterZeroMappingQualityReads")));

    }
}
