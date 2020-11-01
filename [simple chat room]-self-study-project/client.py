import socket
import sys
from  PyQt5.Qt import *
from PyQt5.QtCore import QThread


class ChatRoom(QMainWindow):
    def __init__(self,*args,**kwargs):
        super(ChatRoom, self).__init__(*args,**kwargs)
        self.setWindowTitle("Chat Room")
        self.setGeometry(0,0,360,640)
        self.setFixedSize(360,640)
        self.setStyleSheet("background-color:#2C347C")
        self.row=1
        self.join_layout()
        self.HEADER = 64
        self.PORT = 5050#port=============================
        self.FORMAT = 'utf-8'
        self.DISCONNECT_MESSAGE = "!DISCONNECT"



    def send(self,msg):
        message = msg.encode(self.FORMAT)
        msg_length = len(message)
        send_length = str(msg_length).encode(self.FORMAT)
        send_length += b' ' * (self.HEADER - len(send_length))
        self.client.send(send_length)
        self.client.send(message)





    def join_layout(self):
        self.entryLabel=QLabel("Simple chat room project",self)
        self.entryLabel.setStyleSheet("""QLabel{
                                                background:transparent;
                                                color:white;
                                                font-size:18px;
                                                font-family:RNS Miles;
                                                min-width:190px;
                                                min-height:22px;
                                                font-weight:bold;
                                                }""")
        self.entryLabel.move(80,86)

        self.joinFrame=QFrame(self)
        self.joinFrame.setStyleSheet("""QFrame{
                                                background-color:white;
                                                border-radius:23px;
                                                min-height:284;
                                                min-width:284;
                                                }""")
        self.joinFrame.move(38,169)

        self.joinLabel=QLabel("Join the chat room",self)
        self.joinLabel.setStyleSheet("""QLabel{
                                                background:transparent;
                                                color:#2C347C;
                                                font-size:18px;
                                                min-width:142px;
                                                min-height:22px;
                                                font-family:RNS Miles;
                                                font-weight:bold;
                                                }""")
        self.joinLabel.move(110,204)

        self.enterName=QLabel("Enter your name",self)
        self.enterName.setStyleSheet("""QLabel{
                                                background:transparent;
                                                color:#2C347C;
                                                font-size:16px;
                                                min-width:111px;
                                                min-height:19px;
                                                font-family:RNS Miles;
                                                font-weight:bold;
                                                }""")
        self.enterName.move(72,265)

        self.entryLine=QLabel(self)
        self.entryLine.setStyleSheet("""QLabel{
                                                background:#2C347C;
                                                min-width:200px;
                                                max-height:1px;
                                                }""")
        self.entryLine.move(73,312)

        self.nameInput=QLineEdit(self)
        self.nameInput.setStyleSheet("""QLineEdit{
                                                    background:transparent;
                                                    color:#2C347C;
                                                    font-size:16px;
                                                    min-width:200px;
                                                    min-height:19px;
                                                    font-family:RNS Miles;
                                                    font-weight:bold;
                                                    border-style:hidden;
                                                    }""")
        self.nameInput.move(100,288)





        self.enterCode=QLabel("Enter Code",self)
        self.enterCode.setStyleSheet("""QLabel{
                                                background:transparent;
                                                color:#2C347C;
                                                font-size:16px;
                                                min-width:111px;
                                                min-height:19px;
                                                font-family:RNS Miles;
                                                font-weight:bold;
                                                }""")
        self.enterCode.move(72,326)

        self.entryCodeLine=QLabel(self)
        self.entryCodeLine.setStyleSheet("""QLabel{
                                                background:#2C347C;
                                                min-width:200px;
                                                max-height:1px;
                                                }""")
        self.entryCodeLine.move(73,373)

        self.codeInput=QLineEdit(self)
        self.codeInput.setStyleSheet("""QLineEdit{
                                                    background:transparent;
                                                    color:#2C347C;
                                                    font-size:16px;
                                                    min-width:200px;
                                                    min-height:19px;
                                                    font-family:RNS Miles;
                                                    font-weight:bold;
                                                    border-style:hidden;
                                                    }""")
        self.codeInput.move(100,348)
        self.codeInput.returnPressed.connect(self.join)





        self.joinButton=QPushButton("Join",self)
        self.joinButton.setStyleSheet("""QPushButton{
                                                background-color:#2C347C;
                                                color:white;
                                                font-size:16px;
                                                font-family:RNS Miles;
                                                border-radius:8px;
                                                min-height:34px;
                                                min-width:117px;
                                                font-weight:bold;  
                                                }""")
        self.joinButton.move(115,399)
        self.joinButton.clicked.connect(self.join)





    def disable_join_layout(self):
        self.joinFrame.setVisible(False)
        self.joinLabel.setVisible(False)
        self.enterName.setVisible(False)
        self.joinButton.setVisible(False)
        self.nameInput.setVisible(False)
        self.entryLine.setVisible(False)
        self.enterCode.setVisible(False)
        self.codeInput.setVisible(False)
        self.entryCodeLine.setVisible(False)
        self.entryLabel.setVisible(False)




    def join(self):
        try:
            self.name=self.nameInput.text()
            self.SERVER=self.codeInput.text()
            self.ADDR = (self.SERVER, self.PORT)

            #self.client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)===================================for ip version 4==================================

            self.client = socket.socket(socket.AF_INET6, socket.SOCK_STREAM)#=================================for ip version 6=====================================
            self.client.connect(self.ADDR)

            self.send(self.name)#==================================================================================
            self.recv=Worker(Object=chatroom)
            self.recv.start()
            self.recv.recvSignal.connect(self.chat_scroll)

            self.disable_join_layout()
            self.chat_Layout()
        except:
            print("[Server not found]")






    def chat_Layout(self):
        entryLabel=QLabel("Simple chat room project",self)
        entryLabel.setStyleSheet("""QLabel{
                                                background:transparent;
                                                color:white;
                                                font-size:18px;
                                                font-family:RNS Miles;
                                                min-width:190px;
                                                min-height:22px;
                                                font-weight:bold;
                                                }""")
        entryLabel.move(20,21)
        entryLabel.setVisible(True)

        self.exitButton= QPushButton("EXIT",self)
        self.exitButton.setStyleSheet("""QPushButton{
                                                background-color:#F21313;
                                                color:white;
                                                font-size:16px;
                                                font-family:RNS Miles;
                                                border-radius:3px;
                                                min-height:22px;
                                                min-width:67px;
                                                max-width:67px;
                                                font-weight:bold;  
                                                }
                                        QPushButton:pressed{
                                                background-color:#eb4949;
                                                }""")
        self.exitButton.move(269,25)
        self.exitButton.clicked.connect(self.close_app)
        self.exitButton.setVisible(True)

        self.textBox=QFrame(self)
        self.textBox.setStyleSheet("""QFrame{
                                            background:#C4C4C4;
                                            min-height:63;
                                            min-width:360;  
                                            }""")
        self.textBox.move(0,577)
        self.textBox.setVisible(True)

        self.messageEntry=QLineEdit(self)
        self.messageEntry.setStyleSheet("""QLineEdit{
                                                    background:white;
                                                    color:black;
                                                    font-size:16px;
                                                    min-width:280px;
                                                    min-height:35px;
                                                    font-family:RNS Miles;
                                                    font-weight:bold;
                                                    border-radius:20px;
                                                    border-style:hidden;
                                                    }""")
        self.messageEntry.move(31,591)
        self.messageEntry.returnPressed.connect(self.chat_scroll)


        self.messageEntry.setVisible(True)

        self.sendButton=QPushButton(self)
        self.sendButton.setIcon(QIcon("send_button.png"))
        self.sendButton.setStyleSheet("""QPushButton{ background:transparent;
                                                        max-width:25px;
                                                        max-width:25px;
                                                    }""")
        self.sendButton.move(323,596)
        self.sendButton.clicked.connect(self.chat_scroll)

        self.sendButton.setVisible(True)

        self.main_container = QWidget(self)
        self.main_container.move(0, 63)
        self.main_container.resize(360, 514)
        self.main_container.setStyleSheet("background-color:transparent;")
        self.main_container.setVisible(True)
        self.main_formLayout = QGridLayout()
        self.main_groupBox = QGroupBox()


        self.main_groupBox.setStyleSheet("""QGroupBox { 
                                            border: None;
                                            color:white;
                                            font-size:13px;}""")
        self.main_scroll = QScrollArea()
        self.main_scroll.setStyleSheet("""  QScrollBar:vertical
                                                    {
                                                        background-color: #2F2D2D;
                                                        width: 10px;
                                                        margin: 15px 3px 15px 3px;
                                                        border: 1px transparent #2A2929;
                                                        border-radius: 4px;
                                                    }

                                             QScrollBar::handle:vertical
                                                    {
                                                        background-color: white;         
                                                        min-height: 1px;
                                                        border-radius: 1px;
                                                    } 
                                            QScrollBar::add-line:vertical
                                                    {
                                                        subcontrol-position: bottom;                                           

                                                    }   
                                            QScrollBar::sub-line:vertical
                                                    {  
                                                        height: 0px;
                                                        width: 0px;
                                                        subcontrol-position: top;
                                                        subcontrol-origin: margin;
                                                    }
                                            QScrollBar::add-page:vertical, QScrollBar::sub-page:vertical
                                                    {
                                                    background: none;
                                                    } """)

        self.main_layout = QVBoxLayout(self.main_container)









    def chat_scroll(self,text=None):
            if text:
                self.chatList=QLabel(self)
                message,growth=self.format_message(text)
            else:
                self.chatList=QLabel(self)
                message,growth=self.format_message(self.messageEntry.text())
                if message is not None:self.send(message)
            if message is None:
                pass
            else:
                self.chatList.setText(message)
                self.chatList.setStyleSheet(f"""QLabel{{
                                                            background:white;
                                                            border-radius:5;
                                                            color:black;
                                                            font-size:17px;
                                                            min-height:{growth} px;
                                                            min-width:310 px;
                                                            max-height:{growth} px;                                                            
                                                            }}""")
                self.main_formLayout.addWidget(self.chatList,self.row,1)
                self.main_formLayout.setAlignment(Qt.AlignTop)
                self.main_formLayout.setSpacing(20)
                self.main_groupBox.setLayout(self.main_formLayout)
                self.main_scroll.setFrameStyle(QFrame.NoFrame)
                self.main_scroll.setWidget(self.main_groupBox)
                self.main_scroll.setAlignment(Qt.AlignRight)
                self.main_scroll.setWidgetResizable(True)
                self.main_scroll.setHorizontalScrollBarPolicy(Qt.ScrollBarAlwaysOff)
                vbar=self.main_scroll.verticalScrollBar()
                vbar.setValue(vbar.maximum())
                self.main_layout.addWidget(self.main_scroll)
                self.chatList.setVisible(True)
                self.row+=1
                self.messageEntry.setText("")








    def format_message(self,message):
        if message=="":
            return None,None
        limit=36
        growth=50
        message=list(message)

        for i in range(len(message)//limit):
            if message[limit]==" ":
                message.insert(limit,"\n")
                limit+=36
                growth+=14*2
            else:
                iterator=0
                while True:
                    if message[limit+iterator]==" ":
                        message.insert(limit+iterator,"\n")
                        limit+=36
                        growth+=14*2
                        break
                    iterator+=1
        return "".join(message).strip(),growth






    def close_app(self):
        self.send(self.DISCONNECT_MESSAGE)
        self.close()








class Worker(QThread):
    recvSignal=pyqtSignal(str)
    def __init__(self, Object,parent=None):
        QThread.__init__(self,parent)
        self.Object=Object

    def run(self):
        connected = True
        while connected:
            msg_length =  self.Object.client.recv( self.Object.HEADER).decode( self.Object.FORMAT)
            if msg_length:
                msg_length = int(msg_length)
                msg =  self.Object.client.recv(msg_length).decode( self.Object.FORMAT)
                if msg ==  self.Object.DISCONNECT_MESSAGE:
                    connected = False
                self.recvSignal.emit(msg)

        self.Object.client.close()





app = QApplication(sys.argv)
chatroom=ChatRoom()
chatroom.show()
app.exec_()




