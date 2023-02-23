package com.example.puzzle8gui;

import java.util.*;

public class Game {
    public static int movesSoFar;
    public static int nodeCount;
    public static ArrayList<Integer[]> path = new ArrayList<>();



    private static String lastMove;
    private static final Dictionary<Integer, Integer> maxMoves = new Hashtable<>();
    private static ArrayList<Integer> tempMoves = new ArrayList<>();



    public static boolean start(Integer[] startingMatrix, Integer[] endingMatrix) {
        maxMoves.put(9, 31);
        maxMoves.put(16, 80);
        maxMoves.put(25, 1000);
        maxMoves.put(36, 1000);
        maxMoves.put(49, 1000);


        movesSoFar = 0;
        nodeCount = 1;
        lastMove = "N";

        path.clear();
        tempMoves.clear();

        path.add(startingMatrix);
        Box startingBox = new Box(startingMatrix);

        return helperMethod(startingBox, endingMatrix);
    }

    private static boolean helperMethod(Box currentBox, Integer[] endingMatrix) {
        if (Arrays.equals(currentBox.getMatrix(), endingMatrix)) return true;
        if (movesSoFar > maxMoves.get(endingMatrix.length)) return false;


        int indexOfNull = -1;
        for (int i=0; i<currentBox.getMatrix().length; i++) {
            if (currentBox.getMatrix()[i] == null) indexOfNull = i;
        }
        if (indexOfNull == -1) {
            return false;
        }


        int baseNumber = (int)Math.sqrt(currentBox.getMatrix().length);

        if ((indexOfNull+1) % baseNumber == 0 || lastMove.equals("L")) currentBox.setRight(null);
        else {
            Integer[] rightMatrix = new Integer[currentBox.getMatrix().length];
            System.arraycopy(currentBox.getMatrix(), 0, rightMatrix, 0, currentBox.getMatrix().length);
            int helperVariable = rightMatrix[indexOfNull+1];
            rightMatrix[indexOfNull+1] = null;
            rightMatrix[indexOfNull] = helperVariable;
            currentBox.setRight(new Box(rightMatrix));
            nodeCount++;
        }
        if (indexOfNull % baseNumber == 0 || lastMove.equals("R")) currentBox.setLeft(null);
        else {
            Integer[] leftMatrix = new Integer[currentBox.getMatrix().length];
            System.arraycopy(currentBox.getMatrix(), 0, leftMatrix, 0, currentBox.getMatrix().length);
            int helperVariable = leftMatrix[indexOfNull-1];
            leftMatrix[indexOfNull-1] = null;
            leftMatrix[indexOfNull] = helperVariable;
            currentBox.setLeft(new Box(leftMatrix));
            nodeCount++;
        }
        if ((indexOfNull / baseNumber) < 1 || lastMove.equals("D")) currentBox.setUp(null);
        else {
            Integer[] upMatrix = new Integer[currentBox.getMatrix().length];
            System.arraycopy(currentBox.getMatrix(), 0, upMatrix, 0, currentBox.getMatrix().length);
            int helperVariable = upMatrix[indexOfNull-baseNumber];
            upMatrix[indexOfNull-baseNumber] = null;
            upMatrix[indexOfNull] = helperVariable;
            currentBox.setUp(new Box(upMatrix));
            nodeCount++;
        }
        if (indexOfNull / baseNumber >= baseNumber-1 || lastMove.equals("U")) currentBox.setDown(null);
        else {
            Integer[] downMatrix = new Integer[currentBox.getMatrix().length];
            System.arraycopy(currentBox.getMatrix(), 0, downMatrix, 0, currentBox.getMatrix().length);
            int helperVariable = downMatrix[indexOfNull+baseNumber];
            downMatrix[indexOfNull+ baseNumber] = null;
            downMatrix[indexOfNull] = helperVariable;
            currentBox.setDown(new Box(downMatrix));
            nodeCount++;
        }


        Integer[] costs = new Integer[4];
        if (currentBox.getRight() != null) costs[0] = calculateCost(currentBox.getRight(), endingMatrix);
        else costs[0] = 10000;
        if (currentBox.getLeft() != null) costs[1] = calculateCost(currentBox.getLeft(), endingMatrix);
        else costs[1] = 10000;
        if (currentBox.getUp() != null) costs[2] = calculateCost(currentBox.getUp(), endingMatrix);
        else costs[2] = 10000;
        if (currentBox.getDown() != null) costs[3] = calculateCost(currentBox.getDown(), endingMatrix);
        else costs[3] = 10000;

        int minCost = minFunc(costs);
        ArrayList<Integer> indexArray = new ArrayList<>();
        for (int i=0; i<4; i++) {
            if (costs[i] == minCost) indexArray.add(i);
        }


        for (int each : indexArray) {
            switch (each) {
                case 0 -> {
                    tempMoves.add(movesSoFar++);

                    lastMove = "R";
                    path.add(currentBox.getRight().getMatrix());
                    if (helperMethod(currentBox.getRight(), endingMatrix)) return true;

                    movesSoFar = tempMoves.get(tempMoves.size()-1);
                    tempMoves.remove(tempMoves.size()-1);

                    path.remove(path.size()-1);
                }
                case 1 -> {
                    tempMoves.add(movesSoFar++);

                    lastMove = "L";
                    path.add(currentBox.getLeft().getMatrix());
                    if (helperMethod(currentBox.getLeft(), endingMatrix)) return true;

                    movesSoFar = tempMoves.get(tempMoves.size()-1);
                    tempMoves.remove(tempMoves.size()-1);

                    path.remove(path.size()-1);
                }
                case 2 -> {
                    tempMoves.add(movesSoFar++);

                    lastMove = "U";
                    path.add(currentBox.getUp().getMatrix());
                    if (helperMethod(currentBox.getUp(), endingMatrix)) return true;

                    movesSoFar = tempMoves.get(tempMoves.size()-1);
                    tempMoves.remove(tempMoves.size()-1);

                    path.remove(path.size()-1);
                }
                case 3 -> {
                    tempMoves.add(movesSoFar++);

                    lastMove = "D";
                    path.add(currentBox.getDown().getMatrix());
                    if (helperMethod(currentBox.getDown(), endingMatrix)) return true;

                    movesSoFar = tempMoves.get(tempMoves.size()-1);
                    tempMoves.remove(tempMoves.size()-1);

                    path.remove(path.size()-1);
                }
            }
        }
        return false;
    }

    private static int calculateCost(Box box, Integer[] endingMatrix) {
        Integer[] currentMatrix = box.getMatrix();
        int costOfCurrentMatrix = 0;
        for (int i=0; i<currentMatrix.length; i++) {
            if (!Objects.equals(currentMatrix[i], endingMatrix[i])) {
                costOfCurrentMatrix++;
            }
        }

        return costOfCurrentMatrix + movesSoFar;
    }
    private static int minFunc(Integer[] array) {
        int min = array[0];
        for (Integer each : array) {
            if (each != null && each < min) {
                min = each;
            }
        }
        return min;
    }
}
