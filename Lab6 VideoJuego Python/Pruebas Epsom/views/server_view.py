import sys
import netifaces as ni
from PyQt5 import uic, QtWidgets



qtCreatorFile = "views/server_view.ui" # Nombre del archivo aquí.

Ui_MainWindow, QtBaseClass = uic.loadUiType(qtCreatorFile)

class server_view(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self):
        QtWidgets.QMainWindow.__init__(self)
        Ui_MainWindow.__init__(self)
        self.setupUi(self)

        self.home_view=None

        self.btn_menu.clicked.connect(self.back_menu)
        self.txt_ip.setText(self.get_ip())
        self.txt_port.setText(self.get_port())
        
        

    def back_menu(self):
        print("Show menu view")
        self.close()
        self.home_view.show()

    def get_ip(self):
        interface = ni.gateways()['default'][ni.AF_INET][1]
        ni.ifaddresses(interface)
        ip = ni.ifaddresses(interface)[ni.AF_INET][0]['addr']
        return str(ip)
    
    def get_port(self):
        return "5000"