CREATE TABLE Client
(
    idClient SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL
);

CREATE TABLE Chambre
(
    idChambre SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    prixBase DECIMAL NOT NULL
);

CREATE TABLE ReserveChambre
(
    idClient INTEGER REFERENCES Client(idClient),
    idChambre INTEGER REFERENCES Chambre(idChambre),
    DateDebut DATE NOT NULL,
    DateFin DATE NOT NULL,
    CONSTRAINT pk_clientChambre PRIMARY KEY(idClient,idChambre),
    CONSTRAINT date CHECK (DateDebut <= DateFin)
);

CREATE TABLE Commodite
(
    idCommodite SERIAL PRIMARY KEY,
    Description VARCHAR(255) NOT NULL,
    Prix DECIMAL NOT NULL
);

CREATE TABLE PossedeCommodite
(
    idCommodite INTEGER REFERENCES Commodite(idCommodite),
    idChambre INTEGER REFERENCES Chambre(idChambre),
    CONSTRAINT pk_commoditeChambre PRIMARY KEY(idCommodite,idChambre)
);
