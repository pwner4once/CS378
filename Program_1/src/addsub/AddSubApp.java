/*
 * AddSubApp.java
 */

package addsub;

import java.lang.reflect.*;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AddSubApp extends SingleFrameApplication {
    static String className = "";
    static String ct_text = "";
    static String fields_text = "";
    static String methods_text = "";
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new AddSubView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of AddSubApp
     */
    public static AddSubApp getApplication() {
        return Application.getInstance(AddSubApp.class);
    }

    /**
     * Main method launching the application.
     *
     * 1) Output to command line if arguments exists
     * 2) Else launch the gui
     */
    public static void main(String[] args) {
      if (args.length > 0){

        String cls = args[0];
        setClassName(cls);
        System.out.println("class " + cls);
        System.out.println("Constructors");
        System.out.println(getConstructorsText());
        System.out.println("Fields");
        System.out.println(getFieldsText());
        System.out.println("Methods");
        System.out.print(getMethodsText());
      } else {
        launch(AddSubApp.class, args);
      }

    }

    /**
     * setter for name of the current class
     * @param s
     */
    public static void setClassName(String s){
        className = s;
        // set corresponding outputs when differnet radio buttons are pushed
        setDisplayFields();
    }

    /**
     * propagate changes made to the current 'class'
     * ct_text - displayed when Constructors radio button is checked
     * fields_text - displayed when Fields radio button is checked
     * methods_text - displayed when Methods radio button is checked
     */
    public static void setDisplayFields(){
      try {
        Class m_class = Class.forName(className);
        ArrayList<String> list = new ArrayList<String>();
        Method m[] = m_class.getDeclaredMethods();
        Constructor cons[] = m_class.getDeclaredConstructors();
        Field f[] = m_class.getDeclaredFields();

        /* constructing textbox display content  for Constructors,
           Fields, and Methods */
        for (int cons_num = 0; cons_num < cons.length; cons_num++) {
          Constructor constructor = cons[cons_num];
          ct_text = constructor.getName().substring(constructor.getName().lastIndexOf(".")+1);
          ct_text += "( ";
          Class pvec[] = constructor.getParameterTypes();
          for (int j = 0; j < pvec.length; j++) {
            if (j == 0) ct_text += pvec[j].getName();
            else ct_text += ", " + pvec[j].getName();
          }
          ct_text += " ) ";
          if(Modifier.isPrivate(m_class.getModifiers())) ct_text += "private";
          else if (Modifier.isPublic(m_class.getModifiers())) ct_text += "public";
          ct_text += "\n";
          list.add(ct_text);
        }

        Collections.sort(list);
        ct_text = "";
        for (int j = 0; j < list.size(); j++) {
          ct_text += list.get(j);
        }

        list.clear();
        for (int i = 0; i < f.length; i++) {
          Field field = f[i];
          fields_text = field.getName() + " : " + field.getType();
          if (Modifier.isProtected(field.getModifiers())){ /* nothing */ }
          if (Modifier.isStatic(field.getModifiers())){ fields_text += " static";}
          if (Modifier.isPublic(field.getModifiers())){ fields_text += " public";}
          if (Modifier.isPrivate(field.getModifiers())){ fields_text += " private"; }
          fields_text += "\n";
          list.add(fields_text);
        }

        Collections.sort(list);
        fields_text = "";
        for (int j = 0; j < list.size(); j++) {
          fields_text += list.get(j);
        }

        list.clear();
        for (int k = 0; k < m.length; k++) {
          Method method = m[k];
          methods_text = method.getName() + "( ";
          Class pvec[] = method.getParameterTypes();
          for (int j = 0; j < pvec.length; j++) {
            String name = decode(pvec[j].getName());
            if (j == 0) methods_text += name;
            else methods_text += ", " + name;
          }
          methods_text += " ) : " + decode(method.getReturnType().getName());
          if (Modifier.isProtected(method.getModifiers())){ /* nothing */ }
          if (Modifier.isStatic(method.getModifiers())){ methods_text += " static";}
          if (Modifier.isPublic(method.getModifiers())){ methods_text += " public";}
          if (Modifier.isPrivate(method.getModifiers())){ methods_text += " private";}
          methods_text += "\n";
          list.add(methods_text);
        }

        Collections.sort(list);
        methods_text = "";
        for (int j = 0; j < list.size(); j++) {
          methods_text += list.get(j);
        }
      } catch (Exception e) {
        if (className.length() == 0){
          ct_text = "";
          fields_text = "";
          methods_text = "";
        }else {
          ct_text = "Unable to open " + className;
          fields_text = "Unable to open " + className;
          methods_text = "Unable to open " + className;
        }

      }
    }
    /**
     * getter for name of the current class
     * @param s
     * @return
     */
    public static String getClassName(){
        return className;
    }

    /**
     * return the content to display when 'Constructor' is selected
     * @return string
     */
    public static String getConstructorsText(){
      return ct_text;
    }

    /**
     * return the content to display when 'Fields' is selected
     * @return String
     */
    public static String getFieldsText(){
      return fields_text;
    }

    /**
     * return contents to display when 'Methods' is selected
     * @return
     */
    public static String getMethodsText(){
      return methods_text;
    }

    /**
     * decode foolish encoding by getName()
     */
    public static String decode(String str){
      int strPos = 0;
      String encodings[]= {"[Z", "[B", "[C", "[L", "[D", "[F", "[I", "[J", "[S"};
      String decodings[] = {"boolean[", "byte[", "char[", "", "double[", "float[", "int[", "long[", "short["};
      StringBuffer strBuf;
      for (int i = 0; i < encodings.length; i++) {

        while ( (strPos = str.indexOf(encodings[i])) != -1){
          if (!encodings[i].equals("[L")){
            strBuf = new StringBuffer(str);
            strBuf.insert(str.indexOf("["), decodings[i]).toString();
            strBuf.delete(strPos+decodings[i].length(), strPos+decodings[i].length()+encodings[i].length());
            str = strBuf.toString();
          }else{
            strBuf = new StringBuffer(str);
            int end = str.indexOf(";", strPos);
            int start = str.lastIndexOf(".", end);
            strBuf.insert(strPos, str.substring(start+1, end)+"[");
            strBuf.delete(strPos+end-start, strPos+end-start+encodings[i].length());
          }
          str = strBuf.toString();
        }
     }
     if (str.length() >= 0) str = str.replaceAll(Pattern.quote("["), "[]");
     return str;
    }
}

