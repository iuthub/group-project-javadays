--IJ commands to create database
CONNECT 'jdbc:derby:LMS;create=true';
RUN 'users.sql';
RUN 'gen_users.sql';