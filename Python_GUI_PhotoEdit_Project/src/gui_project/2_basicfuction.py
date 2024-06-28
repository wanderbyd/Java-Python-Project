from tkinter import *
import tkinter.ttk as ttk
import tkinter.messagebox as msgbox
from tkinter import filedialog
# from click.decorators import command
root=Tk()
root.title("My GUI")

#파일추가
def add_file():
    files = filedialog.askopenfilenames(title="Select Images",\
        filetypes=(("PNG File","*.png"),("All File","*.*")),\
        initialdir=r"C:\Users\vsc04\Pictures\1주차") 
    #최초에 사용자가 지정한 경로를 보여줌(C:/) 
    
    #사용자가 선택한 파일 목록
    for file in files:
        list_file.insert(END,file)
        
#선택삭제  
def del_file():
    print(list_file.curselection())
    
    for index in reversed(list_file.curselection()):
        list_file.delete(index)

#저장경로
def browse_dest_path():
    folder_selected = filedialog.askdirectory()
    if folder_selected is None:
        return 
    print(folder_selected)
    txt_dest_path.delete(0, END)
    txt_dest_path.insert(0, folder_selected)
    
def start():
    #각 옵션들 값을 확인
    print("Width", cmb_width.get())
    print("Space", cmb_space.get())
    print("Format", cmb_format.get())
    
    #파일 목록 확인
    if list_file.size() == 0:
        msgbox.showwarning("Warning", "Please add your images")
        return
    
    #저장경로 확인
    if len(txt_dest_path.get()) == 0:
        msgbox.showwarning("Warning", "Select Save Location")

#파일 프레임(파일 추가, 선택삭제)
file_frame= Frame(root)
file_frame.pack(fill="x",padx=5,pady=5)

btn_add_file = Button(file_frame, padx=5,width=12,text="Add File",command=add_file)
btn_add_file.pack(side="left")

btn_del_file= Button(file_frame, padx=5,width=12,text="Select Delete",command=del_file)
btn_del_file.pack(side="right")


#리스트 프레임
list_frame = Frame(root)
list_frame.pack(fill="both",padx=5,pady=5) 

scrollbar= Scrollbar(list_frame)
scrollbar.pack(side="right",fill="y")

list_file= Listbox(list_frame,selectmode="extended",height=15, yscrollcommand=scrollbar.set)
list_file.pack(side="left", fill="both", expand=True)
scrollbar.config(command=list_file.yview)

#저장경로 프레임
path_frame = LabelFrame(root, text="Save Location")
path_frame.pack(fill="x",padx=5,pady=5,ipady=5)

txt_dest_path= Entry(path_frame)
txt_dest_path.pack(side="left", fill="x", expand=True,padx=5,pady=5, ipady=4)#높이 변경

btn_dest_path= Button(path_frame, text="Search", width=0, command=browse_dest_path)
btn_dest_path.pack(side="right",padx=5,pady=5)

#옵션프레임
frame_option= LabelFrame(root,text="Option")
frame_option.pack(padx=5,pady=5,ipady=5)

#1. 가로 넓이 옵션
#가로 넓이 레이블
lbl_width =Label(frame_option,text="Width", width=5)
lbl_width.pack(side="left",padx=5,pady=5)

#가로 넓이 콤보
opt_width=["Original","1024","800","640"]
cmb_width = ttk.Combobox(frame_option, state="readonly",values=opt_width, width=6)
cmb_width.current(0)
cmb_width.pack(side="left",padx=5,pady=5)


#2. 간격 옵션
#간격 옵션 레이블
lbl_space =Label(frame_option,text="Space", width=5)
lbl_space.pack(side="left",padx=5,pady=5)

#간격 옵션 콤보
opt_space=["None","Narrow","Normal","Wide"]
cmb_space = ttk.Combobox(frame_option, state="readonly",values=opt_space, width=6)
cmb_space.current(0)
cmb_space.pack(side="left",padx=5,pady=5)


#3.파일포맷 옵션
#파일 포맷 옵션 레이블
lbl_format =Label(frame_option,text="Format", width=5)
lbl_format.pack(side="left",padx=5,pady=5)

#파일 포맷 옵션 콤보
opt_format=["PNG","JPG","BMP"]
cmb_format = ttk.Combobox(frame_option, state="readonly",values=opt_format, width=6)
cmb_format.current(0)
cmb_format.pack(side="left",padx=5,pady=5)

#진행 상황 progress bar
frame_progress= LabelFrame(root,text="Progressing")
frame_progress.pack(fill="x",padx=5,pady=5,ipady=5)

p_var = DoubleVar()
progress_bar = ttk.Progressbar(frame_progress, maximum=100, variable=p_var)
progress_bar.pack(fill="x",padx=5,pady=5)


#실행프레임
frame_run=Frame(root)
frame_run.pack(fill="x",padx=5,pady=5)

btn_close = Button(frame_run, padx=5, pady=5, text="Close", width=12,command=root.quit)
btn_close.pack(side="right",padx=5,pady=5)

btn_start = Button(frame_run, padx=5, pady=5, text="Start", width=12, command=start)
btn_start.pack(side="right",padx=5,pady=5)


root.resizable(False, False)
root.mainloop()