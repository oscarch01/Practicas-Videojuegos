import socket
import pickle

class Red:
    def __init__(self,ip, puerto):
        self.cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.servidor = ip
        self.puerto = puerto
        self.direccion = (self.servidor,self.puerto)
        self.pk = self.conectar()

    def conectar(self):
        try:
            self.cliente.connect(self.direccion)
            return pickle.loads(self.cliente.recv(24000))
        except:
            pass

    def enviar(self, data):
        try:
            self.cliente.send(pickle.dumps(data))
            return pickle.loads(self.cliente.recv(24000))
        except socket.error as e:
            print(e)
    
    def obtenerPK(self):
        return self.pk