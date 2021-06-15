SELECT * FROM client;
SELECT * FROM chambre;
SELECT * FROM reservechambre;
SELECT * FROM commodite;
SELECT * FROM possedecommodite;

Select  r.*,(c.prixbase + coalesce(SUM(co.prix),0)) as prixTotal
from reservechambre r
         JOIN chambre c on r.idchambre = c.idchambre
         LEFT JOIN possedecommodite p on c.idchambre = p.idchambre
         LEFT JOIN commodite co on co.idcommodite = p.idcommodite
where r.idclient =1
GROUP BY c.prixbase, r.idreservation;