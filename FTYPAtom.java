import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FTYPAtom extends Atom{
    String Major_brand;
    int Minor_version;
    List<String> compatible_brands;

    FTYPAtom(String filepath){
        InitAtom(filepath);
        displayAtom();
    }

    FTYPAtom(byte[] Atom){
        InitAtom(Atom);
        displayAtom();
    }

    @Override
    public void InitAtom(String filepath) {
        try(FileInputStream f = new FileInputStream(filepath)){
        InitAtom(u.GetAtom(filepath,"ftyp").getFirst());
        } catch (Exception e){
            System.out.println("error occoured in ftyp Initatom string block");
        }
    }

    @Override
    public void InitAtom(byte[] Atom) {
        AtomInitDefault(Atom);
        Major_brand = new String(Arrays.copyOfRange(data,0,4));
        Minor_version = u.HEXToInt4B(Arrays.copyOfRange(data,4,8));

        compatible_brands = new ArrayList<>();
        String temp;

        for(int i= 8;i<data.length;i+=4){
            temp = new String(Arrays.copyOfRange(data,i,i+4));
            compatible_brands.add(temp);
        }
    }




    @Override
    public void displayAtom() {
//        System.out.println("🎬💾 --- FTYP Atom Breakdown --- 💾🎬");
//        System.out.println("📦 Atom Size       : " + size + " bytes");
//        System.out.println("🧬 Atom Type       : " + name);
//        System.out.println("🚀 Major Brand     : " + Major_brand);
//        System.out.println("🔢 Minor Version   : " + Minor_version);
//
//        System.out.print("📚 Compatible Brands: ");
//        if (compatible_brands.isEmpty()) {
//            System.out.print("None found 😢");
//        } else {
//            for (String brand : compatible_brands) {
//                System.out.print("[" + brand + "] ");
//            }
//        }
//        System.out.println("\n🧠 Pro tip: FTYP tells players how to read your media file! 🔍");
//        System.out.println("🎉 --- End of Atom Info --- 🎉\n");
        System.out.println("--- FTYP Atom ---");
        System.out.println("Atom Size: "+size);
        System.out.println("Major Brand: " + Major_brand);
        System.out.println("Minor Version: " + Minor_version);
        System.out.println("Compatible Brands: " + compatible_brands);
        System.out.println();
    }



}
