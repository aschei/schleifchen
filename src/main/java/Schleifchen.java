import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Schleifchen {

	private int numberOfPlayers;

	List<String> group1 = new ArrayList<>(numberOfPlayers % 2),
			group2 = new ArrayList<>(numberOfPlayers % 2);

	Set<String> usedPairs = new HashSet<>();
	List<String> currentPairs = new ArrayList<>();

	Set<String> usedOpponents = new HashSet<>();
	Set<String> usedGroup2Opponents = new HashSet<>();

	public Schleifchen(int numberOfPlayers) {
		if (Integer.bitCount(numberOfPlayers) != 1) {
			numberOfPlayers = Integer.highestOneBit(numberOfPlayers) * 2;
		}
//		numberOfPlayers += numberOfPlayers % 2; 
		this.numberOfPlayers = numberOfPlayers;
		System.out.println("Creating Schleifchen for " + numberOfPlayers
				+ " players");

	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("call with <number of players>");
			System.exit(1);
		}
		String numberOfPlayersString = args[0];
		int numberOfPlayers = Integer.parseInt(numberOfPlayersString);
		new Schleifchen(numberOfPlayers).play();
	}

	private void play() {
		int maximumRounds = numberOfPlayers / 2 -1;
		createGroups();
		for (int i = 0; i < maximumRounds; i++) {
			System.out.println("\nRound " + (i + 1));
			createPairs();
			createOpponents();
		}
	}

	private void createOpponents() {
		StringBuilder opponents = new StringBuilder("Opponents: ");
		StringBuilder warnings = new StringBuilder("Warnings: ");
		Set<Integer> usedPlayers = new HashSet<>();
		for (int i = 0; i < group1.size(); i++) {
			if (usedPlayers.contains(i)) {
				continue;
			}
			String player11 = group1.get(i);
			for (int k = i + 1; k < group1.size(); k++) {
				if (usedPlayers.contains(k)) {
					continue;
				}
				String player21 = group1.get(k);
				String group1Opponents = player11 + "-" + player21;
				if (usedOpponents.add(group1Opponents)) {
					usedPlayers.add(i);
					usedPlayers.add(k);
					opponents.append(currentPairs.get(i)).append("-").append(currentPairs.get(k)).append("; ");
					// Just to sanitize
					String pair1 = currentPairs.get(i);
					String player12 = pair1.substring(pair1.indexOf(','), pair1.length());
					String pair2 = currentPairs.get(k);
					String player22 = pair2.substring(pair2.indexOf(','), pair2.length());
					String group2Opponents = player12 + "-" + player22;
					if (!usedGroup2Opponents.add(group2Opponents)) {
						warnings.append("Players of second group have been opponents before: "+group2Opponents);
					}
					break;
				}
			}
		}
		System.out.println(opponents);
	}

	private void createPairs() {
		currentPairs.clear();
		List<String> currentGroup2 = new ArrayList<>();
		currentGroup2.addAll(group2);
		for (String playerOne : group1) {
			for (String playerTwo : currentGroup2) {
				String pair = playerOne + "," + playerTwo;
				if (usedPairs.add(pair)) {
					currentPairs.add(pair);
					currentGroup2.remove(playerTwo);
					break;
				}
			}
		}
		StringBuilder pairs = new StringBuilder("Pairs: ");
		for (String pair : currentPairs) {
			pairs.append(pair).append("; ");
		}
		System.out.println(pairs);
	}

	private void createGroups() {
		for (int i = 0; i < numberOfPlayers / 2; i++) {
			group1.add(Integer.toString(i + 1));
			group2.add(Character.toString((char) ('A' + i)));
		}

	}
}
