package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante
	//testDECHENAIS-ALEXIS
	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		// Les montants ont été correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	void nImprimePasSiBalanceInsuffisante() {
		machine.insertMoney(PRICE-1);
		assertFalse(machine.printTicket(),"On ne doit pas imprimer si il n'y a pas assez d'argent");
	}

	@Test
	// S4 :  on imprime le ticket si le montant inséré est suffisant
	void imprimeSiMontantInsereSuffisant(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(),"ticket pas imprimé alors que la balance est suffisante");
	}
	@Test
	// S5 : Quand on imprime un ticket, la balance est décrémentée du prix du ticket
	void laBalanceEstDecrementeeDuPrixDuTicket() {
		machine.insertMoney(PRICE+1); // Insertion supérieure au prix du ticket
		machine.printTicket();
		assertEquals(1, machine.getBalance(), "La balance n'a pas été correctement décrémentée");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void leMontantEstMisAJourApresLImpressionDuTicket() {
		machine.insertMoney(PRICE);
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le montant collecté n'a pas été correctement mis à jour");
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	void refundRendBienLArgent() {
		machine.insertMoney(30);
		assertEquals(30, machine.refund(), "Le montant remboursé est incorrect");
	}

	@Test
	// S8 : refund() remet la balance à zéro
	void refundRemetBienLaBalanceAZero() {
		machine.insertMoney(30);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance n'a pas été réinitialisée après remboursement");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void pasDeValeursNegativesInseree() {
		assertThrows(IllegalArgumentException.class, () -> machine.insertMoney(-10),
				"Une exception aurait dû être levée pour un montant négatif");
		assertEquals(0, machine.getBalance(), "La balance a changé suite à une insertion négative");
	}

	@Test
	// S10 : on ne peut pas créer de machine avec un prix de ticket négatif
	void onNePeutPasCreerDeMachineAvecPrixNegatif() {
		assertThrows(IllegalArgumentException.class, () -> new TicketMachine(-50),
				"Une exception aurait dû être levée pour un prix de ticket négatif");
	}

}
