package com.example.a2340team25game.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Leaderboard implements Serializable {
    private static Leaderboard leaderboard;

    private LeaderboardNode rootScore;

    private Leaderboard() {
        rootScore = null;
    }

    public static Leaderboard getInstance() {
        if (leaderboard == null) {
            synchronized (Leaderboard.class) {
                if (leaderboard == null) {
                    leaderboard = new Leaderboard();
                }
            }
        }
        return leaderboard;
    }

    public void addOrUpdateScore(Score score) {
        rootScore = addOrUpdateRecursiveHelper(rootScore, score);
    }

    private LeaderboardNode addOrUpdateRecursiveHelper(LeaderboardNode curr, Score score) {
        if (curr == null) {
            return new LeaderboardNode(score);
        }

        if (score.getName().equals(curr.getScore().getName())) {
            // If player name matches, update score value
            curr.getScore().setValue(Math.max(curr.getScore().getValue(), score.getValue()));
        } else if (score.compareTo(curr.getScore()) > 0) {
            curr.setRight(addOrUpdateRecursiveHelper(curr.getRight(), score));
        } else {
            curr.setLeft(addOrUpdateRecursiveHelper(curr.getLeft(), score));
        }

        updateLeaderboardNode(curr);
        return balanceLeaderboard(curr);
    }

    private void updateLeaderboardNode(LeaderboardNode curr) {
        int rHeight = -1;
        int lHeight = -1;
        if (curr.getRight() != null) {
            rHeight = curr.getRight().getHeight();
        }
        if (curr.getLeft() != null) {
            lHeight = curr.getLeft().getHeight();
        }
        curr.setHeight((Math.max(rHeight, lHeight) + 1));
        curr.setBalanceFactor(lHeight - rHeight);
    }

    private LeaderboardNode balanceLeaderboard(LeaderboardNode curr) {
        if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotate(curr.getRight()));
            }
            curr = leftRotate(curr);
        } else if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotate(curr.getLeft()));
            }
            curr = rightRotate(curr);
        }
        return curr;
    }

    private LeaderboardNode leftRotate(LeaderboardNode curr) {
        LeaderboardNode rightNode = curr.getRight();
        curr.setRight(rightNode.getLeft());
        rightNode.setLeft(curr);
        updateLeaderboardNode(curr);
        updateLeaderboardNode(rightNode);
        return rightNode;
    }

    private LeaderboardNode rightRotate(LeaderboardNode curr) {
        LeaderboardNode leftNode = curr.getLeft();
        curr.setLeft(leftNode.getRight());
        leftNode.setRight(curr);
        updateLeaderboardNode(curr);
        updateLeaderboardNode(leftNode);
        return leftNode;
    }

    public void reverseInorderTraversal(ArrayList<Score> scores) {
        recursiveReverseInorderTraversal(rootScore, scores);
    }

    private void recursiveReverseInorderTraversal(LeaderboardNode curr, ArrayList<Score> scores) {
        if (curr != null) {
            recursiveReverseInorderTraversal(curr.getRight(), scores);
            scores.add(curr.getScore());
            recursiveReverseInorderTraversal(curr.getLeft(), scores);
        }
    }

    public Score get(Score score) {
        return findHelper(rootScore, score);
    }

    private Score findHelper(LeaderboardNode curr, Score score) {
        if (curr == null) {
            return null;
        }
        if (score.getName().equals(curr.getScore().getName())) {
            return curr.getScore();
        } else if (score.compareTo(curr.getScore()) < 0) {
            return findHelper(curr.getLeft(), score);
        } else {
            return findHelper(curr.getRight(), score);
        }
    }

}