#==================================================#
# File Name: PygameHouse.py                                                                
# Description: Pygame program that draws a house with animations                           
# Author: Edison Du                                                                        
# Date: 10/28/2019                                                                         
#==================================================#
import pygame,sys
from pygame.locals import *
import random   
pygame.init()

#====================================#
# Colours 
#====================================#
RED  = (255,  0,  0)
GREEN= (  0,255,  0)
BLUE = (  0,  0,255)
CYAN = (  0,255,255)
WHITE = (255,255,255)
BLACK = (  0,  0,  0)
GREY = (128,128,128)
YELLOW = (255,255,0)
GRASS = (8, 189, 8) 
DARKGREEN = (8, 87, 11)
LIGHTRED = (227, 25, 25)
DARKRED = (166, 8, 8)
DARKERRED = (100, 20, 0)
SAND = (245, 212, 76)
BROWN = (65, 33, 1)
GLASS = (108, 214, 234)
LIGHTGREY = (188, 188, 188)
TREEGREEN = (7, 69, 0)
SKY = (0, 36, 67)

#====================================#
# Functions
#====================================#
def drawTree(cenX, cenY, leaves):
    pygame.draw.rect(gameWindow, BROWN, (cenX - 10, cenY, 20, 50))
    if (leaves == "triangle"):
        pygame.draw.polygon(gameWindow, TREEGREEN, ((cenX - 40, cenY), (cenX + 40, cenY), (cenX, cenY - 120)))
    if (leaves == "circle"):
        pygame.draw.ellipse(gameWindow, TREEGREEN, (cenX - 35, cenY - 120, 70, 140))

def drawCloud(cenX, cenY, drX, drY): 
    pygame.draw.circle(gameWindow, WHITE, (cenX, cenY), 30)
    pygame.draw.circle(gameWindow, WHITE, (cenX - drX * 30, cenY + drY * 7), 20)
    pygame.draw.circle(gameWindow, WHITE, (cenX + drX * 4, cenY + drY * 15), 25)
    pygame.draw.circle(gameWindow, WHITE, (cenX + drX * 30, cenY + drY * 10), 25)
    pygame.draw.circle(gameWindow, WHITE, (cenX + drX * 20, cenY - drY * 15), 15)

def drawWindow(topLeftX, topLeftY):
    pygame.draw.rect(gameWindow, BROWN, (topLeftX, topLeftY, 260, 60))
    pygame.draw.rect(gameWindow, GLASS, (topLeftX + 5, topLeftY + 5, 250, 50))
    pygame.draw.line(gameWindow, BROWN, (topLeftX + 130 ,topLeftY), (topLeftX + 130, topLeftY + 55), 5)
    pygame.draw.line(gameWindow, BROWN, (topLeftX + 193 ,topLeftY), (topLeftX + 193, topLeftY + 55), 5)
    pygame.draw.line(gameWindow, BROWN, (topLeftX + 63 ,topLeftY), (topLeftX + 63, topLeftY + 55), 5)

def drawStars(amount, positions):
    for i in range (amount):
        pygame.draw.circle(gameWindow, WHITE, (positions[i][0], positions[i][1]), 2)

#====================================#
# Variables
#====================================#
GRIDSIZE = 10
WIDTH = 800
HEIGHT = 700

gameWindow = pygame.display.set_mode((WIDTH,HEIGHT))
timer = 0
sunY = 450
sunX = -40
moonY = 450
moonX = -40
crescent = 0
cloudX = 900 
cloudY = random.randint(50, 300)
cloudXTwo = 1400
cloudYTwo = random.randint(50, 300)
cloudDrX = 1
cloudDrY = 1
amountStars = 0
starPositions = []
skyRValue = 0
skyGValue = 36
skyBValue = 67
shadowPoint4 = [900, 530]
shadowPoint5 = [900, 530]
nightTime = False
inPlay = True

