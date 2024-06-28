from tkinter import *

root=Tk()
root.title("My GUI")

label1= Label(root, text="Hello")
label1.pack()

photo =PhotoImage(file="D:\pythonWorkspace\gui_project\img.png")
label2= Label(root, image= photo)
label2.pack()

def change():
    label1.config(text="see you again")
    
    global photo2
    photo2 =PhotoImage(file="D:\pythonWorkspace\gui_project\img2.png")
    label2.config(image=photo2)

btn = Button(root,text="click", command=change)
btn.pack()
root.mainloop()
