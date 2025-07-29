import java.io.FileNotFoundException;
import java.util.Arrays;

public class TKHDAtom extends Atom{
    byte version;
    byte[] flags;
    int track_id;
    int reserved1;
    long creation_time;
    long modification_time;
    int track_duration;
    byte[] reserved2;
    short layer;
    short alternate_group;
    short volume;
    short reserved3;
    int[] matrix;
    float width;
    float height;

    TKHDAtom(String filepath){
        InitAtom(filepath);
        displayAtom();
    }

    TKHDAtom(byte[] Atom){
        InitAtom(Atom);
        displayAtom();
    }

    @Override
    public void InitAtom(String filepath) {
        try{
            InitAtom(u.GetAtom(filepath,"tkhd").getFirst());
        } catch (Exception e) {
            System.out.println("error hehe");
        }
    }

    @Override
    public void InitAtom(byte[] Atom) {
        AtomInitDefault(Atom);
        version = data[0];
        flags = Arrays.copyOfRange(data,1,4);
        int offset=4;

        track_id = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        reserved1 = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        if (version==0){
            creation_time = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
            offset+=4;

            modification_time = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
            offset+=4;
        } else {
            creation_time = u.HEXToLong8B(Arrays.copyOfRange(data,offset,offset+8));
            offset+=8;

            modification_time = u.HEXToLong8B(Arrays.copyOfRange(data,offset,offset+8));
            offset+=8;
        }

        track_duration = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        reserved2 = Arrays.copyOfRange(data,offset,offset+8);
        offset+=8;

        layer = u.HEXToShort2B(Arrays.copyOfRange(data,offset,offset+2));
        offset+=2;

        alternate_group = u.HEXToShort2B(Arrays.copyOfRange(data,offset,offset+2));
        offset+=2;

        volume = u.HEXToShort2B(Arrays.copyOfRange(data,offset,offset+2));
        offset+=2;

        reserved3 = u.HEXToShort2B(Arrays.copyOfRange(data,offset,offset+2));
        offset+=2;

        matrix = u.ByteToMatrix(Arrays.copyOfRange(data,offset,offset+36));
        offset+=36;

        width = u.HEXToFloat4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        height = u.HEXToFloat4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;
    }



    @Override
    public void displayAtom() {
        System.out.println("--- TKHD Atom ---");
        System.out.println("Atom Size: " + size);
        System.out.println("Version: " + version);
        System.out.println("Flags: " + Arrays.toString(flags));
        System.out.println("Creation Time: " + creation_time + " (seconds since 1904)");
        System.out.println("Modification Time: " + modification_time + " (seconds since 1904)");
        System.out.println("Track ID: " + track_id);
        System.out.println("Reserved: " + reserved1); // if you store this just as int or leave out
        System.out.println("Duration: " + track_duration + " units");
        System.out.println("Reserved2: " + Arrays.toString(reserved2)); // if it's a byte array
        System.out.println("Layer: " + layer);
        System.out.println("Alternate Group: " + alternate_group);
        System.out.println("Volume: " + volume + " (1.0 = full)");
        System.out.println("Reserved3: " + reserved3);
        System.out.println("Matrix: " + Arrays.toString(matrix));
        System.out.println("Width: " + width + " px");
        System.out.println("Height: " + height + " px");
    }

}
