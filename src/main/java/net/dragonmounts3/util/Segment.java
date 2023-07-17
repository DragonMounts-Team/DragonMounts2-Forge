package net.dragonmounts3.util;

public class Segment implements Cloneable {
    // scale multiplier
    public float scaleX;
    public float scaleY;
    public float scaleZ;
    // rotation points
    public float x;
    public float y;
    public float z;
    // rotation angles
    public float xRot;
    public float yRot;
    public float zRot;

    @Override
    public Segment clone() {
        try {
            return (Segment) super.clone();
        } catch (CloneNotSupportedException e) {
            Segment segment = new Segment();
            segment.scaleX = this.scaleX;
            segment.scaleY = this.scaleY;
            segment.scaleZ = this.scaleZ;
            segment.x = this.x;
            segment.y = this.y;
            segment.z = this.z;
            segment.xRot = this.xRot;
            segment.yRot = this.yRot;
            segment.zRot = this.zRot;
            return segment;
        }
    }
}
