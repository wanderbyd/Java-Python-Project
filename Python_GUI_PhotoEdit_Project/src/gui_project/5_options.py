import os
from tkinter import *
import tkinter.ttk as ttk
import tkinter.messagebox as msgbox
from tkinter import filedialog
from PIL import Image


# from click.decorators import command
root=Tk()
root.title("My GUI")

#파일추가
def add_file():
    files = filedialog.askopenfilenames(title="Select Images",\
        filetypes=(("PNG File","*.png"),("All File","*.*")),\
        initialdir=r"D:\pythonWorkspace\gui_project\src\gui_project") 
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
    if folder_selected == '':
        print("Cancel")
        return 
    #print(folder_selected)
    txt_dest_path.delete(0, END)
    txt_dest_path.insert(0, folder_selected)
   
#이미지 통합
def merge_image():
    # print("Width:", cmb_width.get())
    # print("Space:", cmb_space.get())
    # print("Format:", cmb_format.get())
    
    try:
        #가로 넓이 
        img_width = cmb_width.get()
        if img_width == "Original":
            img_width = -1
        else:
            img_width = int(img_width)
            
        #간격
        img_space = cmb_space.get()
        if img_space == "Narrow":
            img_space = 30
        elif img_space == "Normal":
            img_space = 60
        elif img_space == "Wide":
            img_space = 90
        else:
            img_space =0
        
        img_format = cmb_format.get().lower()
        #############################################
        
        images = [Image.open(x) for x in list_file.get(0, END)]
        
        image_sizes=[]
        if img_width > -1:
            image_sizes = [(int(img_width), int(img_width * x.size[1]/ x.size[0]))for x in images]
        else:
            image_sizes = [(x.size[0], x.size[1]) for x in images]
            
        widths, heights = zip(*(image_sizes))
        
        # print("Width :", widths)
        # print("Heights :", heights)
        max_width, total_height= max(widths), sum(heights)
    
        #스케치북 준비
        if img_space > 0: #이미지 간격 옵션 적용
            total_height += (img_space*(len(images)-1))
            
        result_img = Image.new("RGB", (max_width, total_height),(255,255,255))
        y_offset= 0
    
        for idx, img in enumerate(images):
            #width가 원본유가 아닐때 이미지 크기 조정
            if img_width > -1:
                img = img.resize(image_sizes[idx])
                
            result_img.paste(img,(0 , y_offset))
            y_offset += (img.size[1] + img_space) #height 값 + 사용자가 지정한 간격
            
            progress = (idx +1) /len(images) *100 #실제 퍼센트 정보를 계산
            p_var.set(progress)
            progress_bar.update()
            
        #포맷 옵션 처리
        file_name="my_photo." + img_format
        dest_path = os.path.join(txt_dest_path.get(),file_name)
        result_img.save(dest_path)
        msgbox.showinfo("Alert", "Congratulations!")
    
    except Exception as err:
        msgbox.showerror("Error", err)
    
    
def start():
    #파일 목록 확인
    if list_file.size() == 0:
        msgbox.showwarning("Warning", "Please add your images")
        return
    
    #저장경로 확인
    if len(txt_dest_path.get()) == 0:
        msgbox.showwarning("Warning", "Select Save Location")
        return
    
    merge_image()

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