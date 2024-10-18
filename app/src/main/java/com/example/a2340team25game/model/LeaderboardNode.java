package com.example.a2340team25game.model;

import java.io.Serializable;

public class LeaderboardNode implements Serializable {
    private Score score;
    private LeaderboardNode left;
    private LeaderboardNode right;
    private int height;
    private int balanceFactor;

    public LeaderboardNode(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public LeaderboardNode getLeft() {
        return left;
    }

    public LeaderboardNode getRight() {
        return right;
    }

    public void setLeft(LeaderboardNode left) {
        this.left = left;
    }

    public void setRight(LeaderboardNode right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }
}
