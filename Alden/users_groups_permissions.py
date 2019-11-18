#!/usr/bin/python3
# -*- coding: utf-8 -*-

from faker import Faker
from dbconnection import DbConnection

class Users:

    table_name = "users"

    def create(self, num_insert = 1):
        db = DbConnection()
        fkr = Faker()
        row = db.fetch(f"select max(user_id) as next_id from dbo.{self.table_name}")
        
        table_id = 0
        for r in row:
            if r is not None and r[0] is not None:
                table_id = int(r[0])

        insert = []
        while num_insert > 0:
            table_id += 1
            insert.append(f"('{table_id}'," \
            f"CONCAT('w',{str(table_id)}), " \
            f"'{fkr.first_name()}', " \
            f"'{fkr.last_name()}', " \
            f"'{fkr.street_address()}', " \
            f"'{fkr.city()}', " \
            f"'{fkr.state_abbr()}', " \
            f"'{fkr.postcode_in_state()}', " \
            f"'{fkr.numerify('##########')}')")
            num_insert -= 1

        insert_sql = f"INSERT INTO {self.table_name} (user_id, w_num, first_name, last_name, address, city, state, postalcode, phone) VALUES " + ",".join(insert)
        print(insert_sql)
        db.execute(insert_sql)


class Groups:

    table_name = "groups"

    def create(self):
        db = DbConnection()
        row = db.fetch(f"select max(group_id) as next_id from dbo.{self.table_name}")
        
        table_id = 0
        for r in row:
            if r is not None and r[0] is not None:
                table_id = int(r[0])

        insert = []
        table_id += 1
        insert.append(f"('{table_id}', 'Admin', '0')")
        table_id += 1
        insert.append(f"('{table_id}', 'Instructor', '0')")
        table_id += 1
        insert.append(f"('{table_id}', 'Student', '0')")

        insert_sql = f"INSERT INTO {self.table_name} (group_id, name, deleted) VALUES " + ",".join(insert)
        print(insert_sql)
        db.execute(insert_sql)


class Permissions:

    table_name = "permissions"

    def create(self):
        db = DbConnection()
        fkr = Faker()
        row = db.fetch(f"select max(permission_id) as next_id from dbo.{self.table_name}")
        
        table_id = 1
        for r in row:
            if r is not None and r[0] is not None:
                table_id = int(r[0])

        p = []
        p.append('Student')
        p.append('Instructor')
        i = 20
        while i > 0:
            p.append(f"{fkr.job()}") # other jobs that students and instructors may have on campus
            i -= 1

        p = list(set(p)) # this removes any duplicates from the list
        insert = []
        for r in p:
            table_id += 1
            insert.append(f"('{table_id}', '{r}', '0')")
            db.execute(f"UPDATE {self.table_name} SET deleted = '1' WHERE permission_name = '{r}'")

        insert_sql = f"INSERT INTO {self.table_name} (permission_id, permission_name, deleted) VALUES " + ",".join(insert)
        db.execute(insert_sql)


class GroupsPermissions:

    table_name = "group_permissions"

    def create(self, num_instructors = 10):
        db = DbConnection()
        fkr = Faker()
        row = db.fetch(f"select max(group_permission_id) as next_id from dbo.{self.table_name}")
        
        table_id = 1
        for r in row:
            if r is not None and r[0] is not None:
                table_id = int(r[0])

        db.execute(f"DELETE FROM {self.table_name} WHERE name IN ('Instructor', 'Student')")

        instructor_result = db.fetch(f"SELECT permission_id FROM dbo.permissions WHERE permission_name = 'Instructor'")
        for row in instructor_result:
            permission_id = row[0]

        instructor_result = db.fetch(f"SELECT TOP {num_instructors} user_id FROM dbo.users ORDER BY user_id")
        insert = []
        for row in instructor_result:
            table_id += 1
            user_id = row[0]
            insert.append(f"('{table_id}', '{user_id}', '{permission_id}', 'Instructor', '0')")

        instructor_result = db.fetch(f"SELECT permission_id FROM dbo.permissions WHERE permission_name = 'Student'")
        for row in instructor_result:
            permission_id = row[0]

        user_result = db.fetch(f"SELECT user_id FROM dbo.users WHERE user_id > {user_id} ORDER BY user_id")
        for row in user_result:
            table_id += 1
            user_id = row[0]
            insert.append(f"('{table_id}', '{user_id}', '{permission_id}', 'Student', '0')")

        insert_sql = f"INSERT INTO {self.table_name} (group_permission_id, user_id, permission_id, name, deleted) VALUES " + ",".join(insert)
        db.execute(insert_sql)