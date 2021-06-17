#==================================================#
# File Name: Battleship.py                                                                
# Description: Pygame program that simulates a battleship opponent          
# Author: Edison Du, Edwin Sun                                                        
# Date: 2019-11-25                                                                      
#==================================================#

import pygame, sys
import math
import random
import time
from random import randint
from pygame.locals import *
pygame.init()


#===============================#
#---Colours,Fonts,Gamedisplay---#
#===============================#
BLACK = (  0,  0,  0)
WHITE = ( 255, 255, 255)
DARKSEA = (0, 52, 145)
LIGHTGREY = (184, 189, 183)
GREY = (136, 145, 137)
YELLOW = (255, 251, 0)
DARKGREY = (118,118,118)
BLACKGREY = (55,55,55)
RED = (255, 0, 0)
GREEN = (40, 255, 0)
font = pygame.font.SysFont("Copperplate Gothic Bold", 36)
fontSmall = pygame.font.SysFont("Copperplate Gothic Bold", 20)
fontLarge = pygame.font.SysFont("Copperplate Gothic Bold", 66)
gameWindow = pygame.display.set_mode((950,600))

#===============#
#---Functions---#   
#===============#


# Draws the two grids with the alphabet as the labels on the left side #
def drawGrids(alphabet):
    for j in range (50, 460, 40):
        pygame.draw.line(gameWindow, GREEN, (j, 150), (j, 550), 3)
        pygame.draw.line(gameWindow, GREEN, (j + 450, 150), (j + 450, 550), 3)
        if(int((j-50)/40) < 10):                                               
            gridLabels = font.render(str(int((j - 50) / 40) + 1), 1, GREEN)
            if(int((j-50)/40) < 9):                        
                gridLabels = font.render(" " + str(int((j - 50) / 40) + 1), 1, GREEN)
            gameWindow.blit(gridLabels,(j + 5 , 115))
            gameWindow.blit(gridLabels,(j + 455 , 115))                               
    for i in range (150, 560, 40):
        pygame.draw.line(gameWindow, GREEN, (50, i), (450, i), 3)
        pygame.draw.line(gameWindow, GREEN, (500, i),(900, i), 3)
        if(int((i-150)/40) < 10):
            gridLabels = font.render(alphabet[int((i-150)/40)], 1, GREEN)
            if(alphabet[int((i-150)/40)] == 'I'):
                gridLabels = font.render(" "+alphabet[int((i-150)/40)], 1, GREEN)
            gameWindow.blit(gridLabels,(20 ,i + 10))
            gameWindow.blit(gridLabels,(470 ,i + 10))

    playerName = font.render("YOUR BOARD", 1, GREEN)
    gameWindow.blit(playerName,(510, 560))
    aiName = font.render("THE KRIEGSMARINE", 1, GREEN)
    gameWindow.blit(aiName, (200, 560))

def drawCarrier(x, y, rotation):
    if(rotation == 0):
        pygame.draw.rect(gameWindow, YELLOW, (x, y, 200, 34))
        pygame.draw.rect(gameWindow, BLACKGREY, (x+5, y+5, 190,24))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+30,y+34),(x+30,y+40),(x+60,y+40),(x+70,y+34)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+33,y+20),(x+33,y+35),(x+60,y+35),(x+83,y+20)))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+110,y+34),(x+110,y+40),(x+170,y+40),(x+170,y+34)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+113,y+20),(x+113,y+35),(x+167,y+35),(x+167,y+20))) 
        for i in range (x+5, x+195, 7):
            pygame.draw.line(gameWindow, WHITE, (i, y+12), (i+4, y+12), 1)  
            pygame.draw.line(gameWindow, WHITE, (i, y+22), (i+4, y+22), 1)  
        pygame.draw.polygon(gameWindow, GREY, ((x+130,y+20), (x+160,y+20),(x+160,y+30),(x+120,y+30)))

    elif(rotation == 1):
        pygame.draw.rect(gameWindow, YELLOW, (x, y, 34, 200))
        pygame.draw.rect(gameWindow, BLACKGREY, (x+5, y+5, 24,190))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+34,y+30),(x+40,y+30),(x+40,y+60),(x+34,y+70)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+20,y+33),(x+35,y+33),(x+35,y+60),(x+20,y+83)))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+34,y+110),(x+40,y+110),(x+40,y+170),(x+34,y+170)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+20,y+113),(x+35,y+113),(x+35,y+167),(x+20,y+167)))  
        for i in range (y+5, y+195, 7):
            pygame.draw.line(gameWindow, WHITE, (x+12, i), (x+12, i+4), 1)  
            pygame.draw.line(gameWindow, WHITE, (x+22, i), (x+22, i+4), 1)  
        pygame.draw.polygon(gameWindow, GREY, ((x+20,y+130), (x+20,y+160),(x+30,y+160),(x+30,y+120)))

