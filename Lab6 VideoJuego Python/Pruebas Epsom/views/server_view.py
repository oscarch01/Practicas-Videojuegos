import sys
import netifaces as ni
from PyQt5 import uic, QtWidgets


# Ruta que almacena la vista
qtCreatorFile = "views/server_view.ui"
Ui_MainWindow, QtBaseClass = uic.loadUiType(qtCreatorFile)


class server_view(QtWidgets.QMainWindow, Ui_MainWindow):
    # Constructor
    def __init__(self):
        QtWidgets.QMainWindow.__init__(self)
        Ui_MainWindow.__init__(self)
        self.setupUi(self)

        self.home_view=None

        self.btn_menu.clicked.connect(self.back_menu)
        self.txt_ip.setText(self.get_ip())
        self.btn_start.clicked.connect(self.start_server)
        self.btn_stop.clicked.connect(self.stop_server)
        
        
    # Función que muestra la ventana del menú principal
    def back_menu(self):
        print("Show menu view")
        self.close()
        self.home_view.show()

    # Función que retorna la ip real del host
    def get_ip(self):
        interface = ni.gateways()['default'][ni.AF_INET][1]
        ni.ifaddresses(interface)
        ip = ni.ifaddresses(interface)[ni.AF_INET][0]['addr']
        return str(ip)
    
    # Función que retorna el puerto
    def get_port(self):
        port = self.txt_port.toPlainText()
        return port

    # Función que inicia el servidor
    def start_server(self):
        print('Start server')

        self.txt_ip.setDisabled(True)
        self.txt_port.setDisabled(True)
        self.btn_stop.setDisabled(False)
        self.btn_start.setDisabled(True)

        server_ip = self.txt_ip.toPlainText()
        server_port = self.txt_port.toPlainText()
    
    # Función que detiene el servidor
    def stop_server(self):
        print('Stop server')

        self.txt_ip.setDisabled(False)
        self.txt_port.setDisabled(False)
        self.btn_stop.setDisabled(True)
        self.btn_start.setDisabled(False)

