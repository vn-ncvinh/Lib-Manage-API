import mysql.connector
from mysql.connector import Error
try:
    connection = mysql.connector.connect(host='localhost',
                                        database='thuvien',
                                        user='ncvinh',
                                        password='vinh2000')
except Error as e:
    print("Error while connecting to MySQL", e)