def drawBattleship(x, y, rotation):
    if(rotation == 0):
        pygame.draw.ellipse(gameWindow, YELLOW, (x, y + 4, 160, 32))
        pygame.draw.ellipse(gameWindow, BLACKGREY, (x+3, y + 7, 154, 26))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+18,y+20),(x+23,y+12),(x+33,y+15),(x+33,y+25),(x+23,y+28)))
        pygame.draw.line(gameWindow, LIGHTGREY, (x+8,y+23), (x+23,y+23),2)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+8,y+17), (x+23,y+17),2)
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+48,y+20),(x+53,y+12),(x+63,y+15),(x+63,y+25),(x+53,y+28)))
        pygame.draw.line(gameWindow, LIGHTGREY, (x+38,y+23), (x+53,y+23),2)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+38,y+17), (x+53,y+17),2)
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+70,y+20),(x+75,y+10),(x+105,y+10),(x+110,y+20),(x+105,y+30),(x+75,y+30)))
        pygame.draw.rect(gameWindow, BLACKGREY, (x+80,y+13,20,6))
        pygame.draw.rect(gameWindow, BLACKGREY, (x+80,y+21,20,6))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+142,y+20),(x+137,y+12),(x+127,y+15),(x+127,y+25),(x+137,y+28)))
        pygame.draw.line(gameWindow, LIGHTGREY, (x+152,y+23), (x+137,y+23),2)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+152,y+17), (x+137,y+17),2)

    elif(rotation == 1):
        pygame.draw.ellipse(gameWindow, YELLOW, (x + 4, y, 32, 160))
        pygame.draw.ellipse(gameWindow, BLACKGREY, (x+7, y + 3, 26, 154))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+20,y+18),(x+12,y+23),(x+15,y+33),(x+25,y+33),(x+28,y+23)))
        pygame.draw.line(gameWindow, LIGHTGREY, (x+23,y+8), (x+23,y+23),2)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+17,y+8), (x+17,y+23),2)
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+20,y+48),(x+12,y+53),(x+15,y+63),(x+25,y+63),(x+28,y+53)))
        pygame.draw.line(gameWindow, LIGHTGREY, (x+23,y+38), (x+23,y+53),2)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+17,y+38), (x+17,y+53),2)
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+20,y+70),(x+10,y+75),(x+10,y+105),(x+20,y+110),(x+30,y+105),(x+30,y+75)))
        pygame.draw.rect(gameWindow, BLACKGREY, (x+13,y+80,6,20))
        pygame.draw.rect(gameWindow, BLACKGREY, (x+21,y+80,6,20))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+20,y+142),(x+12,y+137),(x+15,y+127),(x+25,y+127),(x+28,y+137)))
        pygame.draw.line(gameWindow, LIGHTGREY, (x+23,y+152), (x+23,y+137),2)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+17,y+152), (x+17,y+137),2)

def drawCruiser(x, y, rotation):
    if (rotation == 0):
        pygame.draw.polygon(gameWindow, YELLOW, ((x,y),(x,y+40),(x+60,y+35),(x+60,y+5)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+5,y+3),(x+5,y+37),(x+60,y+32),(x+60,y+8)))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+60,y+35),(x+60,y+5),(x+120,y+5),(x+120,y+35)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+60,y+32),(x+60,y+8),(x+120,y+8),(x+120,y+32)))
        pygame.draw.rect(gameWindow, YELLOW, ((x+115,y+5,5,30)))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+60,y+20),15)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+10,y+15), (x+60,y+15),5)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+10,y+25), (x+60,y+25),5)
        pygame.draw.circle(gameWindow, DARKGREY, (x+60,y+20),10)
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+85,y+7),5)
        pygame.draw.rect(gameWindow, LIGHTGREY, (x+85,y+2,20,10))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+105,y+7),5)
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+95,y+20),5)
        pygame.draw.rect(gameWindow, LIGHTGREY, (x+95,y+15,20,10))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+115,y+20),5)
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+85,y+33),5)
        pygame.draw.rect(gameWindow, LIGHTGREY, (x+85,y+28,20,10))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+105,y+33),5)

    elif (rotation == 1):
        pygame.draw.polygon(gameWindow, YELLOW, ((x,y),(x+40,y),(x+35,y+60),(x+5,y+60)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+3,y+5),(x+37,y+5),(x+32,y+60),(x+8,y+60)))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+35,y+60),(x+5,y+60),(x+5,y+120),(x+35,y+120)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+32,y+60),(x+8,y+60),(x+8,y+120),(x+32,y+120)))
        pygame.draw.rect(gameWindow, YELLOW, ((x+5,y+115,30,5)))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+20,y+60),15)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+15,y+10), (x+15,y+60),5)
        pygame.draw.line(gameWindow, LIGHTGREY, (x+25,y+10), (x+25,y+60),5)
        pygame.draw.circle(gameWindow, DARKGREY, (x+20,y+60),10)
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+7,y+85),5)
        pygame.draw.rect(gameWindow, LIGHTGREY, (x+2,y+85,10,20))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+7,y+105),5)
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+20,y+95),5)
        pygame.draw.rect(gameWindow, LIGHTGREY, (x+15,y+95,10,20))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+20,y+115),5)
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+33,y+85),5)
        pygame.draw.rect(gameWindow, LIGHTGREY, (x+28,y+85,10,20))
        pygame.draw.circle(gameWindow, LIGHTGREY, (x+33,y+105),5)

