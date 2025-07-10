package com.aurionpro.model;
import java.util.Random;
import java.util.Scanner;

public class PIGGame {


	    public static void main(String[] args) {
	        new PIGGame().start();  // Create an instance and start the game
	    }

	    private final Scanner scanner = new Scanner(System.in);
	    private final Die die = new Die();
	    private final Score score = new Score();
	    private final Turn turn = new Turn();
	    private final int maximumTurns = 5;

	    public void start() {
	        printWelcomeMessage();
	        int turnCount = 0;

	        while (turnCount < maximumTurns && !score.hasReachedGoal()) {
	            turnCount++;
	            playTurn(turnCount);
	        }

	        printFinalResult(turnCount);
	        scanner.close();
	    }

	    private void printWelcomeMessage() {
	        System.out.println("Welcome to the Pig Dice Game!");
	        System.out.println("Get to 20 points in 5 turns or less.");
	        System.out.println("Rolling a 1 resets your total score to 0 and ends the turn.\n");
	    }

	    private void playTurn(int turnNumber) {
	        turn.reset();
	        System.out.println("TURN " + turnNumber);

	        while (!turn.isOver()) {
	            String userChoice = askPlayerChoice();

	            if (userChoice.equals("r")) {
	                int dieRoll = die.roll();
	                System.out.println("Die: " + dieRoll);

	                if (dieRoll == 1) {
	                    System.out.println("You rolled a 1! Score reset to 0. Turn over.\n");
	                    score.reset();
	                    turn.end();
	                    return;
	                }

	                turn.addPoints(dieRoll);

	                if (score.getValue() + turn.getTurnPoints() >= 20) {
	                    score.add(turn.getTurnPoints());
	                    printTurnSummary();
	                    turn.end();
	                }

	            } else if (userChoice.equals("h")) {
	                score.add(turn.getTurnPoints());
	                printTurnSummary();
	                turn.end();
	            }
	        }
	    }

	    private String askPlayerChoice() {
	        System.out.print("Roll or hold? (r/h): ");
	        String input = scanner.nextLine().trim().toLowerCase();

	        if (!input.equals("r") && !input.equals("h")) {
	            System.out.println("Invalid input. Please enter 'r' or 'h'.");
	            return askPlayerChoice();
	        }

	        return input;
	    }

	    private void printTurnSummary() {
	        System.out.println("Score for turn: " + turn.getTurnPoints());
	        System.out.println("Total score: " + score.getValue() + "\n");
	    }

	    private void printFinalResult(int turnCount) {
	        System.out.println("Game over!");
	        System.out.println("Final score: " + score.getValue());
	        System.out.println("Turns used: " + turnCount);

	        if (score.hasReachedGoal()) {
	            System.out.println("🎉 YOU WON!!!!");
	        } else {
	            System.out.println("😢 YOU DID NOT FINISH IN 5 TURNS");
	        }
	    }

	    // Die class
	    private static class Die {
	        private final Random random = new Random();

	        public int roll() {
	            return random.nextInt(6) + 1;
	        }
	    }

	    // Score class
	    private static class Score {
	        private int value = 0;

	        public void add(int points) {
	            value += points;
	        }

	        public void reset() {
	            value = 0;
	        }

	        public int getValue() {
	            return value;
	        }

	        public boolean hasReachedGoal() {
	            return value >= 20;
	        }
	    }

	    // Turn class
	    private static class Turn {
	        private int turnPoints = 0;
	        private boolean turnOver = false;

	        public void addPoints(int points) {
	            turnPoints += points;
	        }

	        public int getTurnPoints() {
	            return turnPoints;
	        }

	        public void end() {
	            turnOver = true;
	        }

	        public boolean isOver() {
	            return turnOver;
	        }

	        public void reset() {
	            turnPoints = 0;
	            turnOver = false;
	        }
	    }
	}