#====================================#
# Main Program
#====================================#
while (inPlay == True):
    pygame.event.clear()
    SKY = (round(skyRValue), round(skyGValue), round(skyBValue))
    gameWindow.fill(SKY)

    #====================================#
    # Drawing Objects
    #====================================#
    #-Sun-------------#
    pygame.draw.circle(gameWindow, YELLOW, (sunX, sunY), 70)

    #-Moon------------#
    pygame.draw.circle(gameWindow, WHITE, (moonX, moonY), 55)
    pygame.draw.circle(gameWindow, SKY, (moonX - 35, moonY), crescent)

    #-Clouds----------#
    if (nightTime == False):
        drawCloud(cloudX, cloudY, cloudDrX, cloudDrY)
        drawCloud(cloudXTwo, cloudYTwo, cloudDrY, cloudDrX)

    #-Star Generator--#
    if (sunX == 800):
        amountStars = random.randint(20, 70)                                            
        starPositions = [[0 for i in range(2)]for j in range(amountStars)]
        for i in range(amountStars):
            starPositions[i][0] = random.randint(0, 800)
            starPositions[i][1] = random.randint(0, 400)

    #-Stars-----------#
    if (nightTime == True):
        drawStars(amountStars, starPositions)

    #-Grass-----------#
    pygame.draw.rect(gameWindow, GRASS, (0, 350, WIDTH, 350))
    for y in range(350, HEIGHT, 5):
        pygame.draw.line(gameWindow, DARKGREEN, (0,y),(WIDTH,y),2)
     
    #-Sidewalk--------#
    pygame.draw.polygon(gameWindow, LIGHTGREY, ((150, 570), (250, 570), (165,700), (65, 700)))
    pygame.draw.line(gameWindow, GREY, (150, 570), (65, 700), 10)
    pygame.draw.line(gameWindow, GREY, (250, 570), (165, 700), 10)

    #-House Shadow----#
    if (sunX <= 800):
        pygame.draw.polygon(gameWindow, BLACK, ((570, 520),(420, 520),(120, 570),(round(shadowPoint4[0]),round(shadowPoint4[1])),(round(shadowPoint5[0]),round(shadowPoint5[1]))))

    #-Front of House--#
    pygame.draw.rect(gameWindow, LIGHTRED, (120, 300, 300, 270))

    #-Doors-----------#
    pygame.draw.rect(gameWindow, BROWN, (150, 480, 100, 90))
    pygame.draw.rect(gameWindow, SAND, (155, 485, 43, 85))
    pygame.draw.rect(gameWindow, SAND, (202, 485, 43, 85))
    pygame.draw.circle(gameWindow, BROWN, (190, 530), 5)
    pygame.draw.circle(gameWindow, BROWN, (210, 530), 5)

    #-Windows---------#
    pygame.draw.rect(gameWindow, BROWN, (160, 490, 33, 15))
    pygame.draw.rect(gameWindow, GLASS, (163, 493, 27, 9))
    pygame.draw.rect(gameWindow, BROWN, (207, 490, 33, 15))
    pygame.draw.rect(gameWindow, GLASS, (210, 493, 27, 9))
    pygame.draw.rect(gameWindow, BROWN, (280, 500, 110, 40))
    pygame.draw.rect(gameWindow, GLASS, (285, 505, 100, 30))
    pygame.draw.line(gameWindow, BROWN, (335, 505), (335, 535),5)
    drawWindow(140, 320)
    drawWindow(140, 400)

    #-Roof----------#
    pygame.draw.polygon(gameWindow, DARKRED, ((120, 300), (420, 300), (270, 200)))
    pygame.draw.polygon(gameWindow, SAND, ((150, 290), (390, 290), (270, 220)))
    pygame.draw.polygon(gameWindow, DARKERRED, ((270, 200),(420, 300),(570, 250),(420, 150)))

    #-Side----------#
    pygame.draw.polygon(gameWindow, DARKERRED, ((420, 300), (420, 570), (570, 520), (570, 250))) 
    pygame.draw.polygon(gameWindow, WHITE, ((420, 300), (420, 330), (570, 280),(570, 250)))
    pygame.draw.polygon(gameWindow, WHITE, ((440, 293), (440, 563), (460, 557), (460, 287)))

    #-Trees---------#
    drawTree(660, 450, "triangle")
    drawTree(730, 400, "circle")
    drawTree(80, 370, "triangle")
    drawTree(30, 490, "circle")
    drawTree(60, 600, "triangle")
    drawTree(400, 620, "circle")
    drawTree(700, 600, "triangle")
    drawTree(600, 550, "circle")

    #-Fence---------#
    pygame.draw.line(gameWindow, WHITE, (0, 650), (70, 650), 10)
    pygame.draw.line(gameWindow, WHITE, (155, 650), (800, 650), 10)
    pygame.draw.arc(gameWindow, WHITE, (67, 575, 90, 100), 6.3, 3.14, 7)
    pygame.draw.line(gameWindow,WHITE, (68, 620), (67, 700), 7)
    pygame.draw.line(gameWindow,WHITE, (155, 620), (155, 700), 7)
    for x in range (0, 75, 15):
        pygame.draw.line(gameWindow, WHITE, (x, 625), (x, HEIGHT), 7)                 
        pygame.draw.polygon(gameWindow, WHITE, ((x, 610), (x - 3, 625), (x + 3, 625)))
    for x in range(165, WIDTH, 15):
        pygame.draw.line(gameWindow, WHITE, (x, 625), (x,HEIGHT), 7) 
        pygame.draw.polygon(gameWindow, WHITE, ((x, 610), (x - 3, 625), (x + 3, 625)))

    #====================================#
    # Animation
    #====================================#
    timer += 1

    #-Cloud Animation-#
    if (timer % 2 == 0):                       
        cloudX -= 1                                       
        cloudXTwo -= 1
        if (cloudX <= -100):                                                   
            cloudY = random.randint(50, 300)                              
            cloudX = 900
            cloudDrX = random.choice([1, -1])                      
            cloudDrY = random.choice([1, -1])
        if (cloudXTwo <= -100):
            cloudYTwo = random.randint(50, 300)
            cloudXTwo = 900

    #-Sun and Moon Animation---#
    if (timer % 5 == 0):                                     
        if (nightTime == False):
            sunY = round(0.0012 * (sunX - 400)**2 + 130)                     
            sunX += 1    
            if (sunX >= 900):                             
                nightTime = True
                moonX = -40
                if (crescent == 0):                                           
                    crescent += 25                       
                crescent += 10                               
                if (crescent >= 75):                   
                    crescent = 0                            
        elif (nightTime == True):
            moonY = round(0.0012 * (moonX - 400)**2 + 130)
            moonX += 1
            if (moonX >= 900):                             
                nightTime = False
                sunX = -40    

    #-Sky Animated Gradient---#
        if (sunX == -39):                
            skyRValue = 0                                     
            skyGValue = 36                                                              
            skyBValue = 67
        if (sunX <= 50):            
            skyRValue += 14/15
            skyGValue -= 8/45
            skyBValue += 109/90
        elif (sunX <= 600):               
            if (sunX <= 200):                        
                skyRValue -= 0.56
                skyGValue += 1.4
                skyBValue += 79/150                                   
        elif (sunX <= 650):                                   
            skyRValue += 5.1
            skyGValue += 0.2
            skyBValue -= 3.78
        elif (sunX <= 750):                        
            skyRValue -= 0.57
            skyGValue -= 1.25
            skyBValue -= 0.4
        elif (sunX <= 850):             
            skyRValue -= 1.98
            skyGValue -= 0.79
            skyBValue += 0.41

    #-Shadow Animation---#
        if (sunX <= 0):                          
            shadowPoint4 = [900, 530]                                             
            shadowPoint5 = [900, 530]              
        elif (sunX > 0 and sunX <= 200):                         
            shadowPoint4[1] += 0.55          
            shadowPoint5[1] += 0.1
        elif (sunX > 200 and sunX <= 400):
            shadowPoint4[0] -= 1.5
            shadowPoint4[1] += 1.05
            shadowPoint5[1] += 1.5
        elif (sunX > 400 and sunX <= 600):
            shadowPoint4[0] -= 3.5
            shadowPoint5[0] -= 3.8
        elif (sunX > 600 and sunX <= 800):
            shadowPoint4[1] -= 1.35
            shadowPoint5[0] -= 1.2
            shadowPoint5[1] -= 1.35

    #-Exit Program---#
    pygame.event.get()
    keys = pygame.key.get_pressed()
    if (keys[pygame.K_ESCAPE]):
        inPlay = False  

    pygame.display.update()