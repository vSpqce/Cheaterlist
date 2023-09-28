import os
import subprocess
os.system('cls')

while True:
    dmy = input("DD/MM/YY: ")
    array = dmy.split("/")
    if (len(array) != 3):
        break
    mdy = f'{array[1]}/{array[0]}/{array[2]}'
    print(mdy)
    subprocess.run(f"echo {mdy}|clip", shell=True) # copy to clipboard (Windows)