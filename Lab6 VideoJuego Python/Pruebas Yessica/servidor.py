import socket
from _thread import *
from avatar import Avatar
import pickle

class Servidor:
    def __init__(self, ip, puerto):
        self.ip = ip
        self.puerto = puerto
        self.avatares = [
            Avatar(0,500,["img/jugador0/idle.png"],False),
            Avatar(0,500,["img/jugador1/idle.png"],False),
            Avatar(0,500,["img/jugador2/idle.png"],False),
            Avatar(0,500,["img/jugador3/idle.png"],False),
            Avatar(0,500,["img/jugador4/idle.png"],False),
            Avatar(0,500,["img/jugador5/idle.png"],False),
            Avatar(0,500,["img/jugador6/idle.png"],False),
            Avatar(0,500,["img/jugador7/idle.png"],False),
            Avatar(0,500,["img/jugador8/idle.png"],False),
            Avatar(0,500,["img/jugador9/idle.png"],False),
            Avatar(0,500,["img/jugador10/idle.png"],False),
            Avatar(0,500,["img/jugador11/idle.png"],False)
        ]

    def conectar(self):
        self.sk = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        try:
            self.sk.bind((self.ip, self.puerto))
        except socket.error as e:
            str(e)
        self.sk.listen(2)
        print("Esperando a primer jugador...")

    def hilo_jugador(self,conexion,jugador):
        conexion.send(pickle.dumps(self.avatares[jugador]))
        reply = ""
        while True:
            try:
                data = pickle.loads(conexion.recv(2048))
                self.avatares[jugador] = data

                if not data:
                    print("cliente desconectado")
                    break
                else:
                    reply = self.avatares
                    print("Recibido: ", data)
                    print("Enviando : ", reply)
                    conexion.sendall(pickle.dumps(reply))
            except:
                break
        print("conexión perdida")
        conexion.close()
        

    def recibirConexion(self):
        siguienteJugador = 0
        while True:
            conexion, direccion = self.sk.accept()
            print("Conectado a:", direccion)
            self.avatares[siguienteJugador].ponerVisible()
            start_new_thread(self.hilo_jugador, (conexion, siguienteJugador))
            siguienteJugador += 1

if __name__ == "__main__":
    #srv = Servidor("192.168.1.76",5000)
    srv = Servidor("127.0.0.1",5000) #local
    #srv = Servidor("192.168.0.27",5000)
    srv.conectar()
    srv.recibirConexion()