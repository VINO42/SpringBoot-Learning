package com.example.springbootgeodesy.common;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class GeodsyDistanceUtils {

    public static double getDistance(Double lonA, Double latA, Double lonB, Double latB, int newScale) {
        GlobalCoordinates source = new GlobalCoordinates(latA, lonA);
        GlobalCoordinates target = new GlobalCoordinates(latB, lonB);
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target);
        double distance = geoCurve.getEllipsoidalDistance();
        BigDecimal distanceBig = new BigDecimal(distance).setScale(newScale, RoundingMode.UP);
        return distanceBig.doubleValue();
    }

}