def drawSubmarine(x, y, rotation):
    if (rotation == 0):
        pygame.draw.rect(gameWindow, BLACKGREY, (x+15,y+5,90,30))
        pygame.draw.circle(gameWindow, BLACKGREY, (x+15,y+20),15)
        pygame.draw.circle(gameWindow, BLACKGREY, (x+105,y+20),15)
        pygame.draw.line(gameWindow, YELLOW, (x+15, y+20), (x+80, y+20),13)
        pygame.draw.polygon(gameWindow, DARKGREY, ((x+80,y+10),(x+80,y+30),(x+110,y+25),(x+110,y+15)))
        pygame.draw.circle(gameWindow, YELLOW, (x+80,y+20),13)
        pygame.draw.line(gameWindow, DARKGREY, (x+20, y+20), (x+80, y+20),7)
        pygame.draw.circle(gameWindow, DARKGREY, (x+80,y+20),10)

    elif (rotation == 1):
        pygame.draw.rect(gameWindow, BLACKGREY, (x+5,y+15,30,90))
        pygame.draw.circle(gameWindow, BLACKGREY, (x+20,y+15),15)
        pygame.draw.circle(gameWindow, BLACKGREY, (x+20,y+105),15)
        pygame.draw.line(gameWindow, YELLOW, (x+20, y+15), (x+20, y+80),13)
        pygame.draw.polygon(gameWindow, DARKGREY, ((x+10,y+80),(x+30,y+80),(x+25,y+110),(x+15,y+110)))
        pygame.draw.circle(gameWindow, YELLOW, (x+20,y+80),13)
        pygame.draw.line(gameWindow, DARKGREY, (x+20, y+20), (x+20, y+80),7)
        pygame.draw.circle(gameWindow, DARKGREY, (x+20,y+80),10)

def drawDestroyer(x, y, rotation):
    if (rotation == 0):
        pygame.draw.polygon(gameWindow, YELLOW, ((x, y+20), (x+30,y+5), (x+50,y+10), (x+50,y+30) ,(x+30,y+35)))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+40,y+10),(x+40,y+30),(x+60,y+35),(x+80,y+30),(x+80,y+10),(x+60,y+5)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+5, y+20), (x+30,y+9), (x+46,y+14), (x+46,y+26) ,(x+30,y+31)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+44,y+14),(x+40,y+26),(x+60,y+31),(x+80,y+26),(x+80,y+14),(x+60,y+9)))
        pygame.draw.ellipse(gameWindow, LIGHTGREY, (x+30,y+15,30,10))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+60,y+17),(x+60,y+23),(x+75,y+25),(x+75,y+15)))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+15,y+20),(x+35,y+15),(x+35,y+25)))

    elif (rotation == 1):
        pygame.draw.polygon(gameWindow, YELLOW, ((x+20, y), (x+5,y+30), (x+10,y+50), (x+30,y+50) ,(x+35,y+30)))
        pygame.draw.polygon(gameWindow, YELLOW, ((x+10,y+40),(x+30,y+40),(x+35,y+60),(x+30,y+80),(x+10,y+80),(x+5,y+60)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+20, y+5), (x+9,y+30), (x+14,y+46), (x+26,y+46) ,(x+31,y+30)))
        pygame.draw.polygon(gameWindow, BLACKGREY, ((x+14,y+44),(x+26,y+40),(x+31,y+60),(x+26,y+80),(x+14,y+80),(x+9,y+60)))
        pygame.draw.ellipse(gameWindow, LIGHTGREY, (x+15,y+30,10,30))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+17,y+60),(x+23,y+60),(x+25,y+75),(x+15,y+75)))
        pygame.draw.polygon(gameWindow, LIGHTGREY, ((x+20,y+15),(x+15,y+35),(x+25,y+35)))

def drawBox(gridPosX, gridPosY, grid, status):
    if (grid == 1):
        gridPosX = gridPosX * 40 + 50
        gridPosY = gridPosY * 40 + 150
    if (grid == 2):
        gridPosX = gridPosX * 40 + 500
        gridPosY = gridPosY * 40 + 150

    box = pygame.Surface((40, 40)) 
    box.set_alpha(128) 

    if (status == "MISS"):
        box.fill(WHITE)
    elif (status == "HIT"):
        box.fill(RED)

    gameWindow.blit(box, ((gridPosX, gridPosY)))

