import pygame

class Avatar:
    def __init__(self, x, y, imagenes, visible):
        self.x = x
        self.prevX = x
        self.y = y
        self.imagenes = imagenes
        self.velocidad = 3
        self.index = 0
        self.visible = visible

    def dibujar(self,ventana):
        if self.visible:            
            imagen = pygame.image.load(self.imagenes[self.index])
            imagen.convert()
            ventana.blit(imagen,(self.x,self.y))

    def mover(self):
        teclas = pygame.key.get_pressed()

        if teclas[pygame.K_LEFT]:
            self.x -= self.velocidad

        if teclas[pygame.K_RIGHT]:
            self.x += self.velocidad

        #if teclas[pygame.K_UP]:
        #    self.y -= self.velocidad

        #if teclas[pygame.K_DOWN]:
        #    self.y += self.velocidad

        if self.prevX > self.x:
            self.prevX = self.x
            self.actualizar("R")
        else:
            if self.prevX < self.x:
                self.prevX = self.x
                self.actualizar("L")
            else:
                self.actualizar("N")


    def ponerVisible(self):
        self.visible = True

    def actualizar(self,direccion):
        if direccion == "L":
            self.index += 1        
            if self.index >= len(self.imagenes):
                self.index = 4

        if direccion == "R":
            self.index -= 1        
            if self.index < 0:
                self.index = 2

        if direccion == "N":
            self.index = 3        