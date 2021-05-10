package ie.gmit.bio;

public enum AntimicrobialPeptide { //A classification of AMPs for the NN	
	Abaecin 		(0.0d),		//24 sequences
	AlphaDefensin 	(1.0d),		//31
	Ascaphin 		(2.0d),		//15
	Aurein 			(3.0d),		//42
	Bacteriocin 	(4.0d),
	BetaDefensin 	(5.0d),
	Bombinin 		(6.0d),
	Brevinin 		(7.0d),
	Caerin 			(8.0d),
	Cathelicidin 	(9.0d),
	Cecropin 		(10.0d),	
	Clavanin 		(11.0d),
	Coleoptericin 	(12.0d),
	Cupiennin 		(13.0d),	
	Cyclotide 		(14.0d),	
	Cystatin 		(15.0d),
	Dermaseptin 	(16.0d),
	Esculentin 		(17.0d),
	Formaecin 		(18.0d),	
	Hevein 			(19.0d),
	Histone 		(20.0d),
	Lectin 			(21.0d),
	Magainin 		(22.0d),
	Mastoparan 		(23.0d),
	Ocellatin 		(24.0d),
	Palustrin 		(25.0d),
	Phylloseptin 	(26.0d),
	Pseudin 		(27.0d),	
	Ribosome 		(28.0d),
	Rugosin 		(29.0d),	
	Snakin 			(30.0d),	
	Temporin 		(31.0d),
	Thionin 		(32.0d),
	Unknown 		(33.0d);
	
	private final double amp;

    private AntimicrobialPeptide(double amp) {
        this.amp = amp;
    }
	
    public double getAntimicrobialPeptide() {
        return this.amp;
    }
}