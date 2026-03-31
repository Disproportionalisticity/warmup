import pyautogui
import time

import keyboard as keeey

# from time import sleep
# from pynput import keyboard
# from pynput.keyboard import Key

def moveClick(x, sleepTime=0.05):
    pyautogui.moveTo(x[0], x[1])
    pyautogui.click()
    time.sleep(sleepTime)

confirm = (1600, 200)
dartInMenu = (1800, 230)
geraldoInMenu = (1700, 230)
monkeyMenuRightLeftArrow = (1310, 370)
monkeyMenuRightRightArrow = (1580, 370)
monkeyMenuLeftLeftArrow = (90, 370)
monkeyMenuLeftRightArrow = (360, 370)
swordPosition = (1070, 84)
restartPosition = (900, 500)

# dart61 = (293, 669) 
# dart62 = (1022, 190) 
# dart63 = (860, 977) 
dart61 = (298, 670) 
dart62 = (1030, 192) 
dart63 = (863, 974) 
dart63upgrade = (334, 633) 
dart81 = (223, 497)
dart91 = (1071, 1027)
dart101 = (265, 718)
geraldo = (1099, 170)

keeey.wait('l') 
while True:
    if keeey.is_pressed('s'):
        break
    
    if keeey.is_pressed('1'):
        for i in range(23):
            moveClick(swordPosition, 0.01)
        moveClick(restartPosition)
        
    if keeey.is_pressed('2'):
        moveClick(swordPosition)
        moveClick(restartPosition)
        
    if keeey.is_pressed('p'):
        print(pyautogui.position())
        
    if keeey.is_pressed('6'):
        moveClick(dartInMenu)
        moveClick(dart61)
        moveClick(confirm)
        moveClick(dart61)
        moveClick(monkeyMenuRightLeftArrow)
        moveClick(restartPosition)
        
        moveClick(dartInMenu)
        moveClick(dart62)
        moveClick(confirm)
        moveClick(dart62)
        moveClick(monkeyMenuLeftRightArrow)
        moveClick(restartPosition)
        
        moveClick(dartInMenu)
        moveClick(dart63)
        moveClick(confirm)
        moveClick(restartPosition)
        
    if keeey.is_pressed('7'):
        moveClick(dart63)
        moveClick(dart63upgrade)
        moveClick(restartPosition)
        
    if keeey.is_pressed('8'):
        moveClick(dartInMenu)
        moveClick(dart81)
        moveClick(confirm)
        moveClick(dart81)
        moveClick(monkeyMenuRightLeftArrow)
        moveClick(restartPosition)
        
    if keeey.is_pressed('9'):
        moveClick(dartInMenu)
        moveClick(dart91)
        moveClick(confirm)
        moveClick(dart91)
        moveClick(monkeyMenuLeftLeftArrow)
        moveClick(restartPosition)
        
    if keeey.is_pressed('0'):
        moveClick(dartInMenu)
        moveClick(dart101)
        moveClick(confirm)
        moveClick(restartPosition)
        
    if keeey.is_pressed('-'):
        moveClick(geraldoInMenu)
        moveClick(geraldo)
        moveClick(confirm)
        moveClick(restartPosition)
        
        



# print(pyautogui.position())
# time.sleep(2.5)
# print(pyautogui.position())
# time.sleep(2.5)
# print(pyautogui.position())

# pyautogui.moveTo(623, 507) # work
# pyautogui.moveTo(950, 580) # sleep night

# for i in range (200):
# 	print(str(i))
# 	pyautogui.click(950, 420, duration = 1.5)
# 	pyautogui.click(950, 420, duration = 1.5)
# 	pyautogui.click(950, 420, duration = 1.5)

# 	pyautogui.click(950, 420, duration = 1.5)
# 	pyautogui.click(950, 420, duration = 1.5)
# 	pyautogui.click(950, 420, duration = 1.5)

# 	pyautogui.click(950, 420, duration = 1.5)
# 	pyautogui.click(950, 420, duration = 1.5)
# 	pyautogui.click(950, 420, duration = 1.5)

# 	pyautogui.click(950, 580, duration = 1.5)
# 	pyautogui.click(950, 580, duration = 2)

# def on_press(key):
#     global stop_flag
#     if key == Key.a:
#         stop_flag = True

# listener = keyboard.Listener(on_press=on_press)
# listener.start()

# keeey.wait('l')

# for i in range(10000):
# 	pyautogui.mouseDown(button='right')
# 	if keeey.is_pressed("k"):
# 		pyautogui.mouseUp(button='right')
# 		print("k pressed, ending loop")
# 		break


	# pyautogui.keyDown('`')
	# pyautogui.keyDown('w')
	# pyautogui.keyDown('ctrl')
	# pyautogui.mouseDown(button='left')
	# if keeey.is_pressed("k"):
	# 	pyautogui.keyUp('`')
	# 	pyautogui.keyUp('w')
	# 	pyautogui.keyUp('ctrl')
	# 	pyautogui.mouseUp(button='left')
	# 	print("k pressed, ending loop")
	# 	break






# def accelerate():
#     pyautogui.keyDown('w') #holds the key down
#     pyautogui.keyUp('w') #releases the key
#     return 0