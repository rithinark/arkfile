from tkinter import *
from functools import partial
class Main(Frame):
    def __init__(self,master=None):
        Frame.__init__(self,master)
        self.login()
        self.config(bg="#363838")
        self.pack()
    def on_entry(self,entry,text,event=None):
        if entry.get() ==text:
            entry.delete(0,"end")
            entry.insert(0,'')
            entry.config(fg='black',show="*") if text=="password" else entry.config(fg='black')

    def on_exit(self,entry,text,event=None):
        if entry.get()=="":
            entry.insert(0,text)
            entry.config(fg="#969799",show="")
    def gatherData(self):
        # assume data base
        print(self.username.get(),self.password.get())
        #delete entries
        self.username.delete(0,"end")
        self.password.delete(0,"end")
        self.on_exit(self.username,"username")
        self.on_exit(self.password,"password")

    def login(self):
        #-----------------userEntry----------------------------
        self.username=Entry(self,text="hello",fg="#969799")
        self.username.insert(0,"username")
        self.username.grid(row=1,column=2)

        #------------------passwordEntry------------------------
        self.password=Entry(self,fg="#969799")
        self.password.insert(0,"password")
        self.password.grid(row=2,column=2,pady=10)

        #--------------------removing and adding default text------------------------
        self.password.bind('<FocusIn>', partial(self.on_entry,self.password,"password"))
        self.password.bind('<FocusOut>', partial(self.on_exit,self.password,"password"))

        self.username.bind("<FocusIn>",partial(self.on_entry,self.username,"username"))
        self.username.bind("<FocusOut>",partial(self.on_exit,self.username,"username"))

        #---------------------username icon-------------------------
        self.usericon=PhotoImage(file="user.png")
        self.usericonbuild=Label(self,image=self.usericon,bd=0)
        self.usericonbuild.grid(row=1,column=1)

        #----------------------pwd icon-----------------------------
        self.pwdicon=PhotoImage(file="pwdicon.png")
        self.pwdiconbuild=Label(self,image=self.pwdicon,bd=0)
        self.pwdiconbuild.grid(row=2,column=1)

        #----------------------------loginButton------------------------------------
        self.loginicon=PhotoImage(file="loginicon.png")
        self.loginButton=Button(self,text="Login",image=self.loginicon,height=22,width=119,relief=FLAT,bd=0,bg="#363838",activebackground="#363838",command=self.gatherData)
        self.loginButton.grid(row=3,column=1,columnspan=2,pady=15)

        #-------------------------------loginLabel---------------------------------
        Login=Label(self,text="Login",font=("RNS Miles bold",16),fg="#FFC700",bg="#363838")
        Login.grid(row=0,column=1,columnspan=2,pady=10)

#objects...
root=Tk()
app=Main(master=root)
root.title("Login Page")
root.geometry("300x180")
root.config(bg="#363838")
root.resizable(0,0)
app.mainloop()
