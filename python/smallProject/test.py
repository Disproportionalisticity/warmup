import pyautogui
import time
import keyboard as keeey

from time import sleep
from pynput import keyboard
from pynput.keyboard import Key

# print(pyautogui.position())
# time.sleep(2.5)
# print(pyautogui.position())
# time.sleep(2.5)
# print(pyautogui.position())

sleep_duration = 0.25
keeey.wait('l') 
for i in range (12):
    print(str(i))
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    pyautogui.click(x=484, y=483, duration = sleep_duration)
    
    pyautogui.click(x=482, y=564, duration = sleep_duration)
    pyautogui.click(x=482, y=564, duration = sleep_duration)
    pyautogui.click(x=482, y=564, duration = sleep_duration)

# pyautogui.moveTo(950, 420) # work
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

# keeey.wait('e')

# for i in range(10000):
# 	pyautogui.press('e')
# 	if keeey.is_pressed("q"):
# 		print("q pressed, ending loop")
# 		break