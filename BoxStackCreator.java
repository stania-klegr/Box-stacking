import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//Creates a list of boxes each one bigger than the previous.
//Prints out the max tower height of that list
//Then list can be randomised to test a program against
public class BoxStackCreator {
    private ArrayList<Box> boxList;
    private int maxTowerHeight;
    Random rand = new Random();

    public BoxStackCreator(){}

    //Create list of size passed in
    public void generateBoxList(int size){
        boxList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            boxList.add(new Box(i, i, i, i));
            maxTowerHeight += i;
        }
        System.out.println("New set max tower height is :" + maxTowerHeight);
    }

    //Have a method to randomise the list
    public ArrayList<Box> randomiseList()
    {
        Collections.shuffle(boxList);
        return boxList;
    }
}
