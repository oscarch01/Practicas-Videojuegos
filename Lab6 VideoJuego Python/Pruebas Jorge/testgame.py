#!/usr/bin/env python
#-*- coding: utf-8 -*-

# Importar librerias
import os
import sys
import socket

# Importar Pygame
sys.stdout = open(os.devnull, 'w')
import pygame
from pygame.locals import *
sys.stdout = sys.__stdout__

# Iniciar Pygame
pygame.init()

# Recuperar Hosrt y dirección IP
hostName = socket.gethostname()
ipAddr = socket.gethostbyname(hostName)

# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Definir colores
colorNegro = (0, 0, 0)
colorBlanco = (255, 255, 255)
colorActive = pygame.Color(255, 255, 255)
colorInactive = pygame.Color(180, 180, 180)

# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Variables generales
sprW, sprH = 65, 80 # Tamaño del Sprite
vtnW, vtnH = 800,500 # Ancho y alto de ventana
tiempoMS = pygame.time.get_ticks() # N/1000 para volver segundos
delayIdleTimeMS = 50 # Retraso para movimiento en milisegundos
delayRunTimeMS = 10 # Retraso para movimiento en milisegundos
velMov = 1 # Velocidad del movimiento

# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Crear ventana de Pygame
ventana = pygame.display.set_mode((vtnW, vtnH))
pygame.display.set_caption("Multiplayer")

# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Elementos de ventana
doConection = False
clock = pygame.time.Clock()
fontGam = pygame.font.Font(None, 25) # Tipo y tamaño de fuente

inputBox_IP = pygame.Rect(250, 65, 140, 32)
inputBox_Port_H = pygame.Rect(250, 115, 140, 32)
inputBox_Port_L = pygame.Rect(250, 165, 140, 32)
inputBox_NickName = pygame.Rect(250, 215, 140, 32)

colorIB_IP = colorInactive
colorIB_Port_H = colorInactive
colorIB_Port_L = colorInactive
colorIB_NickName = colorInactive

isActive_IB_IP = False
isActive_IB_Port_H = False
isActive_IB_Port_L = False
isActive_IB_NickName = False

text_IB_IP = ipAddr
text_IB_Port_H = '5000'
text_IB_Port_L = '6000'
text_IB_NickName = 'PlayerZero'

buttonConection = pygame.Rect(350, 265, 50, 32)
colorBTN_C = colorInactive
isActive_BTN_C = False

def tryConection (text_ip, port_h, port_l, nickname):
    # Bandera "True" si la conexión fallo, "False" si fue un exito
    conectionFailure = True
    print ("IP: " + text_ip)
    print ("PortH: " + port_h)
    print ("PortL: " + port_l)
    print ("NickName: " + nickname)
    # Regresar estado de la conexión
    return conectionFailure

# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Mostrar formulario
while not doConection:
    # Revisar eventos Pygame
    for event in pygame.event.get():
        # Si es el evento de salida
        if event.type == pygame.QUIT:
            doConection = True
        # Revisar click de mouse
        if event.type == pygame.MOUSEBUTTONDOWN:
            # InputBox_IP
            # Si el usuario hace click sobre el InputBox_IP
            if inputBox_IP.collidepoint(event.pos):
                isActive_IB_IP = not isActive_IB_IP
            else:
                isActive_IB_IP = False
            # inputBox_Port_H
            # Si el usuario hace click sobre el inputBox_Port_H
            if inputBox_Port_H.collidepoint(event.pos):
                isActive_IB_Port_H = not isActive_IB_Port_H
            else:
                isActive_IB_Port_H = False
            # inputBox_Port_L
            # Si el usuario hace click sobre el inputBox_Port_L
            if inputBox_Port_L.collidepoint(event.pos):
                isActive_IB_Port_L = not isActive_IB_Port_L
            else:
                isActive_IB_Port_L = False
            # inputBox_NickName
            # Si el usuario hace click sobre el inputBox_NickName
            if inputBox_NickName.collidepoint(event.pos):
                isActive_IB_NickName = not isActive_IB_NickName
            else:
                isActive_IB_NickName = False
            
            # Cambiar el color actual de los InputBox
            colorIB_IP = colorActive if isActive_IB_IP else colorInactive
            colorIB_Port_H = colorActive if isActive_IB_Port_H else colorInactive
            colorIB_Port_L = colorActive if isActive_IB_Port_L else colorInactive
            colorIB_NickName = colorActive if isActive_IB_NickName else colorInactive

            # buttonConection
            # Si el usuario hace click sobre el buttonConection
            if buttonConection.collidepoint(event.pos):
                doConection = tryConection(text_IB_IP, text_IB_Port_H, text_IB_Port_L, text_IB_NickName)

        # Revisar eventos del tecleado 
        if event.type == pygame.KEYDOWN:
            # Si esta activo el InputBox_IP
            if isActive_IB_IP:
                if event.key == pygame.K_RETURN:
                    text_IB_IP = ''
                elif event.key == pygame.K_BACKSPACE:
                    text_IB_IP = text_IB_IP[:-1]
                else:
                    text_IB_IP += event.unicode
            # Si esta activo el inputBox_Port_H
            if isActive_IB_Port_H:
                if event.key == pygame.K_RETURN:
                    text_IB_Port_H = ''
                elif event.key == pygame.K_BACKSPACE:
                    text_IB_Port_H = text_IB_Port_H[:-1]
                else:
                    text_IB_Port_H += event.unicode
            # Si esta activo el inputBox_Port_L
            if isActive_IB_Port_L:
                if event.key == pygame.K_RETURN:
                    text_IB_Port_L = ''
                elif event.key == pygame.K_BACKSPACE:
                    text_IB_Port_L = text_IB_Port_L[:-1]
                else:
                    text_IB_Port_L += event.unicode
            # Si esta activo el inputBox_NickName
            if isActive_IB_NickName:
                if event.key == pygame.K_RETURN:
                    text_IB_NickName = ''
                elif event.key == pygame.K_BACKSPACE:
                    text_IB_NickName = text_IB_NickName[:-1]
                else:
                    text_IB_NickName += event.unicode
    # Pintar fondo de ventana
    ventana.fill(colorNegro)
    # Definir labels
    textGam_IP = fontGam.render(u"Dirección IP:", 0, colorIB_IP)
    textGam_Port_H = fontGam.render(u"Puerto (Host):", 0, colorIB_Port_H)
    textGam_Port_L = fontGam.render(u"Puerto (Local):", 0, colorIB_Port_L)
    textGam_NickName = fontGam.render(u"Apodo:", 0, colorIB_NickName)
    # Dibujar labels
    ventana.blit(textGam_IP, (100, 75))
    ventana.blit(textGam_Port_H, (100, 125))
    ventana.blit(textGam_Port_L, (100, 175))
    ventana.blit(textGam_NickName, (100, 225))
    # -------------------
    # -------------------
    # InputBox_IP...
    # Preparar texto
    txtSur_IB_IP = fontGam.render(text_IB_IP, True, colorIB_IP)
    # Calcular tamaño de la caja
    width_IB_IP = max(200, txtSur_IB_IP.get_width() + 10)
    inputBox_IP.w = width_IB_IP
    # Mostrar texto de input en ventana
    ventana.blit(txtSur_IB_IP, (inputBox_IP.x + 5, inputBox_IP.y + 8))
    pygame.draw.rect(ventana, colorIB_IP, inputBox_IP, 2)

    # inputBox_Port_H...
    # Preparar texto
    txtSur_IB_Port_H = fontGam.render(text_IB_Port_H, True, colorIB_Port_H)
    # Calcular tamaño de la caja
    width_IB_Port_H = max(200, txtSur_IB_Port_H.get_width() + 10)
    inputBox_Port_H.w = width_IB_Port_H
    # Mostrar texto de input en ventana
    ventana.blit(txtSur_IB_Port_H, (inputBox_Port_H.x + 5, inputBox_Port_H.y + 8))
    pygame.draw.rect(ventana, colorIB_Port_H, inputBox_Port_H, 2)

    # inputBox_Port_L...
    # Preparar texto
    txtSur_IB_Port_L = fontGam.render(text_IB_Port_L, True, colorIB_Port_L)
    # Calcular tamaño de la caja
    width_IB_Port_L = max(200, txtSur_IB_Port_L.get_width() + 10)
    inputBox_Port_L.w = width_IB_Port_L
    # Mostrar texto de input en ventana
    ventana.blit(txtSur_IB_Port_L, (inputBox_Port_L.x + 5, inputBox_Port_L.y + 8))
    pygame.draw.rect(ventana, colorIB_Port_L, inputBox_Port_L, 2)

    # inputBox_NickName...
    # Preparar texto
    txtSur_IB_NickName = fontGam.render(text_IB_NickName, True, colorIB_NickName)
    # Calcular tamaño de la caja
    width_IB_NickName = max(200, txtSur_IB_NickName.get_width() + 10)
    inputBox_NickName.w = width_IB_NickName
    # Mostrar texto de input en ventana
    ventana.blit(txtSur_IB_NickName, (inputBox_NickName.x + 5, inputBox_NickName.y + 8))
    pygame.draw.rect(ventana, colorIB_NickName, inputBox_NickName, 2)

    # buttonConection
    # Si el usuario hace click sobre el buttonConection
    if buttonConection.collidepoint(pygame.mouse.get_pos()):
        isActive_BTN_C = True
    else:
        isActive_BTN_C = False
    # Cambiar el color actual del Button
    colorBTN_C = colorActive if isActive_BTN_C else colorInactive
    # buttonConection...
    # Preparar texto
    txtSur_BTN_C = fontGam.render("Conectar", True, colorBTN_C)
    # Calcular tamaño de la caja
    width_BTN_C = max(100, txtSur_BTN_C.get_width())
    buttonConection.w = width_BTN_C
    # Mostrar texto de input en ventana
    ventana.blit(txtSur_BTN_C, (buttonConection.x + 13, buttonConection.y + 7))
    pygame.draw.rect(ventana, colorBTN_C, buttonConection, 2)

    # -------------------
    # -------------------
    pygame.display.flip()
    # Tiempo de espera
    clock.tick(30)

