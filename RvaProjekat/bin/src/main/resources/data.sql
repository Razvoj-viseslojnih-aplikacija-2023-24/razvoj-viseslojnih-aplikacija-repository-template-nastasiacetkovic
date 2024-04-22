
insert into banka(id, naziv, kontakt, pib)
values(nextval('banka_seq'), 'Unicredit', '0692001024', '12422' ),
	  (nextval('banka_seq'), 'Raiffasien', '0652417362', '67521' ),
		(nextval('banka_seq'), 'Komercijalna banka', '06126375132', '892712' ),
	(nextval('banka_seq'), 'Addiko', '06261528322', '22115' );

insert into korisnik_usluge(id, ime, prezime, maticni_broj)
values(nextval('korisnikusluge_seq'), 'Luka', 'Petkovic', '2410002805147' ),
	(nextval('korisnikusluge_seq'), 'Marija', 'Aleksic', '237182673927' ),
	(nextval('korisnikusluge_seq'), 'Milos', 'Bumbic', '190729108273' ),
	(nextval('korisnikusluge_seq'), 'Anja', 'Milosevic', '0918297382912' );

insert into filijala(id, adresa, broj_pultova, poseduje_sef, banka)
values(nextval('filijala_seq'), 'Zeleznicka 3', 5, true, 1 ),
	  (nextval('filijala_seq'), 'Cara Lazara 88', 9, false, 3 ),
       (nextval('filijala_seq'), 'Tone Hadzica 12', 3, false, 2 ),
		(nextval('filijala_seq'), 'Narodnog fronta 32', 7, true, 4 );

insert into usluga(id, naziv, opis_usluge, provizija, datum_ugovora, filijala, korisnik_usluge)
values(nextval('usluga_seq'), 'Otvaranje racuna', 'Otvaranje studentskog racuna radi stipendije', 6, to_date('12.12.2023','dd.mm.yyyy.'), 3, 1),
	(nextval('usluga_seq'), 'Podizanje gotovine', 'Podizanje gotovine radi kupovine nekretnine', 18, to_date('08.02.2024','dd.mm.yyyy.'), 1, 4),
	(nextval('usluga_seq'), 'Uzimanje kredita', 'Uzimanje kredita radi kupovine imovine', 12, to_date('09.07.2024','dd.mm.yyyy.'), 2, 2),
	(nextval('usluga_seq'), 'Transfer sredstava', 'Transfer sredstava po ugovoru', 8, to_date('11.06.2024','dd.mm.yyyy.'), 1, 3);
