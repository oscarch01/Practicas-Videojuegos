import sys
import netifaces as ni
from PyQt5 import uic, QtWidgets


# Ruta que almacena la vista
qtCreatorFile = "views/client_view.ui"
Ui_MainWindow, QtBaseClass = uic.loadUiType(qtCreatorFile)

class client_view(QtWidgets.QMainWindow, Ui_MainWindow):
    # Constructor
    def __init__(self):
        QtWidgets.QMainWindow.__init__(self)
        Ui_MainWindow.__init__(self)
        self.setupUi(self)

        self.home_view=None

        self.btn_menu.clicked.connect(self.back_menu)
        self.txt_ip_c.setText(self.get_ip())
        self.btn_start.clicked.connect(self.start_connection)
        self.btn_stop.clicked.connect(self.stop_connection)
        
        
    # Función que retorna a la vista del menú principal
    def back_menu(self):
        print("Show menu view")
        self.close()
        self.home_view.show()

    # Función que obtiene la ip capturada del servidor
    def get_server_ip(self):
        ip = self.txt_ip_s.toPlainText()
        return ip
    
    # Función que obtiene el puerto capturado del servidor
    def get_server_port(self):
        port = self.txt_port_s.toPlainText()
        return port

    # Función que obtiene la ip real del cliente
    def get_ip(self):
        interface = ni.gateways()['default'][ni.AF_INET][1]
        ni.ifaddresses(interface)
        ip = ni.ifaddresses(interface)[ni.AF_INET][0]['addr']
        return str(ip)
    
    # Función que obtiene el puerto configurado para el cliente
    def get_port_c(self):
        port = self.txt_port.toPlainText()
        return port

    # Función que conecta al cliente con el servidor
    def start_connection(self):
        print('Server connection')
        server_ip = self.txt_ip_s.toPlainText()
        server_port = self.txt_port_s.toPlainText()
        client_ip = self.txt_ip_c.toPlainText()
        client_port = self.txt_port_c.toPlainText()

        self.txt_ip_s.setDisabled(True)
        self.txt_port_s.setDisabled(True)
        self.txt_ip_c.setDisabled(True)
        self.txt_port_c.setDisabled(True)
        self.btn_stop.setDisabled(False)
        self.btn_start.setDisabled(True)

        print('server ip: {}\nserver port: {}\nclient ip: {}\nclient port: {}',server_ip, server_port, client_ip, client_port)
    
    # Función que detiene el cliente
    def stop_connection(self):
        print('Stop client')

        self.txt_ip_s.setDisabled(False)
        self.txt_port_s.setDisabled(False)
        self.txt_ip_c.setDisabled(False)
        self.txt_port_c.setDisabled(False)
        self.btn_stop.setDisabled(True)
        self.btn_start.setDisabled(False)