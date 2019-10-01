import socket
from _thread import *
from core.avatar import Avatar
import pickle

class Servidor:
    def __init__(self, ip, puerto):
        self.ip = ip
        self.puerto = puerto
        self.avataresStrData = [
            "0" + "|" + "0" + "|" + "500" + "|" + "0" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "1" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "2" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "3" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "4" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "5" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "6" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "7" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "8" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "9" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "10" + "|" + "0" + "|" + "F",
            "0" + "|" + "0" + "|" + "500" + "|" + "11" + "|" + "0" + "|" + "F",
        ]
        #self.avatares_viejo = [
        #    Avatar(0,500,["core/img/jugador0/walkFront003.png","core/img/jugador0/walkFront004.png","core/img/jugador0/walkFront005.png","core/img/jugador0/idle.png","core/img/jugador0/walkFront000.png","core/img/jugador0/walkFront001.png","core/img/jugador0/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador1/walkFront003.png","core/img/jugador1/walkFront004.png","core/img/jugador1/walkFront005.png","core/img/jugador1/idle.png","core/img/jugador1/walkFront000.png","core/img/jugador1/walkFront001.png","core/img/jugador1/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador2/walkFront003.png","core/img/jugador2/walkFront004.png","core/img/jugador2/walkFront005.png","core/img/jugador2/idle.png","core/img/jugador2/walkFront000.png","core/img/jugador2/walkFront001.png","core/img/jugador2/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador3/walkFront003.png","core/img/jugador3/walkFront004.png","core/img/jugador3/walkFront005.png","core/img/jugador3/idle.png","core/img/jugador3/walkFront000.png","core/img/jugador3/walkFront001.png","core/img/jugador3/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador4/walkFront003.png","core/img/jugador4/walkFront004.png","core/img/jugador4/walkFront005.png","core/img/jugador4/idle.png","core/img/jugador4/walkFront000.png","core/img/jugador4/walkFront001.png","core/img/jugador4/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador5/walkFront003.png","core/img/jugador5/walkFront004.png","core/img/jugador5/walkFront005.png","core/img/jugador5/idle.png","core/img/jugador5/walkFront000.png","core/img/jugador5/walkFront001.png","core/img/jugador5/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador6/walkFront003.png","core/img/jugador6/walkFront004.png","core/img/jugador6/walkFront005.png","core/img/jugador6/idle.png","core/img/jugador6/walkFront000.png","core/img/jugador6/walkFront001.png","core/img/jugador6/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador7/walkFront003.png","core/img/jugador7/walkFront004.png","core/img/jugador7/walkFront005.png","core/img/jugador7/idle.png","core/img/jugador7/walkFront000.png","core/img/jugador7/walkFront001.png","core/img/jugador7/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador8/walkFront003.png","core/img/jugador8/walkFront004.png","core/img/jugador8/walkFront005.png","core/img/jugador8/idle.png","core/img/jugador8/walkFront000.png","core/img/jugador8/walkFront001.png","core/img/jugador8/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador9/walkFront003.png","core/img/jugador9/walkFront004.png","core/img/jugador9/walkFront005.png","core/img/jugador9/idle.png","core/img/jugador9/walkFront000.png","core/img/jugador9/walkFront001.png","core/img/jugador9/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador10/walkFront003.png","core/img/jugador10/walkFront004.png","core/img/jugador10/walkFront005.png","core/img/jugador10/idle.png","core/img/jugador10/walkFront000.png","core/img/jugador10/walkFront001.png","core/img/jugador10/walkFront002.png"],False),
        #    Avatar(0,500,["core/img/jugador11/walkFront003.png","core/img/jugador11/walkFront004.png","core/img/jugador11/walkFront005.png","core/img/jugador11/idle.png","core/img/jugador11/walkFront000.png","core/img/jugador11/walkFront001.png","core/img/jugador11/walkFront002.png"],False)
        #]

    # Función para conectar servidor
    def conectar(self):
        self.sk = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        try:
            self.sk.bind((self.ip, self.puerto))
        except socket.error as e:
            str(e)
        self.sk.listen(2)
        print("Esperando a primer jugador...")

    # Hilo del servicio
    def hilo_jugador(self,conexion,jugador):
        conexion.send(pickle.dumps(self.avataresStrData[jugador]))
        reply = ""
        while True:
            try:
                data = pickle.loads(conexion.recv(24000))
                self.avataresStrData[jugador] = data
                
                if not data:
                    # Poner al player X invisible
                    self.avataresStrData[jugador] = self.avataresStrData[jugador].replace("T", "F")
                    reply = self.avataresStrData
                    print("Recibido-porDesconeccion: ", data)
                    print("Enviando-porDesconeccion: ", reply)
                    conexion.sendall(pickle.dumps(reply))
                    print("cliente desconectado")
                    break
                else:
                    reply = self.avataresStrData
                    print("Recibido: ", data)
                    print("Enviando: ", reply)
                    conexion.sendall(pickle.dumps(reply))
            except:
                break
        print("conexión perdida")
        conexion.close()

    # Función que recibe conexiones
    def recibirConexion(self):
        # Contador de jugadores
        siguienteJugador = 0
        # Ciclo
        while True:
            # Crear conexión a host remoto
            conexion, direccion = self.sk.accept()
            print("Conectado a:", direccion)
            # Poner al player X visible
            self.avataresStrData[siguienteJugador] = self.avataresStrData[siguienteJugador].replace("F", "T")
            start_new_thread(self.hilo_jugador, (conexion, siguienteJugador))
            # Aumentar contador
            siguienteJugador += 1

if __name__ == "__main__":
    srv = Servidor("192.168.1.78",5000)
    srv.conectar()
    srv.recibirConexion()