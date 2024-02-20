/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Magic.WinMain;

import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

/**
 *
 * @author lenovo
 */
public class GlobalAWTEventListener implements AWTEventListener {

    private static GlobalAWTEventListener s_singleton = null;

    private GlobalAWTEventListener() {
    }

    public static GlobalAWTEventListener getInstance() {
        if (s_singleton == null) {
            s_singleton = new GlobalAWTEventListener();
        }
        return s_singleton;
    }

    public void eventDispatched(AWTEvent event) {
        processEvent(event);
    }

    private static void processEvent(final AWTEvent event) {

        if (event.getClass() == KeyEvent.class) {

            // 被处理的事件是键盘事件.
            KeyEvent keyEvent = (KeyEvent) event;

            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                  MagicFrame.instance.gotoPrevious(1);
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
              //  System.out.println("--------next-----");
                  MagicFrame.instance.gotoNext(1);
            }

        }
    }
}


