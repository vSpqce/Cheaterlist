# Number.
# IGN: 
# Discord: []
# Cheats used: 
# Date: 
# Caught by: 
# Positive?: 
# Comment: 
# Evidence:
import os
import subprocess
os.system('cls')

number = input("listing #: ")
ign = input("IGN: ")
discord = input("discord (username, id): ")
if (discord == "unknown" or discord == "unk"):
    discordUsername = "unknown"
    id = ""
else:
    discordUsername, id = discord.split(", ")

cheatsUsed = input("Cheats used: ")
date = input("Date (month/day/year): ")
caughtBy = input("Caught by: ")
positivity = input("Positive?: ")
comment = input("Comment: ")

os.system('cls')

listing = f"""
{number}.
IGN: {ign}
Discord: {discordUsername} [{id}]
Cheats used: {cheatsUsed}
Date: {date}
Caught by: {caughtBy}
Positive?: {positivity}%
Comment: {comment}
Evidence:


[original evidence]

"""
print(listing)
# subprocess.run(f"echo {listing}|clip", shell=True) # copy to clipboard (Windows). This is currently not working. I have an idea as to why but I don't care enough rn to deal with it