# Ship prediction using probability
def calculateProbability (aiAttack, enemyShipsLeft):
    probabilityChart = [[0 for i in range (10)] for j in range (10)]
    for w in enemyShipsLeft:                              
        for i in range (0, 10):     
            for j in range (0, 10):                         
                if (i < 11-w):                        
                    shipPlacementPossibility = True
                    for d in range (0, w):                      
                        if(aiAttack[i+d][j] !=0):              
                            shipPlacementPossibility = False
                    if (shipPlacementPossibility == True):
                        for f in range (0, w):   
                            probabilityChart[i+f][j] += 1
                if (j < 11-w):                           
                    shipPlacementPossibility = True
                    for e in range (0, w):             
                        if(aiAttack[i][j+e] !=0):
                            shipPlacementPossibility = False
                    if (shipPlacementPossibility == True):
                        for g in range (0, w):
                            probabilityChart[i][j+g] += 1
    return probabilityChart

#===============#
#---Variables---#
#===============#

#---Display related---#
title = ""
subtitle = ""
shotsTakenDisplay = ""
shotsHitDisplay = ""
shotsMissedDisplay = ""
shipsLeftDisplay = ""
endDisplay = ""
numberOfShots = 0
numberOfHits = 0
shipsRemaining = 5
alphabet = "ABCDEFGHIJ"

#---User Input Related---#
userAttack = ""
aiAttackState = ""
turn = ""
inputX = 0
inputY = 0
shipCount = [5,4,3,3,2]
enemyShipCount = 5

#---Generating AI Ships---#
shipOrientation = random.choice([0,1])
shipDisplayPositions = [[0 for i in range (3)]for j in range (5)]
aiShip = [[0 for i in range (10)] for j in range (10)]
placeShipCounter = 0
currentShipLength = 5
shipY = random.randint(0,9)
shipX = random.randint(0,9)
canPlace = True

#---Ai Attack Related---#
aiAttack = [[0 for i in range (10)] for j in range (10)]
strategy = 1
probability = [[0 for i in range (10)] for j in range (10)]
highestProbability = 0
highestPosition = [0, 0]
shipNames = ["CARRIER", "BATTLESHIP","CRUISER" ,"SUBMARINE" , "DESTROYER"]
enemyShipsLeft = [5,4,3,3,2]
hitShipPositions = []
shipAdded = False  
hipDeleted = False
phaseOne = 1
phaseTwo = 1
phaseThree = 1
phaseFour = 1
skipPhaseThree = False
canShoot = True
hitFromProbability = False
hitFromSprawl = False
originalX = 0
originalY = 0
X = 0
Y = 0
searchAxis = ""

#===========================#
#---Generate Random Board---#
#===========================#

while(currentShipLength >= 2):             
    shipOrientation = random.choice([0,1])
    shipY = random.randint(0,9)
    shipX = random.randint(0,9)

    if(shipOrientation == 1):                          
        shipY = random.randint(0, currentShipLength-1)        
    elif (shipOrientation == 0):                        
        shipX = random.randint(0, currentShipLength-1)

    canPlace = True
    if(shipOrientation == 1): 
        for a in range (0,currentShipLength,1):
            if(aiShip[shipY+a][shipX] != 0):
                canPlace = False
    elif(shipOrientation == 0):
        for b in range (0,currentShipLength,1):
            if(aiShip[shipY][shipX+b] != 0):
                canPlace = False

    # Initialize 5 for carrier, 4 for battleship, 3 for submarine, 6 for cruiser,  2 for destroyer

    if(canPlace == True):
        if(shipOrientation == 1):
            for a in range (0,currentShipLength,1):
                aiShip[a+shipY][shipX] = currentShipLength     
        elif(shipOrientation == 0):
            for b in range (0,currentShipLength,1):
                aiShip[shipY][shipX+b] = currentShipLength

        shipDisplayPositions[currentShipLength-1][0] = shipX   
        shipDisplayPositions[currentShipLength-1][1] = shipY
        shipDisplayPositions[currentShipLength-1][2] = shipOrientation

        currentShipLength -= 1
        if(currentShipLength == 3): 
            currentShipLength = 2

currentShipLength = 3

