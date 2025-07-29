import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the path : ");
        String filepath = sc.nextLine();

        MP4Utils u =new MP4Utils();

        FTYPAtom f= new FTYPAtom(filepath);
        MVHDAtom m = new MVHDAtom(filepath);
        List<byte[]> tkhd_list = new ArrayList<>();

        tkhd_list = u.GetAtom(filepath,"tkhd");
        List<TKHDAtom> tkhdAtomList= new ArrayList<>();
        tkhd_list.forEach(tkhd ->{
            TKHDAtom t = new TKHDAtom(tkhd);
            tkhdAtomList.add(t);
        });

        //C:\Users\durva_6\Videos\2024-10-04 12-45-14.mp4
    }
}
