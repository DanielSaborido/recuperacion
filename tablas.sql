CREATE TABLE NOTAS (
                      alumno VARCHAR(250) NOT NULL,
                      tareas INT NOT NULL,
                      examen INT NOT NULL,
                      cuestionario INT NOT NULL,
                      PRIMARY KEY (alumno)
);

CREATE TABLE RAs (
                     alumno VARCHAR(250) NOT NULL,
                     UD INT NOT NULL AUTO_INCREMENT,
                     porcentaje INT,
                     resultado INT,
                     PRIMARY KEY (UD),
                     FOREIGN KEY (alumno) REFERENCES NOTAS(alumno)
);

CREATE TABLE EVALUACIONES (
                     alumno VARCHAR(250) NOT NULL,
                     iniciales VARCHAR(5) NOT NULL,
                     notamedia INT,
                     PRIMARY KEY (alumno),
                     FOREIGN KEY (alumno) REFERENCES NOTAS(alumno)
);