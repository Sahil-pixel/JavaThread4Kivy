# JavaThread4Kivy
Java Thread Implementation for KIvy Android 
```PYTHON
from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.label import Label
from kivy.uix.button import Button
from kivy.clock import Clock
from kivy.metrics import sp
from jnius import autoclass, PythonJavaClass, java_method
#from android.runnable import run_on_ui_thread
import threading
import time
TO=10000000
JavaThread=autoclass("org.kivy.JavaThread")

class JavaThreadCallback(PythonJavaClass):
    """Python implementation of Java Runnable for callback."""
    __javainterfaces__ = ['org/kivy/CallBackWrapper']
    __javacontext__ = 'app'

    def __init__(self, callback):
        #super().__init__()
        self.callback = callback

    @java_method('()V')
    def onCallback(self):
        if self.callback:
            self.callback()
        #print("java thread")
        #Clock.schedule_once(lambda dt: self.callback("Java Thread Completed!"), 0)

class MainLayout(BoxLayout):
    def __init__(self, **kwargs):
        super().__init__(orientation='vertical', **kwargs)

        self.label = Label(text="Testing JavaThread and Python Thread",size_hint=(1, 0.2),font_size=sp(20))
        self.label2=Label(text="Testing JavaThread and Python Thread",size_hint=(1, 0.5),)
        self.label2.bind(width=lambda instance, value: setattr(instance, 'text_size', (value, None)))


        self.add_widget(self.label)
        self.add_widget(self.label2)

        self.button = Button(text="Start Java Thread", size_hint=(1, 0.5))
        self.button.bind(on_press=self.start_java_thread)
        self.button2=Button(text="Start Python Thread", size_hint=(1, 0.5))
        self.button2.bind(on_press=self.start_python_thread)
        
        self.add_widget(self.button)
        self.add_widget(self.button2)
        #self.add_widget(widget)
    def start_python_thread(self,instance):
        threading.Thread(target=self.call_task).start()
    def call_task(self):
        print("Python Thread  running");
        st=time.time()
        j=0
        to=TO
        for i in range(0,to):
            j+=i
        print("sum the "+ str(to) +"number is = "+str(j))
        
        print("python","Python Thread completed");
        et=time.time()
        print("Time taking to complete python task in python thread  = ",abs(et-st))
        string="sum the "+ str(to) +" number is = "+str(j)+"\nTime python task in python thread  = "+str(abs(et-st))
        self.update_label(string)
    @mainthread
    def update_label(self,text):
        self.label2.text=text

    def start_java_thread(self, instance):    
        #print("start time of java thread is ",str(st))
        callback=JavaThreadCallback(self.callback)
        thread =JavaThread(callback)
        print(thread)
        thread.start()



    def callback(self, ):
        print("java Thread  running");
        st=time.time()
        j=0
        to=TO
        for i in range(0,to):
            j+=i
        print("sum the "+ str(to) +"number is = "+str(j))
        
        print("python","java Thread completed");
        et=time.time()
        print("Time taking to complete python task in java thread = ",abs(et-st))
        self.update_label("sum the "+ str(to) +" number is = "+str(j)+"\nTime python task in java thread  = "+str(abs(et-st)))


class MyApp(App):
    def build(self):
        return MainLayout()

if __name__ == "__main__":
    MyApp().run()

```
