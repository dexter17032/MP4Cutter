import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MP4Utils {

    private static final Set<String> ContainerAtoms = new HashSet<>(Arrays.asList(
            "moov", "trak", "mdia", "minf", "dinf", "stbl", "edts", "udta", "meta",
            "moof", "traf", "mfra", "mvex", "iprp", "iloc","elst"
    ));

    public int HEXToInt4B (byte[] arr){
        int size = ((arr[0]&0xFF)<<24)|
                ((arr[1]&0xFF)<<16)|
                ((arr[2]&0xFF)<<8)|
                ((arr[3]&0xFF));

        return size;
    }

    public long HEXToLong8B(byte[] arr){
            return ((long)(arr[0] & 0xFF) << 56) |
                    ((long)(arr[1] & 0xFF) << 48) |
                    ((long)(arr[2] & 0xFF) << 40) |
                    ((long)(arr[3] & 0xFF) << 32) |
                    ((long)(arr[4] & 0xFF) << 24) |
                    ((long)(arr[5] & 0xFF) << 16) |
                    ((long)(arr[6] & 0xFF) << 8)  |
                    ((long)(arr[7] & 0xFF));
    }

    public short HEXToShort2B (byte[] arr){
        short result = (short) (((arr[0] & 0xFF) << 8) | (arr[1] & 0xFF));
        return result;
    }

    public  float HEXToFloat4B(byte[] bytes) {

        // Convert the 4 bytes to an unsigned 32-bit integer
        int value = ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8)  |
                (bytes[3] & 0xFF);

        // Split into integer and fractional parts
        int integerPart = (value >> 16) & 0xFFFF;
        int fractionalPart = value & 0xFFFF;

        return (float) (integerPart + (fractionalPart / 65536.0));
    }


    public int[] ByteToMatrix(byte[] arr){
        if (arr.length<36){
            System.out.println("not enough data to build matrix");
            return null;
        }

        int[] int_arr = new int[9];

        int current=0;
        for (int i=0;i<9;i++){
            current = i*4;
            int_arr[i] = HEXToInt4B(Arrays.copyOfRange(arr,current,current+4));
        }
        return int_arr;
    }


    public List<byte[]> GetAtom(String filepath, String name) throws FileNotFoundException {
        List<byte[]> result_list = new ArrayList<>();
        int read_bytes=8;
        byte[] info_buffer = new byte[8];
        byte[] current_atom;
        String current_name;
        int size;
        try(FileInputStream f = new FileInputStream(filepath)){
            while(f.available()>0){
                int temp = f.read(info_buffer);
                size = HEXToInt4B(Arrays.copyOfRange(info_buffer,0,4));
                current_name = new String(Arrays.copyOfRange(info_buffer,4,8));
                //System.out.println(current_name);

                current_atom = new byte[size-8];
                read_bytes = f.read(current_atom);
                if (read_bytes!=size-8){
                    System.out.println("read error for atom "+current_name);
                    return new ArrayList<>();
                }
                read_bytes =temp;

                if (isContainerAtom(current_name)){
                    //System.out.println("called search in getatom for : "+current_name);
                    List<byte[]> temp_list = SearchSubAtoms(current_atom,name);
                    if (!temp_list.isEmpty()){
                        result_list.addAll(temp_list);
                    }
                } else if(current_name.equals(name)){
                    result_list.add(concatenateByteArray(info_buffer,current_atom));
                }
            }
            return result_list;
        } catch (IOException e){
            System.out.println("Error occoured idk waht to type");
        }
        return result_list;
    }


    public List<byte[]> SearchSubAtoms(byte[] Atom,String name){
        List<byte[]>  result_array = new ArrayList<>();
        int index = 0;


        while(index+8<Atom.length){
            //System.out.println(index);
            byte[] info_buffer = Arrays.copyOfRange(Atom,index,index+8);
            int size = HEXToInt4B(Arrays.copyOfRange(info_buffer,0,4));

            if (size < 8 || index + size > Atom.length) {
                //System.out.println("Invalid atom size: " + size + " at index " + index);
                break;
            }

            String current_name = new String(Arrays.copyOfRange(info_buffer,4,8));
            //System.out.println(current_name);
            if (isContainerAtom(current_name)){
                //System.out.println("called sub atom for : "+current_name);
                List<byte[]> result;
                if (current_name.equals("meta")){
                    result = SearchSubAtoms(Arrays.copyOfRange(Atom,index+8+4,index+size),name);
                    //System.out.println("😈 meta tried to sneak those 4 extra bytes past me");
                } else {
                    result = SearchSubAtoms(Arrays.copyOfRange(Atom,index+8,index+size),name);
                }
                if (!result.isEmpty()){
                    result_array.addAll(result);
                }
            }
            else if (current_name.equals(name)){
                result_array.add(Arrays.copyOfRange(Atom,index,index + size));
            }
            index+=size;
        }
        return result_array;
    }

    public boolean isContainerAtom(String name){
        return ContainerAtoms.contains(name);
    }

    public byte[] concatenateByteArray(byte[] arr1, byte[] arr2){
        int size =arr1.length + arr2.length;
        byte[] new_arr = new byte[size];
        System.arraycopy(arr1,0,new_arr,0,arr1.length);
        System.arraycopy(arr2,0,new_arr,arr1.length,arr2.length);
        return new_arr;
    }
}
