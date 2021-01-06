--IJ commands to create database
CONNECT 'jdbc:derby:LMS;create=true';
RUN 'users_tbl.sql';
RUN 'books_tbl.sql';
RUN 'issued_books_tbl.sql';
RUN 'gen_users.sql';
RUN 'gen_books.sql';