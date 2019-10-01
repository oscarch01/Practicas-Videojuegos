import socket
from _thread import *
from avatar import Avatar
import pickle

class Servidor:
    def __init__(self, ip, puerto):
        self.ip = ip
        self.puerto = puerto
        self.avatares = [
            Avatar(0,500,["img/jugador0/walkFront003.png","img/jugador0/walkFront004.png","img/jugador0/walkFront005.png","img/jugador0/idle.png","img/jugador0/walkFront000.png","img/jugador0/walkFront001.png","img/jugador0/walkFront002.png"],False),
            Avatar(0,500,["img/jugador1/walkFront003.png","img/jugador1/walkFront004.png","img/jugador1/walkFront005.png","img/jugador1/idle.png","img/jugador1/walkFront000.png","img/jugador1/walkFront001.png","img/jugador1/walkFront002.png"],False),
            Avatar(0,500,["img/jugador2/walkFront003.png","img/jugador2/walkFront004.png","img/jugador2/walkFront005.png","img/jugador2/idle.png","img/jugador2/walkFront000.png","img/jugador2/walkFront001.png","img/jugador2/walkFront002.png"],False),
            Avatar(0,500,["img/jugador3/walkFront003.png","img/jugador3/walkFront004.png","img/jugador3/walkFront005.png","img/jugador3/idle.png","img/jugador3/walkFront000.png","img/jugador3/walkFront001.png","img/jugador3/walkFront002.png"],False),
            Avatar(0,500,["img/jugador4/walkFront003.png","img/jugador4/walkFront004.png","img/jugador4/walkFront005.png","img/jugador4/idle.png","img/jugador4/walkFront000.png","img/jugador4/walkFront001.png","img/jugador4/walkFront002.png"],False),
            Avatar(0,500,["img/jugador5/walkFront003.png","img/jugador5/walkFront004.png","img/jugador5/walkFront005.png","img/jugador5/idle.png","img/jugador5/walkFront000.png","img/jugador5/walkFront001.png","img/jugador5/walkFront002.png"],False),
            Avatar(0,500,["img/jugador6/walkFront003.png","img/jugador6/walkFront004.png","img/jugador6/walkFront005.png","img/jugador6/idle.png","img/jugador6/walkFront000.png","img/jugador6/walkFront001.png","img/jugador6/walkFront002.png"],False),
            Avatar(0,500,["img/jugador7/walkFront003.png","img/jugador7/walkFront004.png","img/jugador7/walkFront005.png","img/jugador7/idle.png","img/jugador7/walkFront000.png","img/jugador7/walkFront001.png","img/jugador7/walkFront002.png"],False),
            Avatar(0,500,["img/jugador8/walkFront003.png","img/jugador8/walkFront004.png","img/jugador8/walkFront005.png","img/jugador8/idle.png","img/jugador8/walkFront000.png","img/jugador8/walkFront001.png","img/jugador8/walkFront002.png"],False),
            Avatar(0,500,["img/jugador9/walkFront003.png","img/jugador9/walkFront004.png","img/jugador9/walkFront005.png","img/jugador9/idle.png","img/jugador9/walkFront000.png","img/jugador9/walkFront001.png","img/jugador9/walkFront002.png"],False),
            Avatar(0,500,["img/jugador10/walkFront003.png","img/jugador10/walkFront004.png","img/jugador10/walkFront005.png","img/jugador10/idle.png","img/jugador10/walkFront000.png","img/jugador10/walkFront001.png","img/jugador10/walkFront002.png"],False),
            Avatar(0,500,["img/jugador11/walkFront003.png","img/jugador11/walkFront004.png","img/jugador11/walkFront005.png","img/jugador11/idle.png","img/jugador11/walkFront000.png","img/jugador11/walkFront001.png","img/jugador11/walkFront002.png"],False)
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
                data = pickle.loads(conexion.recv(4096))
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
    srv = Servidor("192.168.1.78",5000)
    srv.conectar()
    srv.recibirConexion()