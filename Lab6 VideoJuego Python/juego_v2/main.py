#!/usr/bin/env python3

import sys
import netifaces as ni
from PyQt5 import uic, QtWidgets
from views.home import home

# Clase principal
class main(QtWidgets.QMainWindow):
    def __init__(self):
        # Instancia QT5
        app =  QtWidgets.QApplication(sys.argv)
        # Crear instancia de ventana pricipal
        window = home()
        window.show()
        # Ejecutar modulos QT5
        sys.exit(app.exec_())

if __name__ == "__main__":
    main()
    
    