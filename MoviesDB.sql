set SERVEROUTPUT on;

CREATE TABLE Movies (
    MovieID INT PRIMARY KEY,
    Title VARCHAR2(100),
    Genre VARCHAR2(50),
    ReleaseDate DATE,
    Director VARCHAR2(100)
);

-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (1, 'Pathaan', 'Action', TO_DATE('2023-01-25', 'YYYY-MM-DD'), 'Siddharth Anand');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (2, 'Jawan', 'Action', TO_DATE('2023-09-07', 'YYYY-MM-DD'), 'Atlee Kumar');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (3, 'Gadar 2', 'Drama', TO_DATE('2023-08-11', 'YYYY-MM-DD'), 'Anil Sharma');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (4, 'Zara Hatke Zara Bachke', 'Romantic Comedy', TO_DATE('2023-06-02', 'YYYY-MM-DD'), 'Laxman Utekar');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (5, 'Rocky Aur Rani Ki Prem Kahani', 'Romantic Drama', TO_DATE('2023-07-28', 'YYYY-MM-DD'), 'Karan Johar');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (6, 'Dono', 'Romantic Comedy', TO_DATE('2023-10-12', 'YYYY-MM-DD'), 'Avnish Barjatya');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (7, 'Dhamaka', 'Thriller', TO_DATE('2023-11-03', 'YYYY-MM-DD'), 'Ram Madhvani');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (8, 'Chandigarh Kare Aashiqui', 'Romantic Drama', TO_DATE('2023-12-22', 'YYYY-MM-DD'), 'Abhishek Kapoor');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (9, 'Lal Kaptaan', 'Action', TO_DATE('2023-04-19', 'YYYY-MM-DD'), 'Navdeep Singh');
-- INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) VALUES (10, 'Uunchai', 'Drama', TO_DATE('2023-11-11', 'YYYY-MM-DD'), 'Sooraj R. Barjatya');
select * from movies;

CREATE OR REPLACE PROCEDURE add_movie (
    p_id IN NUMBER,
    p_title IN VARCHAR2,
    p_genre IN VARCHAR2,
    p_releaseDate IN DATE,
    p_director IN VARCHAR2
) AS
BEGIN
    BEGIN
        INSERT INTO Movies (MovieID, Title, Genre, ReleaseDate, Director) 
        VALUES (p_id, p_title,p_genre,p_releaseDate, p_director);
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            RAISE_APPLICATION_ERROR(-20001, 'Error: MovieID already exists.');
        WHEN OTHERS THEN
            RAISE_APPLICATION_ERROR(-20002, 'Unexpected error: ' || SQLERRM);
    END;
END;
/

CREATE OR REPLACE PROCEDURE read_one_movie (
    p_id IN NUMBER,
    p_title OUT VARCHAR2,
    p_genre OUT VARCHAR2,
    p_releaseDate OUT DATE,
    p_director OUT VARCHAR2
) AS
BEGIN
    BEGIN
        SELECT Title, Genre, ReleaseDate, Director
        INTO p_title, p_genre, p_releaseDate, p_director
        FROM Movies
        WHERE MovieID = p_id;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_title := NULL;
            p_genre := NULL;
            p_releaseDate := NULL;
            p_director := NULL;
            DBMS_OUTPUT.PUT_LINE('Error: No movie found with the provided ID.');
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Unexpected error: ' || SQLERRM);
    END;
END;
/

CREATE OR REPLACE PROCEDURE update_movie (
    p_id IN NUMBER,
    p_title IN VARCHAR2,
    p_genre IN VARCHAR2,
    p_releaseDate IN DATE,
    p_director IN VARCHAR2
) AS
BEGIN
    UPDATE movies
    SET Title = p_title, 
    Genre = p_genre, 
    ReleaseDate = p_releaseDate, 
    Director = p_director
    WHERE MovieId = p_id;
    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: No Movie found with the provided ID.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE delete_movie (
    p_id IN NUMBER
) AS
BEGIN
    DELETE FROM movies
    WHERE MovieId = p_id;
    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: No Movie found with the provided ID.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error: ' || SQLERRM);
END;
/


CREATE OR REPLACE FUNCTION get_movies_by_director (
    p_director IN VARCHAR2
) RETURN SYS_REFCURSOR
AS
    v_cursor SYS_REFCURSOR;
BEGIN
    OPEN v_cursor FOR
        SELECT MovieID, Title, Genre, ReleaseDate, Director
        FROM Movies
        WHERE Director = p_director;

    RETURN v_cursor;
END;
/


CREATE or REPLACE VIEW action_movies AS
SELECT MovieID, Title, ReleaseDate, Director
FROM Movies
where Genre = 'Action';

CREATE OR REPLACE PROCEDURE read_all_movies (
    p_movies OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN p_movies FOR
        SELECT MovieID, Title, Genre, ReleaseDate, Director
        FROM Movies;
END;
/



