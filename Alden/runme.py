#!/usr/bin/python3
# -*- coding: utf-8 -*-

# install python3 change to directory where python is installed and go into the scripts directory ie: cd C:\Program Files (x86)\Python37-32\Scripts 
# pip install pyodbc
# pip install faker
# run command prompt, go to dir where script is located at and run python runme.py (this file can also be changed to what ever name you want)
# you can also just run python and then run the script from python with the following: import runme; runme.start()

from users_groups_permissions import *

def start():
    u = Users()
    u.create(200)
    print("Created Users")

    #c = Classes()
    #c.create(10)

    #for row in c.get_classes(10):
    #    a = Assignment()
    #    a.create(row[0], 7)

    g = Groups()
    g.create()
    print("Created Groups")

    p = Permissions()
    p.create()
    print("Created Permissions")

    gp = GroupsPermissions()
    gp.create()
    print("Created GroupsPermissions")

    # return 20 students
    # return teacher
    # associate with class

    # create assignments for class
    # create graded assignments

# this auto runs the start() method when executing this python file no matter what you name this file
if __name__ == '__main__':
    start()