while(placeShipCounter < 2):     
    shipOrientation = random.choice([0,1])
    shipY = random.randint(0,9)
    shipX = random.randint(0,9)

    if(shipOrientation == 1):                 
        shipY = random.randint(0, currentShipLength-1)    
    elif (shipOrientation == 0):               
        shipX = random.randint(0, currentShipLength-1)

    canPlace = True
    if(shipOrientation == 1):
        for a in range (0,currentShipLength,1):
            if(aiShip[shipY+a][shipX] != 0):
                canPlace = False
    elif(shipOrientation == 0):
        for b in range (0,currentShipLength,1):
            if(aiShip[shipY][shipX+b] != 0):
                canPlace = False

    if(canPlace == True): 
        if (placeShipCounter == 0):
            if(shipOrientation == 1):
                for a in range (0,currentShipLength,1):
                    aiShip[a+shipY][shipX] = currentShipLength
            elif(shipOrientation == 0):
                for b in range (0,currentShipLength,1):
                    aiShip[shipY][shipX+b] = currentShipLength     

            shipDisplayPositions[2][0] = shipX 
            shipDisplayPositions[2][1] = shipY
            shipDisplayPositions[2][2] = shipOrientation

        elif (placeShipCounter == 1):
            if(shipOrientation == 1):
                for a in range (0,currentShipLength,1):
                    aiShip[a+shipY][shipX] = 6
            elif(shipOrientation == 0):
                for b in range (0,currentShipLength,1):
                    aiShip[shipY][shipX+b] = 6

            shipDisplayPositions[0][0] = shipX
            shipDisplayPositions[0][1] = shipY
            shipDisplayPositions[0][2] = shipOrientation

        placeShipCounter += 1

