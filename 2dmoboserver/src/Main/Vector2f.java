
package Main;

import java.io.Serializable;

public class Vector2f implements Serializable
{
    private float x,y;
    
    public Vector2f(float x,float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public float length()
    {
        return (float)Math.sqrt(x*x+y*y);
    }
    
    public Vector2f add(Vector2f vect)
    {
        return new Vector2f(x + vect.getX(),y + vect.getY());
    }
    
    public Vector2f minus(Vector2f vect)
    {
        return new Vector2f(x - vect.getX(),y - vect.getY());
    }
    public Vector2f mult(float mul)
    {
        return new Vector2f(x * mul,y * mul);
    }
    public Vector2f div(float div) {
        return new Vector2f(x / div,y / div);
    }
    public float dot(Vector2f vect)
    {
        return x * vect.getX() + y * vect.getY();
    }
    public Vector2f normalize()
    {
        float length = length();
        if(length != 0) {
            return div(length);
        }
        else {
            return this;
        }
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public void setX(float var) {
        x = var;
    }
    public void setY(float var) {
        y = var;
    }

}
