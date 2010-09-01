/*
 * AddSubApp.java
 */

package addsub;

import java.lang.reflect.*;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AddSubApp extends SingleFrameApplication {
    static String className = "";
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
     */
    public static void main(String[] args) {
        launch(AddSubApp.class, args);
    }

    /**
     * Query contents of a Class
     */
    public static Class queryClass(String str){
      Class m_class;

      try {
        m_class = Class.forName(str);
      } catch (Exception e) {
        m_class = null;
      }
      return m_class;
    }

    public static void setClassName(String s){
        className = s;
    }
    public static String getClassName(String s){
        return className;
    }
}