#===============#
#---Game Loop---#
#===============#
while (True):
    pygame.event.clear()
    gameWindow.fill(BLACK)   

    #=============================#
    #---Drawing Grids and Ships---#
    #=============================#

    pygame.draw.rect(gameWindow, DARKSEA, (50, 150, 400, 400))
    pygame.draw.rect(gameWindow, DARKSEA,(500, 150, 400, 400))

    drawCarrier(shipDisplayPositions[4][0]*40 + 50, shipDisplayPositions[4][1]*40 + 150, shipDisplayPositions[4][2])
    drawBattleship(shipDisplayPositions[3][0]*40 + 50, shipDisplayPositions[3][1]*40 + 150, shipDisplayPositions[3][2])
    drawCruiser(shipDisplayPositions[2][0]*40 + 50, shipDisplayPositions[2][1]*40 + 150, shipDisplayPositions[2][2])
    drawSubmarine(shipDisplayPositions[0][0]*40 + 50, shipDisplayPositions[0][1]*40 + 150, shipDisplayPositions[0][2])
    drawDestroyer(shipDisplayPositions[1][0]*40 + 50, shipDisplayPositions[1][1]*40 + 150, shipDisplayPositions[1][2])

    for i in range (0, 10):
        for j in range (0, 10):
            if (aiShip[i][j] == -1):
                drawBox(j, i, 1, "MISS")
            elif (aiShip[i][j] == 1):
                drawBox(j, i, 1, "HIT")
            if (aiAttack[i][j] == 1):
                drawBox(j, i, 2, "MISS")
            elif (aiAttack[i][j] == 2):
                drawBox(j, i, 2, "HIT")

    #=================================#
    #---Displaying Titles and Stats---#
    #=================================#
    for i in range (0, 5):  
        if (shipCount[i] == 0):
            shipsRemaining -= 1
    
    pygame.draw.rect(gameWindow, GREEN, (20,10,910,100))
    pygame.draw.rect(gameWindow, BLACK, (25,15,900,90))

    title = fontLarge.render("BATTLESHIP",1,GREEN)
    subtitle = fontSmall.render(" SIMULATED OPPONENT",1,GREEN)
    shotsTakenDisplay = font.render("SHOTS TAKEN: "+str(numberOfShots),1,GREEN)
    shotsHitDisplay = font.render("HIT: "+str(numberOfHits),1,GREEN)
    shotsMissedDisplay = font.render("MISSED: "+str(numberOfShots-numberOfHits),1,GREEN)
    shipsLeftDisplay = font.render("SHIPS LEFT: " +str(shipsRemaining),1,GREEN)

    gameWindow.blit(title,(40,30))
    gameWindow.blit(subtitle,(40,80))
    gameWindow.blit(shotsTakenDisplay,(500, 30))
    gameWindow.blit(shotsHitDisplay,(760, 30))
    gameWindow.blit(shotsMissedDisplay,(760, 70))
    gameWindow.blit(shipsLeftDisplay,(500, 70))

    shipsRemaining = 5     

    drawGrids(alphabet) 

    pygame.display.update()

    #==========================#
    #---User Declares Attack---#
    #==========================#

    if (turn != "FIRST"):   
        turn = input("First or Second? ").upper() 

    if(turn == "FIRST"):              
        print()
        userAttack = input("YOUR ATTACK: ").upper()
        inputX = alphabet.index(userAttack[0])
        inputY = int(userAttack[1])-1
        if (len(userAttack) > 2):     
            inputY = int(userAttack[1:3])-1

        while(aiShip[inputX][inputY] == 1 or aiShip[inputX][inputY] == -1):  
            print("YOU HAVE ALREADY HIT AT THIS LOCATION. PLEASE INPUT A DIFFERENT LOCATION.\n")
            userAttack = input("YOUR ATTACK: ").upper()
            inputX = alphabet.index(userAttack[0])
            inputY = int(userAttack[1])-1

        if(aiShip[inputX][inputY] > 1):    
            if(aiShip[inputX][inputY] == 5):
                shipCount[0] -= 1
                if(shipCount[0] == 0):
                    print("HIT, SUNK AIRCRAFT CARRIER.")

                else:
                    print("HIT, AIRCRAFT CARRIER.")
            elif(aiShip[inputX][inputY] == 4):
                shipCount[1] -= 1
                if(shipCount[1] == 0):
                    print("HIT, SUNK BATTLESHIP.")

                else:
                    print("HIT, BATTLESHIP.")
            elif(aiShip[inputX][inputY] == 3):
                shipCount[2] -= 1
                if(shipCount[2] == 0):
                    print("HIT, SUNK CRUISER.")

                else:
                    print("HIT, CRUISER.")
            elif(aiShip[inputX][inputY] == 6):
                shipCount[3] -= 1
                if(shipCount[3] == 0):
                    print("HIT, SUNK SUBMARINE.")

                else:
                    print("HIT, SUBMARINE.")
            elif(aiShip[inputX][inputY] == 2):
                shipCount[4] -= 1
                if(shipCount[4] == 0):
                    print("HIT, SUNK DESTROYER.")

                else:
                    print("HIT, DESTROYER.")
            aiShip[inputX][inputY] = 1

        elif(aiShip[inputX][inputY] == 0):
            print("YOU HAVE MISSED.")
            aiShip[inputX][inputY] = -1

    turn = "FIRST"

    if(shipCount[0]+shipCount[1]+shipCount[2]+shipCount[3]+shipCount[4] == 0):    
        pygame.draw.rect(gameWindow, GREEN, (195, 305,585,70))
        pygame.draw.rect(gameWindow, BLACK, (200,310,575,60)) 

        endDisplay = font.render("YOU HAVE DEFEATED THE KRIEGSMARINE",1,GREEN)
        gameWindow.blit(endDisplay,(220,330))  

        pygame.display.update()
        time.sleep(20)
        pygame.quit()

    #===============================#
    #---Computer Generates Attack---#
    #===============================#

    # Determines best spot to hit with probability

    if (strategy == 1):

        probability = calculateProbability(aiAttack,enemyShipsLeft)
        numberOfShots += 1
        highestProbability = 0   

        correctInput = False

        for i in range (0, 10):
            for j in range (0, 10): 
                if (probability[i][j] > highestProbability):
                    highestProbability = probability[i][j]
                    highestPosition[0] = i
                    highestPosition[1] = j

        print ("\nI ATTACK " ,alphabet[highestPosition[0]]+str(highestPosition[1] + 1))

        while (not correctInput):
            aiAttackState = input().upper()
            
            if ("MISS" in aiAttackState):
                aiAttack[highestPosition[0]][highestPosition[1]] = 1
                correctInput = True
            elif ("HIT" in aiAttackState):
                for u in shipNames:
                    if (u in aiAttackState):
                        hitShipPositions.append([highestPosition[0],highestPosition[1],u])
                        correctInput = True

                aiAttack[highestPosition[0]][highestPosition[1]] = 2
                numberOfHits += 1
                strategy = 0 
            
            if (not correctInput):
                print("UNRECOGNIZED SHIP, PLEASE ENTER A VALID RESPONSE.")

    # If hit a ship, aim for the consecutive spots to sink it

    if (strategy == 0):

        if(phaseOne == 1):
                                   
            originalX = hitShipPositions[0][0]
            originalY = hitShipPositions[0][1] 
            queueX = []
            queueY = []
            
            if (originalX-1 >= 0 and aiAttack[originalX-1][originalY] == 0):
                queueX.append(originalX-1) 
                queueY.append(originalY) 

            if (originalY-1 >= 0 and aiAttack[originalX][originalY-1] == 0):
                queueX.append(originalX) 
                queueY.append(originalY-1)

            if (originalX+1 < 10 and aiAttack[originalX+1][originalY] == 0):
                queueX.append(originalX+1) 
                queueY.append(originalY)

            if (originalY+1 < 10 and aiAttack[originalX][originalY+1] == 0):
                queueX.append(originalX)
                queueY.append(originalY+1)

            phaseOne = 0
            phaseTwo = 1
            phaseThree = 1
            phaseFour = 1
            
        if(phaseTwo == 1):
 
            if(hitFromSprawl == True): 
                X = queueX.pop(0)
                Y = queueY.pop(0)
                numberOfShots += 1
                print("\nI ATTACK ",alphabet[X]+str(Y+1))
                aiAttackState = input().upper() 

                skipPhaseThree = True
                if("HIT" in aiAttackState): 
                    numberOfHits += 1  
                    aiAttack[X][Y] = 2 

                    if ("SUNK" not in aiAttackState):
                        if (hitShipPositions[0][2] in aiAttackState): 
                            if(X == originalX-1 or X == originalX+1):
                                if(X == originalX-1):
                                    X = originalX

                                phaseTwo = 0
                                searchAxis =  "vertical"
                                if (X+1 < 10 and aiAttack[X+1][Y] == 0):
                                    skipPhaseThree = False

                            else:
                                if(Y == originalY-1):
                                    Y = originalY

                                phaseTwo = 0
                                searchAxis =  "horizontal"
                                if (Y+1 < 10 and aiAttack[X][Y+1] == 0): 
                                    skipPhaseThree = False

                        elif (hitShipPositions[0][2] not in aiAttackState): 
                            shipAdded = False                      
                            for r in shipNames:
                                for g in range (0, len(hitShipPositions)):
                                    if (r != hitShipPositions[g][2] and r in aiAttackState and shipAdded == False):
                                        hitShipPositions.append([X,Y,r])
                                        shipAdded = True

                    elif ("SUNK" in aiAttackState): 
                        shipDeleted = False
                        for r in shipNames:
                            g = 0                   
                            while(shipDeleted == False and g < len(hitShipPositions)):
                                if (r == hitShipPositions[g][2] and r in aiAttackState):
                                    enemyShipsLeft[shipNames.index(r)] = 0
                                    del(hitShipPositions[g])
                                    shipDeleted = True
                                g += 1
                        phaseOne = 1
                        enemyShipCount -= 1

                elif ("MISS" in aiAttackState):
                    aiAttack[X][Y] = 1

        elif(phaseThree == 1 and skipPhaseThree == False):

            if(searchAxis == "vertical"):
                if(X+1 < 10):
                    X+=1
                    numberOfShots += 1
                    print("\nI ATTACK ",alphabet[X]+str(Y+1))
                    aiAttackState = input().upper()

                    if("HIT" in aiAttackState):
                        numberOfHits += 1
                        aiAttack[X][Y] = 2
                        
                        if (hitShipPositions[0][2] in aiAttackState):       
                            if("SUNK" in aiAttackState):
                                enemyShipsLeft[shipNames.index(hitShipPositions[0][2])] = 0
                                del(hitShipPositions[0])
                                phaseOne = 1
                                enemyShipCount -= 1
                        elif (hitShipPositions[0][2] not in aiAttackState):   
                            if ("SUNK" in aiAttackState):
                                shipDeleted = False
                                for r in shipNames:
                                    g = 0                   
                                    while(shipDeleted == False and g < len(hitShipPositions)):
                                        if (r == hitShipPositions[g][2] and r in aiAttackState):
                                            del(hitShipPositions[g])
                                            enemyShipsLeft[shipNames.index(r)] = 0
                                            shipDeleted = True
                                        g += 1
                            else:
                                for r in shipNames:
                                    for g in range (0, len(hitShipPositions)):
                                        if (r != hitShipPositions[g][2] and r in aiAttackState):
                                            hitShipPositions.append([X,Y,r])

                            phaseThree = 0

                    elif("MISS" in aiAttackState):
                        aiAttack[X][Y] = 1
                        phaseThree = 0     
                    if (X+1 <= 9):
                        if(aiAttack[X+1][Y] != 0):
                            phaseThree = 0
                    elif (X+1 > 9):
                        phaseThree = 0

            elif (searchAxis == "horizontal"): 
                if(Y+1 < 10):
                    Y+=1
                    numberOfShots += 1
                    print("\nI ATTACK ",alphabet[X]+str(Y+1))
                    aiAttackState = input().upper()

                    if("HIT" in aiAttackState):
                        numberOfHits += 1
                        aiAttack[X][Y] = 2
                        if (hitShipPositions[0][2] in aiAttackState):  
    
                            if("SUNK" in aiAttackState):
                                enemyShipsLeft[shipNames.index(hitShipPositions[0][2])] = 0
                                del(hitShipPositions[0])
                                phaseOne = 1
                                enemyShipCount -= 1

                        elif (hitShipPositions[0][2] not in aiAttackState):   
                            if ("SUNK" in aiAttackState):
                                shipDeleted = False
                                for r in shipNames:
                                    g = 0                   

                                    # Remove ships that have been sunk from the list of ships to sink, as well as enemy ships left #
                                    while(shipDeleted == False and g < len(hitShipPositions)):
                                        if (r == hitShipPositions[g][2] and r in aiAttackState):
                                            del(hitShipPositions[g])
                                            enemyShipsLeft[shipNames.index(r)] = 0
                                            shipDeleted = True
                                        g += 1
                            else:
                                for r in shipNames:
                                    for g in range (0, len(hitShipPositions)):
                                        if (r != hitShipPositions[g][2] and r in aiAttackState):
                                            hitShipPositions.append([X,Y,r])

                            phaseThree = 0

                    elif("MISS" in aiAttackState):
                        aiAttack[X][Y] = 1
                        phaseThree = 0
                    
                    if (Y+1 <= 9):
                        if(aiAttack[X][Y+1] != 0):
                            phaseThree = 0
                    elif (Y+1 > 9):
                        phaseThree = 0

        elif(phaseFour == 1):
            X = originalX
            Y = originalY
            if(searchAxis == "vertical"):
                if(X-1 >= 0):
                    canShoot = True
                    while(canShoot == True):
                        if(X-1 >= 0):
                            X -= 1
                        if(aiAttack[X][Y] == 0):
                            canShoot = False
                    numberOfShots += 1
                    print("\nI ATTACK ",alphabet[X]+str(Y+1))
                    aiAttackState = input().upper()

                    if("HIT" in aiAttackState):
                        numberOfHits += 1
                        aiAttack[X][Y] = 2
                        if (hitShipPositions[0][2] in aiAttackState):

                            if("SUNK" in aiAttackState):
                                enemyShipsLeft[shipNames.index(hitShipPositions[0][2])] = 0
                                del(hitShipPositions[0])
                                phaseOne = 1
                                enemyShipCount -= 1

                        elif (hitShipPositions[0][2] not in aiAttackState):   
                            if ("SUNK" in aiAttackState):
                                shipDeleted = False
                                for r in shipNames:
                                    g = 0                   

                                    while(shipDeleted == False and g < len(hitShipPositions)):
                                        if (r == hitShipPositions[g][2] and r in aiAttackState):
                                            del(hitShipPositions[g])
                                            enemyShipsLeft[shipNames.index(r)] = 0
                                            shipDeleted = True
                                        g += 1
                            else:
                                for r in shipNames:
                                    for g in range (0, len(hitShipPositions)):
                                        if (r != hitShipPositions[g][2] and r in aiAttackState):
                                            hitShipPositions.append([X,Y,r])
                                            
                            phaseFour = 0

                    elif("MISS" in aiAttackState):
                        aiAttack[X][Y] = 1  
                        phaseFour = 0
                        
                    if (X-1 <= 9):
                        if(aiAttack[X-1][Y] != 0):
                            phaseFour = 0
                    elif (X-1 > 9):
                        phaseFour = 0

            elif (searchAxis == "horizontal"):
                if(Y-1 >= 0):
                    canShoot = True
                    while(canShoot == True):
                        if(Y-1 >= 0):
                            Y -= 1
                        if(aiAttack[X][Y] == 0):
                            canShoot = False
                    numberOfShots += 1
                    print("\nI ATTACK ",alphabet[X]+str(Y+1))
                    aiAttackState = input().upper()

                    if("HIT" in aiAttackState):
                        numberOfHits += 1
                        aiAttack[X][Y] = 2

                        if (hitShipPositions[0][2] in aiAttackState):
                            if("SUNK" in aiAttackState):
                                enemyShipsLeft[shipNames.index(hitShipPositions[0][2])] = 0
                                del(hitShipPositions[0])
                                phaseOne = 1
                                enemyShipCount -= 1

                        elif (hitShipPositions[0][2] not in aiAttackState):   
                            if ("SUNK" in aiAttackState):
                                shipDeleted = False
                                for r in shipNames:
                                    g = 0                   

                                    while(shipDeleted == False and g < len(hitShipPositions)):
                                        if (r == hitShipPositions[g][2] and r in aiAttackState):
                                            del(hitShipPositions[g])
                                            enemyShipsLeft[shipNames.index(r)] = 0
                                            shipDeleted = True
                                        g += 1
                            else:
                                for r in shipNames:
                                    for g in range (0, len(hitShipPositions)):
                                        if (r != hitShipPositions[g][2] and r in aiAttackState):
                                            hitShipPositions.append([X,Y,r])

                            phaseFour = 0

                    elif("MISS" in aiAttackState):
                        aiAttack[X][Y] = 1
                        phaseFour = 0
                    if (Y-1 <= 9):
                        if(aiAttack[X][Y-1] != 0):
                            phaseFour = 0
                    elif (Y-1 > 9):
                        phaseFour = 0
            
        hitFromSprawl = True

        if(len(hitShipPositions) == 0):
            strategy = 1
            hitFromSprawl = False

    if (enemyShipCount == 0):
        pygame.draw.rect(gameWindow, GREEN, (175, 305,625,70))
        pygame.draw.rect(gameWindow, BLACK, (180,310,615,60))   

        endDisplay = font.render("YOU HAVE BEEN BEAT BY THE KRIEGSMARINE",1,GREEN)
        gameWindow.blit(endDisplay,(200,330))

        pygame.display.update()
        time.sleep(20)
        pygame.quit() 