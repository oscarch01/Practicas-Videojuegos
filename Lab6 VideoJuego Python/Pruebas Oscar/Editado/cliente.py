import pygame
import time
from red import Red

class Cliente:
    def __init__(self,ancho,alto,ip,puerto,nombre):
        self.ancho = ancho
        self.alto = alto
        self.ip = ip
        self.puerto = puerto
        self.ventana = pygame.display.set_mode((self.ancho,self.alto))
        self.fondo = pygame.image.load("img/fondo.jpg")
        pygame.display.set_caption(nombre)

    def repintarVentana(self,ventana,avatares):
        #ventana.fill((255,255,255))
        ventana.blit(self.fondo,[0,0])
        for avatar in avatares:
            avatar.dibujar(ventana)
        pygame.display.update()

    def main(self):
        run = True
        nt = Red(self.ip,self.puerto)
        avatar = nt.obtenerPK()
        clock = pygame.time.Clock()
        
        while run:
            clock.tick(60)
            avatares = nt.enviar(avatar)

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    run = False
                    pygame.quit()

            avatar.mover()
            time.sleep(0.02)
            self.repintarVentana(self.ventana,avatares)

if __name__ == "__main__":
    cn = Cliente(800,600,"192.168.1.78",5000,"Cliente")
    cn.main()