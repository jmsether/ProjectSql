#!/usr/bin/python3
# -*- coding: utf-8 -*-

from faker import Faker
from dbconnection import DbConnection

class Assignments:

    table_name = "assignments"

    def create(self, class_num, num_insert = 1):
        db = DbConnection()
        fkr = Faker()
        row = db.fetch(f"select max(assignment_id) as next_id from dbo.{table_name}")
        
        if row is None:
            table_id = 1
        else:
            for r in row:
                table_id = r[0]

        insert = []
        while num_users > 0:
            table_id  += 1
            insert.append(f"('{table_id}'," \
            f"'{class_num}', " \
            f"'{}', " \
            f"'{}', " \
            f"'{}', " \
            ")")
            num_insert -= 1

        insert_sql = f"INSERT INTO {table_name} (assignment_id, class_id, name, due_date, points) VALUES " + ",".join(insert)
        print(insert_sql)
        db.execute(insert_sql)

        


