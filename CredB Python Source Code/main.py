import os                       #To use the os operations.


def engine():                             #Checks all the directories and files in a particular drive.
    drives = ["D:", "H:", "I:", "J:"]
    out = open(r'C:\xampp\htdocs\credb\File_Index.txt', 'w+')      #Object for file.
    for i in drives:
        for root, dirs, files in os.walk(i):
            for name in files:
                out.write(name + "\n\t")
                out.write(os.path.join(root, name) + "\n\n")
            for name in dirs:
                out.write(name + "\n\t")
                out.write(os.path.join(root, name) + "\n\n")
    out.close()
