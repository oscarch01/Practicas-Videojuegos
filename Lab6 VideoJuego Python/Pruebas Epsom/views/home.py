import sys
from PyQt5 import uic, QtWidgets
from views.server_view import server_view
sys.path.insert(1, 'src/img/misc/')
import uv_logo_rc

qtCreatorFile = "views/home.ui" # Nombre del archivo aquí.

Ui_MainWindow, QtBaseClass = uic.loadUiType(qtCreatorFile)

class home(QtWidgets.QMainWindow, Ui_MainWindow):
    def __init__(self):
        QtWidgets.QMainWindow.__init__(self)
        Ui_MainWindow.__init__(self)
        self.setupUi(self)
        self.btn_server.clicked.connect(self.server_view)
        self.btn_client.clicked.connect(self.client_view)
        self.server_view = server_view()
    
    # Server view
    def server_view(self):
        print("Show server view")
        self.close()
        self.server_view.home_view = self
        self.server_view.show()


    # Client view
    def client_view(self):
        print("Show client view")

