/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurtleSoup {
    /**
     * Draw a square.
     *
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength){
        for(int i = 0;i<4;i++)
        {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }
    /**
     * Determine inside angles of a regular polygon.
     *
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides){
        return 180.0-360.0/sides;
    }
    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     *
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle){
        return (int) Math.ceil(360.0/(180.0-angle));
    }
    /**
     * Given the number of sides, draw a regular polygon.
     *
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength){
        double angle = 180.0-calculateRegularPolygonAngle(sides);
        for(int i=0;i<sides;i++){
            turtle.forward(sideLength);
            turtle.turn(angle);
        }
    }
    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     *
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
        int targetX, int targetY){
        if(currentX==targetX)
        {
            if(targetY>=currentX){
                if(currentBearing!=0)
                {
                    return 360-currentBearing;
                }
                else
                {
                    return 0;
                }
            }
            else{
                if(currentBearing!=180.0)
                {
                    if(180.0-currentBearing>=0)
                    {
                        return 180.0-currentBearing;
                    }
                    else
                    {
                        return 360.0+180.0-currentBearing;
                    }
                }
            }
        }
        if(currentY==targetY)
        {
            if(targetX>=currentX)
            {
                if(currentBearing<=90)
                {
                    return 90-currentBearing;
                }
                else
                {
                    return 360.0+90.0-currentBearing;
                }
            }
            else {
                if(currentBearing<=270.0){
                    return 270.0-currentBearing;
                }
                else{
                    return 360.0+270.0-currentBearing;
                }
            }
        }
        double targetbearing = Math.toDegrees(Math.atan((targetY-currentY)/(targetY-currentX)));
        return 90.0-targetbearing-currentBearing;
    }
    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     *
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double>angles = new ArrayList<Double>();
        double currentBearing = 0.0,angle;
        if(xCoords.size() < 2) {
            angle=calculateBearingToPoint(currentBearing,xCoords.get(0),yCoords.get(0),
                    xCoords.get(1),yCoords.get(1));
            angles.add(angle);
            return angles;
        }
        for(int i = 0;i < xCoords.size()-1;i++){
            angle = calculateBearingToPoint(currentBearing,xCoords.get(i),yCoords.get(i),
                    xCoords.get(i+1),yCoords.get(i+1));
            angles.add(angle);
            currentBearing = (currentBearing+angle)%360;
        }
        return angles;
    }
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        if (points.size() <= 3) {
            return points;
        }
        Point[] P = points.toArray(new Point[points.size()]);
        Point a = P[0];
        Set<Point> convexHullPoints = new HashSet<Point>();
        for (Point i : points) {
            if (i.x() < a.x() || (i.x() == a.x() && i.y() < a.y()))
                a = i;
        }
        Point current = a, min = null, last = a;
        double x1 = 0.0, y1 = -1.0;
        do {
            convexHullPoints.add(current);
            double minangle = Double.MAX_VALUE, x2 = 0.0, y2 = 0.0;
            for (Point i : points) {
                if ((!convexHullPoints.contains(i) || i == a) && (i != last)) {
                    double dx = i.x() - current.x(), dy = i.y() - current.y();
                    double anlge = Math.acos((x1* dx + dy * y1) / Math.sqrt(dx * dx + dy * dy)/Math.sqrt(x1*x1+y1*y1));
                    if ( (anlge == minangle && dx * dx + dy * dy > Math.pow(i.x() - min.x(), 2) + Math.pow(i.y() - min.y(), 2))||anlge < minangle ) {
                        min = i;
                        minangle = anlge;
                        x2 = dx;
                        y2 = dy;
                    }
                }
            }
            x1 = x2;
            y1 = y2;
            last = current;
            current = min;
        } while (current != a);
        return convexHullPoints;
    }
    /**
     * Draw your personal, custom art.
     *
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle){
        for(int i=0;i<5;i++)
        {
            turtle.forward(100);
            turtle.turn(144);
        }
    }
    /**
     * Main method.
     *
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String[] args) {
        DrawableTurtle turtle = new DrawableTurtle();
        drawPersonalArt(turtle);
        turtle.draw();
    }
    public static class RuntimeException extends Exception {
        String message;
        public RuntimeException(String ErrorMessage){
            message = ErrorMessage;
        }
        public String getMessage(){
            return message;
        }
    }
}
