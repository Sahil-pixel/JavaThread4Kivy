package org.kivy;

import android.util.Log;
import org.kivy.CallBackWrapper;

public class JavaThread extends Thread {
    private CallBackWrapper callback;

    public JavaThread(CallBackWrapper pythoncallback) {
        this.callback = pythoncallback;
    }

    @Override
    public void run() {
                
            Log.d("python", "Java Thread");
            if (callback != null) {
               callback.onCallback();  // Call Python callback (through a Java interface)
        }   
       
    
}
}

