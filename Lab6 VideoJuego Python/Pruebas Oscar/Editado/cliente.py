import pygame
from red import Red

ancho = 800
alto = 600
ventana = pygame.display.set_mode((ancho,alto))
pygame.display.set_caption("Cliente")

def repintarVentana(ventana,avatares):
    ventana.fill((255,255,255))
    for avatar in avatares:
        avatar.dibujar(ventana)
    pygame.display.update()

def main():
    run = True
    nt = Red("192.168.1.76",5000)
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
        repintarVentana(ventana,avatares)

main()