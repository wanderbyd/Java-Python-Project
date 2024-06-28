from tkinter import *

root=Tk()
root.title("My GUI")

btn1 = Button(root, text="button1")
btn1.pack()

btn2 = Button(root, padx=5, pady=10, text='button2')
btn2.pack()

btn3 = Button(root, padx=10, pady=5, text='button3')
btn3.pack()

btn4 = Button(root, width=10, pady=3, text='button4')
btn4.pack()

btn5 = Button(root, fg="red", bg="yellow", text='button4')
btn5.pack()

photo =PhotoImage(file="D:\pythonWorkspace\gui_project\img.png")
btn6= Button(root, image= photo)
btn6.pack()

btn7 = Button(root,text='동작하는 버튼',command='btncmd동작중')#안됨
btn7.pack()

root.mainloop()
