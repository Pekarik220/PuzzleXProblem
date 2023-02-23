package com.example.puzzle8gui;

public class Box {
    private final Integer[] matrix;
    private Box up;
    private Box down;
    private Box left;
    private Box right;

    public Box(Integer[] matrix) {
        this.matrix = matrix;
        this.up = null;
        this.down = null;
        this.left = null;
        this.right = null;
    }

    public void setUp(Box up) {
        this.up = up;
    }

    public void setDown(Box down) {
        this.down = down;
    }

    public void setLeft(Box left) {
        this.left = left;
    }

    public void setRight(Box right) {
        this.right = right;
    }

    public Integer[] getMatrix() {
        return matrix;
    }

    public Box getUp() {
        return up;
    }

    public Box getDown() {
        return down;
    }

    public Box getLeft() {
        return left;
    }

    public Box getRight() {
        return right;
    }
}
