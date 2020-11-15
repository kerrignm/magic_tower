package com.game.magictower.astar;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.game.magictower.util.LogUtil;

public class AStarPath {
    
    private final static String TAG = "MagicTower:AStarPath";
    
    private final static int[][] DIR = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    
    private AStarPoint[][] points;
    
    private ObstacleFilter filter;
    
    public AStarPath(int width, int height) {
        points = initePoints(width, height);
    }

    public AStarPoint getPath(int startX, int startY, int goalX, int goalY, int[][]map) {
        
        LogUtil.d(TAG, "getPath() startX = " + startX + ", startY = " + startY + ", goalX = " + goalX + ", goalY = " + goalY);

        Queue<AStarPoint> openSet = new PriorityQueue<>();
        Set<AStarPoint> closeSet = new HashSet<>();

        AStarPoint start = points[startY][startX];
        AStarPoint goal = points[goalY][goalX];
        
        clearPoints(points);

        openSet.offer(start);

        AStarPoint current = null;
        AStarPoint temp = null;
        
        while (!openSet.isEmpty()) {
            current = openSet.remove();
            closeSet.add(current);

            for (int i = 0; i < DIR.length; i++) {
                int y_t = current.getY() + DIR[i][0];
                int x_t = current.getX() + DIR[i][1];

                if (y_t < 0 || y_t >= map.length || x_t < 0 || x_t >= map.length) {
                    continue;
                }

                temp = points[y_t][x_t];

                if (closeSet.contains(temp)) {
                    continue;
                } else if ((filter != null) && filter.isObstacle(map[y_t][x_t], x_t, y_t)) {
                    continue;
                } else if ((filter == null) && isObstacle(map[y_t][x_t])) {
                    continue;
                } else if (openSet.contains(temp)) {
                    if (current.getG_score() <= temp.getG_score()) {
                        temp.setG_scoreAndF_score(current.getG_score() + 1, distance(temp, goal));
                        temp.setFather(current);
                    }
                } else {
                    temp.setG_scoreAndF_score(current.getG_score() + 1, distance(temp, goal));
                    temp.setFather(current);
                    openSet.offer(temp);
                }
            }

            if (openSet.contains(goal)) {
                break;
            }
        }

        if (openSet.isEmpty()) {
            LogUtil.d(TAG, "getPath() empty return null");
            return null;
        }

        return goal.getFather();
    }
    
    public void setFilter(ObstacleFilter filter) {
        this.filter = filter;
    }

    private AStarPoint[][] initePoints(int width, int height) {
        AStarPoint[][] points = new AStarPoint[height][width];
        for (int y = 0; y < points.length; y++) {
            for (int x = 0; x < points[y].length; x++) {
                points[y][x] = new AStarPoint(y, x);
            }
        }
        return points;
    }
    
    private void clearPoints(AStarPoint[][] points) {
        for (int y = 0; y < points.length; y++) {
            for (int x = 0; x < points[y].length; x++) {
                points[y][x].clear();
            }
        }
    }

    private boolean isObstacle(int value) {
        return value > 0;
    }

    private int distance(AStarPoint p1, AStarPoint p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }
}
