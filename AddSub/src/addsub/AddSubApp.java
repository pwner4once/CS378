/*
 * AddSubApp.java
 */

package addsub;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class AddSubApp extends SingleFrameApplication {
    Class<?> c; 

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
    public Class queryClass(String str){
        try {
          c = Class.forName(str);
        } catch (Exception e) {
          return null;
        }

        return c;
    }
}
