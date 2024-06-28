from tkinter import *

root=Tk()
root.title("My GUI")
root.geometry("640x480")

def create_new_file():
    print("새 파일을 만듭니다.")

menu = Menu(root)

#File메뉴
menu_file= Menu(menu, tearoff=0)
menu_file.add_command(labe="New File",command=create_new_file)
menu_file.add_command(label="New Window")
menu_file.add_separator()
menu_file.add_command(label="Open File...")
menu_file.add_separator()
menu_file.add_command(label="Save All",state="disable")
menu_file.add_separator()
menu_file.add_command(label="Exit",command =root.quit)
menu.add_cascade(label="File",menu=menu_file)

#Edit 메뉴 (빈값)
menu.add_cascade(label="Edit")

#language 메뉴 추가 (radio button 통해서 택1)
menu_lang= Menu(menu, tearoff=0)
menu_lang.add_radiobutton(label="Python")
menu_lang.add_radiobutton(label="java")
menu_lang.add_radiobutton(label="C++")
menu.add_cascade(label="Language",menu= menu_lang)

#체크박스 뷰메뉴
menu_view =Menu(menu, tearoff=0)
menu_view.add_checkbutton(label="show Minimap")
menu.add_cascade(label="view", menu= menu_view)


root.config(menu=menu)
root.mainloop()