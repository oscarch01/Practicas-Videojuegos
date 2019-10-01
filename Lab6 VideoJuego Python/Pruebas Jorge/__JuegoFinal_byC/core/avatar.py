import pygame

class Avatar:
    def __init__(self, str_data):
        # Arreglo de imagenes
        temp_aryImgs = [["core/img/jugador0/walkFront003.png","core/img/jugador0/walkFront004.png","core/img/jugador0/walkFront005.png","core/img/jugador0/idle.png","core/img/jugador0/walkFront000.png","core/img/jugador0/walkFront001.png","core/img/jugador0/walkFront002.png"], ["core/img/jugador1/walkFront003.png","core/img/jugador1/walkFront004.png","core/img/jugador1/walkFront005.png","core/img/jugador1/idle.png","core/img/jugador1/walkFront000.png","core/img/jugador1/walkFront001.png","core/img/jugador1/walkFront002.png"], ["core/img/jugador2/walkFront003.png","core/img/jugador2/walkFront004.png","core/img/jugador2/walkFront005.png","core/img/jugador2/idle.png","core/img/jugador2/walkFront000.png","core/img/jugador2/walkFront001.png","core/img/jugador2/walkFront002.png"], ["core/img/jugador3/walkFront003.png","core/img/jugador3/walkFront004.png","core/img/jugador3/walkFront005.png","core/img/jugador3/idle.png","core/img/jugador3/walkFront000.png","core/img/jugador3/walkFront001.png","core/img/jugador3/walkFront002.png"], ["core/img/jugador4/walkFront003.png","core/img/jugador4/walkFront004.png","core/img/jugador4/walkFront005.png","core/img/jugador4/idle.png","core/img/jugador4/walkFront000.png","core/img/jugador4/walkFront001.png","core/img/jugador4/walkFront002.png"], ["core/img/jugador5/walkFront003.png","core/img/jugador5/walkFront004.png","core/img/jugador5/walkFront005.png","core/img/jugador5/idle.png","core/img/jugador5/walkFront000.png","core/img/jugador5/walkFront001.png","core/img/jugador5/walkFront002.png"], ["core/img/jugador6/walkFront003.png","core/img/jugador6/walkFront004.png","core/img/jugador6/walkFront005.png","core/img/jugador6/idle.png","core/img/jugador6/walkFront000.png","core/img/jugador6/walkFront001.png","core/img/jugador6/walkFront002.png"], ["core/img/jugador7/walkFront003.png","core/img/jugador7/walkFront004.png","core/img/jugador7/walkFront005.png","core/img/jugador7/idle.png","core/img/jugador7/walkFront000.png","core/img/jugador7/walkFront001.png","core/img/jugador7/walkFront002.png"], ["core/img/jugador8/walkFront003.png","core/img/jugador8/walkFront004.png","core/img/jugador8/walkFront005.png","core/img/jugador8/idle.png","core/img/jugador8/walkFront000.png","core/img/jugador8/walkFront001.png","core/img/jugador8/walkFront002.png"], ["core/img/jugador9/walkFront003.png","core/img/jugador9/walkFront004.png","core/img/jugador9/walkFront005.png","core/img/jugador9/idle.png","core/img/jugador9/walkFront000.png","core/img/jugador9/walkFront001.png","core/img/jugador9/walkFront002.png"], ["core/img/jugador10/walkFront003.png","core/img/jugador10/walkFront004.png","core/img/jugador10/walkFront005.png","core/img/jugador10/idle.png","core/img/jugador10/walkFront000.png","core/img/jugador10/walkFront001.png","core/img/jugador10/walkFront002.png"], ["core/img/jugador11/walkFront003.png","core/img/jugador11/walkFront004.png","core/img/jugador11/walkFront005.png","core/img/jugador11/idle.png","core/img/jugador11/walkFront000.png","core/img/jugador11/walkFront001.png","core/img/jugador11/walkFront002.png"]]
        # Dividir datos
        self.asy_data = str_data.split("|")
        # Ajuste de datos
        temp_prevX = int(self.asy_data[0])
        temp_x = int(self.asy_data[1])
        temp_y = int(self.asy_data[2])
        temp_idp = int(self.asy_data[3])
        temp_imgs = temp_aryImgs[temp_idp]
        temp_index = int(self.asy_data[4])
        temp_visible = self.asy_data[5]
        if temp_visible == "T":
            temp_visible = True
        else:
            temp_visible = False
        # Iniciar player
        self.inicializar(temp_prevX, temp_x, temp_y, temp_idp, temp_imgs, temp_index, temp_visible)

    # Función para abstraer los datos del player como cadena
    def getStrData(self):
        strIdV = ""
        if self.visible:
            strIdV = "T"
        else:
            strIdV = "F"
        # Armar cadena de datos del player
        strMyDataPlayer = str(self.prevX) + "|" + str(self.x) + "|" + str(self.y) + "|" + str(self.idplay) + "|" + str(self.index) + "|" + strIdV
        # Regresar cadena
        return strMyDataPlayer

    # Función que inicializa al player con sus respectivos parametros
    def inicializar(self, preX, x, y, mi_idp, imagenes, mi_index, visible):
        self.prevX = preX
        self.x = x
        self.y = y
        self.idplay = mi_idp
        self.imagenes = imagenes
        self.index = mi_index
        self.velocidad = 3
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

    def ponerInVisible(self):
        self.visible = False

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