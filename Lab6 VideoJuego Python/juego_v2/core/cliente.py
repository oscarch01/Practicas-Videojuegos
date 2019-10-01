import pygame
import time
from core.red import Red
from core.avatar import Avatar

# Clase cliente
class Cliente:
    # Inicializar cliente
    def __init__(self,ancho,alto,ip,puerto,nombre):
        self.ancho = ancho
        self.alto = alto
        self.ip = ip
        self.puerto = puerto
        # Definir ventana pygame
        self.ventana = pygame.display.set_mode((self.ancho,self.alto))
        self.fondo = pygame.image.load("core/img/fondo.jpg")
        pygame.display.set_caption(nombre)

    # Función para repintar la ventana
    def repintarVentana(self,ventana, aryStrAvatares):
        # Mantener fondo
        ventana.blit(self.fondo,[0,0])
        # Pintar cada avatar
        for strAvaData in aryStrAvatares:
            avatarPlayer = Avatar(strAvaData)
            avatarPlayer.dibujar(ventana)
        # Actualizar ventana
        pygame.display.update()

    # Función que mantiene conexión con el servidor
    def main(self):
        # Bandera
        run = True
        # Instancia de Red
        nt = Red(self.ip,self.puerto)
        # Recuperar data de avatar
        strAvatarData = nt.obtenerPK()
        avatarPlayer = Avatar(strAvatarData)
        # Inicializar reloj
        clock = pygame.time.Clock()
        # Empezar hilo de juego
        while run:
            # Trtar reloj en milisegundos
            clock.tick(60)
            # Convierte al avatar en datos 
            aryStrAvatares = nt.enviar(avatarPlayer.getStrData())
            # Validar eventos
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    # Avisar por desconexión
                    avatarPlayer.ponerInVisible()
                    # Convierte al avatar en datos 
                    aryStrAvatares = nt.enviar(avatarPlayer.getStrData())
                    # Dejar de correr
                    run = False
                    pygame.quit()
            # Control de movimiento de player
            avatarPlayer.mover()
            # Tratar el hilo
            time.sleep(0.02)
            # Repintar avatares
            self.repintarVentana(self.ventana, aryStrAvatares)

if __name__ == "__main__":
    cn = Cliente(800,600,"192.168.1.78",5000,"Cliente")
    cn.main()