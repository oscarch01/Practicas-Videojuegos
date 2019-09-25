import pygame
from red import Red


ancho = 800
alto = 600


ventana = pygame.display.set_mode((ancho,alto))
pygame.display.set_caption("Cliente")
fondo=pygame.image.load("img/fondo.jpg")
posXf,posYf=0,0

def repintarVentana(ventana,avatares):
    ventana.blit(fondo,[0,0])
    #ventana.fill((232,64,52))
    #ventana.fill((255,255,255))
    for avatar in avatares:
        avatar.dibujar(ventana)
    pygame.display.update()

def main():
    run = True
    nt = Red("127.0.0.1",5000)
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