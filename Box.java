//staniaKlegr_1339709
//JasonTollison 1319030

//Stores the dimensions of a box. Added methods to rotate certain dimensions
//when wanting to mutate a box. Also included a deep copy method.
public class Box
{
	public int x, y, z, id = 0;
	int tmp; //Used for rotating boxes

	//constructor
	public Box(int newX, int newY, int newZ, int newID){
		this.x = newX;
		this.y = newY;
		this.z = newZ;
		this.id =newID;
	}

	public Box(){}

	//Returns a deep copy of this box
	public Box getCopy(){
		Box b= new Box(x, y, z, id);

		return b;
	}

	//Rotate this box on the width and height
	public void rotateXZ(){
		tmp = x;
		x = z;
		z = tmp;
	}

	//Rotate this box on the depth and height
	public void rotateYZ(){
		tmp = y;
		y = z;
		z = tmp;
	}

	//Rorate this box on the depth and width
	public void rotateXY(){
		tmp = y;
		y = x;
		x = tmp;
	}

	//Returns dimensions of this box
    @Override
    public String toString()
    {
        return ("x:" + x + "  y:" + y + "  z:" + z);
    }
}
