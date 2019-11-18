#!/usr/bin/python3
# -*- coding: utf-8 -*-

import pyodbc

class DbConnection:

    db_server = 'CLSLC4553ALDENP\DEVELOPER_SERVER'
    db_name = 'Higby_Park_Starks'
    db_uid = 'testuser'
    db_pass = 'test'

    def __init__(self, *args, **kwargs):
        self.conn = pyodbc.connect('Driver={ODBC Driver 17 for SQL Server};'
                      f'SERVER={self.db_server};'
                      f'DATABASE={self.db_name};'
                      f'UID={self.db_uid};PWD={self.db_pass}')

    def execute(self, sql):
        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

    def fetch(self, sql):
        cursor = self.conn.cursor()
        cursor.execute(sql)
        return cursor.fetchall()
