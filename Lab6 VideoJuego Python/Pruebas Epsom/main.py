#!/usr/bin/env python3

import netifaces as ni
import sys
from PyQt5 import uic, QtWidgets
from views.home import home


class main(QtWidgets.QMainWindow):
    def __init__(self):
        app =  QtWidgets.QApplication(sys.argv)
        window = home()
        window.show()
        sys.exit(app.exec_())

    
        
if __name__ == "__main__":
    main()
    
    