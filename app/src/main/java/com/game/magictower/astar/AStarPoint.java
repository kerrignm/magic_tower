package com.game.magictower.astar;

public class AStarPoint implements Comparable<AStarPoint> {
    
    private final int y;
    private final int x;
    
    private int g_score = 1000;
    private int f_score = 1000;
    private AStarPoint father = null;

    public AStarPoint(int y, int x) {
        super();
        this.x = x;
        this.y = y;
    }
    
    public void clear() {
        g_score = 1000;
        f_score = 1000;
        father = null;
    }

    public void setG_scoreAndF_score(int g_score, int distance) {
        this.g_score = g_score;
        this.f_score = g_score + distance;
    }

    public int getG_score() {
        return g_score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setFather(AStarPoint father) {
        this.father = father;
    }

    public AStarPoint getFather() {
        return father;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AStarPoint) {
            return ((AStarPoint) obj).x == x && ((AStarPoint) obj).y == y;
        }
        return false;
    }

    @Override
    public int compareTo(AStarPoint o) {
        return Integer.compare(f_score, o.f_score);
    }
}