'''
# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Variables del Player
statu = "" # IDLE, MOV
direc = "" # RIGHT, LEFT
posX, posY = 350, 400 # X, Y
imgSprPy1 = pygame.image.load("Imagenes/Idle/stand000.png")
imgSprPy2 = pygame.image.load("Imagenes/Idle/stand000.png")

# ---------------------------------------------------------------
# ---------------------------------------------------------------
# Loop infinito para ventana
while True:
    # Cargar fondo
    ventana.fill(colorNegro)
    # Cargar imagen
    ventana.blit(imgSprPy1, (posX, posY))
    ventana.blit(imgSprPy2, (0, posY))
    # Revisar eventos Pygame
    for evento in pygame.event.get():
        # Si se busca salir de la ventana
        if evento.type == QUIT:
            pygame.quit()
            sys.exit()
            break
        # Validar acciones por tecla
        elif evento.type == pygame.KEYDOWN:
            if evento.key == K_RIGHT:
                direc = "RIGHT"
                statu = "MOV"
            elif evento.key == K_LEFT:
                direc = "LEFT"
                statu = "MOV"
        elif evento.type == pygame.KEYUP:
            if evento.key == K_LEFT or evento.key == K_RIGHT:
                statu = "IDLE"
    # Dirigir movimiento
    if statu == "MOV":
        if direc == "RIGHT":
            if (posX + velMov) <= (vtnW - sprW):
                posX += velMov
            else:
                statu = "IDLE"
        elif direc == "LEFT":
            if (posX - velMov) >= 0:
                posX -= velMov
            else:
                statu = "IDLE"
    # Actualizar ventana
    pygame.display.update()
'''