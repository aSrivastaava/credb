import main
import time
# This 'while-loop' is for running the program and update the database in a particular interval.
while(True):
    main.engine()
    time.sleep(1)               #Pause the execution for 1 sec.
