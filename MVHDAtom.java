import java.io.FileNotFoundException;
import java.util.Arrays;

public class MVHDAtom extends Atom {
    byte version;
    byte[] flags;
    long creation_time;
    long modification_time;
    int time_scale;
    long duration;
    float preferred_rate;
    short preferred_volume;
    byte[] reserved;
    int[] matrix;
    int preview_time;
    int preview_duration;
    int poster_time;
    int selection_time;
    int selection_duration;
    int current_time;
    int next_track_id;


    MVHDAtom(String filepath){
        InitAtom(filepath);
        displayAtom();
    }

    MVHDAtom(byte[] Atom){
        InitAtom(Atom);
        displayAtom();
    }

    @Override
    public void InitAtom(String filepath) {
        try {
            InitAtom(u.GetAtom(filepath,"mvhd").getFirst());
        } catch (FileNotFoundException f){
            System.out.println("file not found");
        }
    }

    @Override
    public void InitAtom(byte[] Atom) {
        AtomInitDefault(Atom);
        version = data[0];
        flags = Arrays.copyOfRange(data,1,4);

        int offset = 4;
        if (version==0) {
            creation_time = u.HEXToInt4B(Arrays.copyOfRange(data, offset, offset + 4));
            offset += 4;
            modification_time = u.HEXToInt4B(Arrays.copyOfRange(data, offset, offset+4));
            offset += 4;
        }
        else{
            creation_time = u.HEXToLong8B(Arrays.copyOfRange(data, offset, offset + 8));
            offset += 8;
            modification_time = u.HEXToLong8B(Arrays.copyOfRange(data, offset, offset+8));
            offset += 8;
        }
        time_scale = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        if (version==0){
            duration = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
            offset+=4;
        }
        else {
            duration = u.HEXToLong8B(Arrays.copyOfRange(data,offset,offset+8));
            offset+=8;
        }
        preferred_rate = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        preferred_volume = u.HEXToShort2B(Arrays.copyOfRange(data,offset,offset+2));
        offset+=2;

        reserved = Arrays.copyOfRange(data,offset,offset+10);
        offset+=10;

        matrix = u.ByteToMatrix(Arrays.copyOfRange(data,offset,offset+36));
        offset+=36;

        preview_time = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        preview_duration = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        poster_time = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        selection_time = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        selection_duration = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        current_time = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        next_track_id = u.HEXToInt4B(Arrays.copyOfRange(data,offset,offset+4));
        offset+=4;

        System.out.println("offset is : "+offset);
        }



    @Override
    public void displayAtom() {
        System.out.println("--- MVHD Atom ---");
        System.out.println("Atom Size: "+size);
        System.out.println("Version: " + version);
        System.out.println("Flags: " + Arrays.toString(flags));
        System.out.println("Creation Time: " + creation_time + " (seconds since 1904)");
        System.out.println("Modification Time: " + modification_time + " (seconds since 1904)");
        System.out.println("Time Scale: " + time_scale + " units/sec");
        System.out.println("Duration: " + duration + " units (" + (duration / (float) time_scale) + " sec)");
        System.out.println("Preferred Rate: " + preferred_rate + "x (1.0 = normal)");
        System.out.println("Preferred Volume: " + preferred_volume + " (1.0 = full)");
        System.out.println("Matrix Structure: " + Arrays.toString(matrix));
        System.out.println("Preview Time: " + preview_time);
        System.out.println("Preview Duration: " + preview_duration);
        System.out.println("Poster Time: " + poster_time);
        System.out.println("Selection Time: " + selection_time);
        System.out.println("Selection Duration: " + selection_duration);
        System.out.println("Current Time: " + current_time);
        System.out.println("Next Track ID: " + next_track_id);
        System.out.println("Creating time"+creation_time);
    }

}

