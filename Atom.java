import java.util.Arrays;

public abstract class Atom {
    int size;
    String name;
    byte[] data;
    MP4Utils u = new MP4Utils();

    public abstract void InitAtom(String filepath);
    public abstract void InitAtom(byte[] Atom);
    public abstract void displayAtom();
    public void AtomInitDefault(byte[] memory_buffer){
        if (memory_buffer==null){
            System.out.println("Error parsing the given atom");
            return;
        }
        size = u.HEXToInt4B(Arrays.copyOfRange(memory_buffer,0,4));
        name = new String(Arrays.copyOfRange(memory_buffer,4,8));
        data = Arrays.copyOfRange(memory_buffer,8,memory_buffer.length);
    }
}
