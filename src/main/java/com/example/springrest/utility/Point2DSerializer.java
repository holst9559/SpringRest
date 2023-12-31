package com.example.springrest.utility;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.io.IOException;

public class Point2DSerializer extends JsonSerializer<Point<G2D>> {
    @Override
    public void serialize(Point<G2D> g2DPoint, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("longitude", g2DPoint.getPosition().getCoordinate(0));
        jsonGenerator.writeNumberField("latitude", g2DPoint.getPosition().getCoordinate(1));
        jsonGenerator.writeEndObject();
    }
}